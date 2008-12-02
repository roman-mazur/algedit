/**
 * 
 */
package org.mazur.algedit.transformers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.mazur.algedit.Transformer;
import org.mazur.algedit.boolfunc.model.BoolFunction;
import org.mazur.algedit.boolfunc.model.BoolFunctionModel;
import org.mazur.algedit.boolfunc.model.ConjunctionTerm;
import org.mazur.algedit.exceptions.TransformException;

/**
 * @author Roman Mazur (IO-52)
 */
public class MinimizationTransformer implements Transformer<BoolFunctionModel, BoolFunctionModel> {

  public String getName() {
    return "Minimization";
  }

  private ArrayList<ConjunctionTerm> prepareDNF(final BoolFunction f) {
    ArrayList<ConjunctionTerm> dnf = new ArrayList<ConjunctionTerm>(f.getDisjunction());
    if (dnf.size() == 0) { return dnf; }
    LinkedList<ConjunctionTerm> undefinedTerms = new LinkedList<ConjunctionTerm>();
    HashSet<Integer> setNumbers = new HashSet<Integer>(dnf.size() + f.getInverse().size());
    for (ConjunctionTerm term : dnf) { setNumbers.add(term.getNumber()); }
    for (ConjunctionTerm term : f.getInverse()) { setNumbers.add(term.getNumber()); }
    for (ConjunctionTerm term : dnf) {
      List<ConjunctionTerm> nearTerms = term.getNearTerms();
      for (ConjunctionTerm nt : nearTerms) {
        if (!setNumbers.contains(nt.getNumber())) {
          undefinedTerms.add(nt);
        }
      }
    }
    dnf.addAll(undefinedTerms);
    return dnf;
  }
  
  private BoolFunction minimize(final BoolFunction func, final String[] names) {
    LinkedList<List<ConjunctionTerm>> steps = new LinkedList<List<ConjunctionTerm>>();
    ArrayList<ConjunctionTerm> dnf = prepareDNF(func); 
    steps.push(dnf);
    boolean finished = false;
    while (!finished) {
      List<ConjunctionTerm> terms = steps.peek();
      HashSet<ConjunctionTerm> covered = new HashSet<ConjunctionTerm>();
      ArrayList<ConjunctionTerm> mergedTerms = new ArrayList<ConjunctionTerm>(terms.size());
      for (int i = 0; i < terms.size(); i++) {
        ConjunctionTerm t1 = terms.get(i);
        for (int j = i + 1; j < terms.size(); j++) {
          ConjunctionTerm t2 = terms.get(j);
          ConjunctionTerm merged = t1.merge(t2);
          if (merged == null) { continue; }
          if (t1.isCovered(merged)) { covered.add(t1); }
          if (t2.isCovered(merged)) { covered.add(t2); }
          mergedTerms.add(merged);
        }
      }
      terms.removeAll(covered);
      steps.push(mergedTerms);
      finished = mergedTerms.size() == 0;
    }
    BoolFunction f = new BoolFunction();
    f.setIndex(func.getIndex());
    f.setName(func.getName());
    do {
      List<ConjunctionTerm> terms = steps.pop();
      for (ConjunctionTerm ct : terms) { ct.recover(names); }
      f.getDisjunction().addAll(terms);
    } while (!steps.isEmpty());
    System.gc();
    return f;
  }
  
  public BoolFunctionModel transform(final BoolFunctionModel source)
      throws TransformException {
    BoolFunctionModel result = new BoolFunctionModel(source.getName());
    BoolFunctionsTransformer.prepareResult(result, source.getOutFunctions().size(), 
        source.getTransFunctions().size() >> 1);
    String[] names = source.getNames();
    for (BoolFunction f : source.getOutFunctions()) {
      result.getOutFunctions().set(f.getIndex(), minimize(f, names));
    }
    int i = 0;
    for (BoolFunction f : source.getTransFunctions()) {
      result.getTransFunctions().set(i, minimize(f, names));
      i++;
    }
    return result;
  }

}
