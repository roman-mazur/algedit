/**
 * 
 */
package org.mazur.algedit.boolfunc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class BoolFunction implements Serializable {

  private static final long serialVersionUID = -3707548042207408424L;

  private int index;
  private String name;

  private List<ConjunctionTerm> inverse = new LinkedList<ConjunctionTerm>();
  private List<ConjunctionTerm> disjunction = new LinkedList<ConjunctionTerm>();

  /**
   * @return the inverse
   */
  public final List<ConjunctionTerm> getInverse() {
    return inverse;
  }

  /**
   * @return the disjunction
   */
  public final List<ConjunctionTerm> getDisjunction() {
    return disjunction;
  }
  
  public String getDescription() {
    StringBuilder sb = new StringBuilder();
    final String or = " or ";
    sb.append(name);
    sb.append(index);
    sb.append(" = ");
    if (disjunction.size() == 0) { 
      sb.append("0"); 
    } else {
      for (ConjunctionTerm term : disjunction) {
        sb.append(term.getTermString());
        sb.append(or);
      }
      sb.delete(sb.length() - or.length(), sb.length());
    }
    return sb.toString();
  }

  /**
   * @return the index
   */
  public final int getIndex() {
    return index;
  }

  /**
   * @return the name
   */
  public final String getName() {
    return name;
  }

  /**
   * @param index the index to set
   */
  public final void setIndex(int index) {
    this.index = index;
  }

  /**
   * @param name the name to set
   */
  public final void setName(String name) {
    this.name = name;
  }
 
  public ArrayList<String> getNames() {
    ArrayList<String> res = new ArrayList<String>();
    for (ConjunctionTerm ct : disjunction) {
      BoolFunctionModel.merge(res, ct.getNames());
    }
    return res;
  }
}
