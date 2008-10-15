/**
 * 
 */
package org.mazur.algedit.alg.model;


/**
 * Begin vertex.
 * @author Roman Mazur (IO-52)
 *
 */
public class BeginVertex extends AbstractVertex {
  
  /** Label. */
  private static final String BEGIN_LABEL = "B";

  /**
   * {@inheritDoc}
   * @see org.mazur.algedit.structure.Vertex#getLabel()
   */
  public String getLabel() {
    return BeginVertex.BEGIN_LABEL;
  }

  @Override
  public VertexType getType() {
    return VertexType.SPECIAL;
  }

}
