/**
 * 
 */
package org.mazur.algedit.transformers;

import java.util.ArrayList;
import java.util.LinkedList;

import org.mazur.algedit.Transformer;
import org.mazur.algedit.exceptions.TransformException;
import org.mazur.algedit.mili.model.MiliGraphModel;
import org.mazur.algedit.mili.model.MiliTransition;
import org.mazur.algedit.mili.model.MiliVertex;
import org.mazur.algedit.mili.model.MiliTransition.TransmitionCondition;
import org.mazur.algedit.transtable.model.TableRow;
import org.mazur.algedit.transtable.model.TransTableModel;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class TransTableTransformer implements Transformer<MiliGraphModel, TransTableModel> {

  public String getName() {
    return "Transmition table";
  }

  public TransTableModel transform(final MiliGraphModel source)
      throws TransformException {
    LinkedList<Entry> entries = new LinkedList<Entry>();
    int digitsCount = 0, sCount = 0, cCount = 0;
    for (MiliVertex vertex : source.getMainObject()) {
      int x = NeighboringCoding.getMinDigitsCount(vertex.getCode());
      if (digitsCount < x) { digitsCount = x; }
      for (MiliTransition mt : vertex.getOutgoings()) {
        Entry entry = new Entry();
        entry.row = new TableRow();
        entry.transition = mt;
        entry.row.setNameFrom("Q" + vertex.getIndex());
        entry.row.setNameTo("Q" + mt.getTarget().getIndex());
        entry.row.setCodeFrom(vertex.getCode());
        entry.row.setCodeTo(mt.getTarget().getCode());
        entries.add(entry);
        if (sCount < mt.getYSignal()) { sCount = mt.getYSignal(); }
        for (TransmitionCondition cond : mt.getConditions()) {
          if (cCount < cond.getIndex()) { cCount = cond.getIndex(); }
        }
      }
    }
    ArrayList<TableRow> rows = new ArrayList<TableRow>(entries.size());
    for (Entry e : entries) {
      TableRow row = e.row;
      row.setRsFunctions(new TableRow.RSFunction[digitsCount]);
      row.setConditionSignals(cCount + 1);
      row.setOperatorSignals(sCount + 1);
      if (e.transition.getYSignal() >= 0) {
        row.getOperatorSignals()[e.transition.getYSignal()] = 1;
      }
      for (TransmitionCondition cond : e.transition.getConditions()) {
        row.getConditionSignals()[cond.getIndex()] = (byte)(cond.isDesc() ? 1 : 0);
      }
      int old = row.getCodeFrom();
      int temp = row.getCodeFrom() ^ row.getCodeTo();
      int i = 0;
      while (i < digitsCount) {
        int oldDigit = old & 1;
        if ((temp & 1) == 1) { //changed
          row.setRsFunction(i, oldDigit, 1 - oldDigit);
        } else {
          if (oldDigit == 0) { row.setRsFunction(i, -1, 0); }
          else { row.setRsFunction(i, 0, -1); }
        }
        old >>= 1;
        temp >>= 1;
        i++;
      }
      rows.add(row);
    }
    TransTableModel result = new TransTableModel(source.getName());
    result.setMainObject(rows);
    result.setCodeDigitsCount(digitsCount);
    return result;
  }

  
  private static class Entry {
    private TableRow row;
    private MiliTransition transition;
  }
}
