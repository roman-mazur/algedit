package org.mazur.algedit.boolfunc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ConjunctionTerm implements Serializable {

  private static final long serialVersionUID = 527010964023168935L;

  private List<TermElement> elements = new LinkedList<TermElement>();
  private byte[] term;
  
  public ConjunctionTerm() { }
  private ConjunctionTerm(final byte[] term) {
    this.term = term;
  }
  
  public int getNumber() {
    int res = 0;
    int inc = 1;
    for (byte t : term) {
      if (t > 0) { res += inc; }
      inc <<= 1;
    }
    return res;
  }
  
  public void addElement(final Input input, final byte dir) {
    TermElement e = new TermElement();
    e.input = input;
    e.direction = dir > 0;
    elements.add(e);
    term[input.getNumber()] = dir;
  }
  
  public void setTermLength(final int size) {
    term = new byte[size];
    for (int i = 0; i < term.length; i++) { term[i] = -1; }
  }
  
  public int getTermLength() {
    return term.length;
  }
  
  public void unmark(final byte[] undefined) {
    for (int i = 0; i < term.length; i++) {
      if (term[i] >= 0) {
        undefined[i] = 1;
      }
    }
  }
  
  public String getTermString() {
    String res = "";
    for (TermElement e : elements) { res += e; }
    return res;
  }
  
  public ConjunctionTerm merge(final ConjunctionTerm ct) {
    int difCnt = 0;
    ConjunctionTerm result = new ConjunctionTerm();
    result.setTermLength(term.length);
    for (int i = 0; i < term.length && difCnt < 2; i++) {
      int comp = term[i] ^ ct.term[i]; 
      if (comp == 0) {
        result.term[i] = term[i]; continue;
      }
      if (term[i] < 0) {result.term[i] = ct.term[i]; continue;}
      if (ct.term[i] < 0) {result.term[i] = term[i]; continue;}
      difCnt++;
      result.term[i] = -1;
    }
    if (difCnt < 2) { return result; }
    return null;
  }
  
  public boolean isCovered(final ConjunctionTerm ct) {
    for (int i = 0; i < term.length; i++) {
      byte comp = (byte)(term[i] ^ ct.term[i]);
      if (comp > 0) { return false; }
      if (comp < 0 && term[i] < 0) { return false; }
    }
    return true;
  }
 
  public ArrayList<String> getNames() {
    ArrayList<String> result = new ArrayList<String>(term.length);
    for (TermElement e : elements) {
      BoolFunctionModel.setValue(result, e.input.getNumber(), e.input.getName());
    }
    return result;
  }
  
  public void recover(final String names[]) {
    elements = new LinkedList<TermElement>();
    for (int i = 0; i < term.length; i++) {
      if (term[i] < 0) { continue; }
      addElement(new Input(names[i], i), term[i]);
    }
  }
  
  private byte[] createNearTerm(final int i) {
    byte[] result = new byte[term.length];
    System.arraycopy(term, 0, result, 0, term.length);
    result[i] = (byte)(1 - result[i]);
    return result;
  }
  
  public List<ConjunctionTerm> getNearTerms() {
    ArrayList<ConjunctionTerm> result = new ArrayList<ConjunctionTerm>(term.length);
    for (int i = 0; i < term.length; i++) {
      if (term[i] >= 0) {
        result.add(new ConjunctionTerm(createNearTerm(i)));
      }
    }
    return result;
  }
  
  private static class TermElement implements Serializable {
    private static final long serialVersionUID = 7260912742022114282L;
    private Input input;
    private boolean direction;
    private TermElement() { }
    @Override
    public String toString() {
      return (direction ? "" : "~") + input.getName();
    }
  }
  
}