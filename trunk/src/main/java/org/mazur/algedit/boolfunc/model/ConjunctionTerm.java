package org.mazur.algedit.boolfunc.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ConjunctionTerm implements Serializable {

  private static final long serialVersionUID = 527010964023168935L;

  private List<TermElement> elements = new LinkedList<TermElement>();
  private byte[] term;
  
  public void addElement(final Input input, final byte dir) {
    TermElement e = new TermElement();
    e.input = input;
    e.direction = dir > 0;
    elements.add(e);
    term[input.getNumber()] = dir;
  }
  
  public void setTermLength(final int size) {
    term = new byte[size];
  }
  
  public String getTermString() {
    String res = "";
    for (TermElement e : elements) { res += e; }
    return res;
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