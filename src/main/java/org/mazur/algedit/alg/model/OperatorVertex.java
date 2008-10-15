/**
 * 
 */
package org.mazur.algedit.alg.model;


/**
 * Operator vertex.
 * @author Roman Mazur (IO-52)
 *
 */
public class OperatorVertex extends AbstractVertex {
  
  /** Label. */
  private static final String OPERATOR_LABEL = "Y";
  
  /**
   * {@inheritDoc}
   * @see org.mazur.algedit.structure.Vertex#getLabel()
   */
  public String getLabel() {
    return OperatorVertex.OPERATOR_LABEL + getSignalIndex();
  }

  @Override
  public VertexType getType() {
    return VertexType.OPERATOR;
  }
}
