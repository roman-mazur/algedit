/**
 * 
 */
package org.mazur.algedit.mili.model;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class MiliVertex {

  /** Index. */
  private int index;
  
  /** Vertex code. */
  private int code;
  
  /** List of outgoing edges. */
  private List<MiliTransition> outgoings = new LinkedList<MiliTransition>();
  
  /**
   * @return the outgoings
   */
  public final List<MiliTransition> getOutgoings() { return outgoings; }

  /**
   * @return the code
   */
  public final int getCode() { return code; }

  /**
   * @param code the code to set
   */
  public final void setCode(final int code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return "Q" + code + "(outs=" + outgoings + ")";
  }

  /**
   * @return the index
   */
  public final int getIndex() {
    return index;
  }

  /**
   * @param index the index to set
   */
  public final void setIndex(final int index) {
    this.index = index;
  }
}
