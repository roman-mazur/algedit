/**
 * 
 */
package org.mazur.algedit.transformers;

import org.mazur.algedit.Transformer;
import org.mazur.algedit.boolfunc.model.BoolFunction;
import org.mazur.algedit.boolfunc.model.BoolFunctionModel;
import org.mazur.algedit.boolfunc.model.ConjunctionTerm;
import org.mazur.algedit.boolfunc.model.Input;
import org.mazur.algedit.exceptions.TransformException;
import org.mazur.algedit.transtable.model.TableRow;
import org.mazur.algedit.transtable.model.TransTableModel;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class BoolFunctionsTransformer implements Transformer<TransTableModel, BoolFunctionModel> {

  public String getName() {
    return "Boolean functions";
  }

  public static void prepareResult(final BoolFunctionModel result, final int oCnt, final int transCnt) {
    result.setOutFunctions(oCnt);
    result.setTransFunctions(transCnt);
    for (int i = 0; i < oCnt; i++) {
      BoolFunction f = new BoolFunction();
      f.setIndex(i);
      f.setName("y");
      result.getOutFunctions().add(f);
    }
    for (int i = 0; i < transCnt; i++) {
      BoolFunction f = new BoolFunction();
      f.setIndex(i);
      f.setName("R");
      result.getTransFunctions().add(f);
      f = new BoolFunction();
      f.setIndex(i);
      f.setName("S");
      result.getTransFunctions().add(f);
    }
    
  }
  
  public BoolFunctionModel transform(final TransTableModel source)
      throws TransformException {
    final int outFuncCount = source.getMainObject().get(0).getOperatorSignals().length;
    final int termsLen = source.getCodeDigitsCount() + source.getMainObject().get(0).getConditionSignals().length;
    BoolFunctionModel result = new BoolFunctionModel(source.getName());
    prepareResult(result, outFuncCount, source.getCodeDigitsCount());
    for (TableRow row : source.getMainObject()) {
      ConjunctionTerm term = new ConjunctionTerm();
      term.setTermLength(termsLen);
      int condLen = row.getConditionSignals().length;
      for (int i = 0; i < condLen; i++) {
        if (row.getConditionSignals()[i] >= 0) {
          term.addElement(new Input("x" + i, i), row.getConditionSignals()[i]);
        }
      }
      int code = row.getCodeFrom();
      for (int i = 0; i < source.getCodeDigitsCount(); i++) {
        term.addElement(new Input("Q" + i, condLen + i), (byte)(code & 1));
        code >>= 1;
      }
      
      for (int i = 0; i < outFuncCount; i++) {
        BoolFunction f = result.getOutFunctions().get(i);
        if (row.getOperatorSignals()[i] == 1) {
          f.getDisjunction().add(term);
        } else {
          f.getInverse().add(term);
        }
      }
      for (int i = 0; i < source.getCodeDigitsCount(); i++) {
        byte[] rs = row.getRsFunctions()[i].getValues();
        int tfi = i << 1;
        BoolFunction r = result.getTransFunctions().get(tfi);
        if (rs[0] == 1) {
          r.getDisjunction().add(term);
        } else {
          r.getInverse().add(term);
        }
        BoolFunction s = result.getTransFunctions().get(tfi + 1);
        if (rs[1] == 1) {
          s.getDisjunction().add(term);
        } else {
          s.getInverse().add(term);
        }
      }
    }
    return result;
  }

}
