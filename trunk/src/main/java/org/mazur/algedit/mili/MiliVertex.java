/**
 * 
 */
package org.mazur.algedit.mili;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class MiliVertex implements Serializable {

  /** serialVersionUID. */
  private static final long serialVersionUID = -7763519574196975623L;
  
  /** Vertex code. */
  private int code;
  
  /** List of outgoing edges. */
  private List<MiliTransmition> outgoings = new LinkedList<MiliTransmition>();
  
  /**
   * @return the outgoings
   */
  public final List<MiliTransmition> getOutgoings() { return outgoings; }

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
}
