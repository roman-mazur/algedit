/**
 * 
 */
package org.mazur.algedit.alg;

/**
 * Back link.
 * @author Roman Mazur (IO-52)
 *
 */
public class BackLink {

  /** Vertex. */
  private AbstractVertex vertex;
  /** Number. */
  private int number;
  /** Direction. */
  private boolean direction = true;
  
  /**
   * @return the direction
   */
  public final boolean isDirection() {
    return direction;
  }
  /**
   * @param direction the direction to set
   */
  public final void setDirection(final boolean direction) {
    this.direction = direction;
  }
  /**
   * @return the vertex
   */
  public final AbstractVertex getVertex() {
    return vertex;
  }
  /**
   * @return the number
   */
  public final int getNumber() {
    return number;
  }
  /**
   * @param vertex the vertex to set
   */
  public final void setVertex(final AbstractVertex vertex) {
    this.vertex = vertex;
  }
  /**
   * @param number the number to set
   */
  public final void setNumber(final int number) {
    this.number = number;
  }
  
}
