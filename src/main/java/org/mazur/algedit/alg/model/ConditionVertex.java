/**
 * 
 */
package org.mazur.algedit.alg.model;


/**
 * Condition vertex.
 * @author Roman Mazur (IO-52)
 *
 */
public class ConditionVertex extends AbstractVertex {

  /** Label. */
  private static final String CONDITION_LABEL = "X";
  
  /** Alternative vertex (for x=1). */
  private AbstractVertex alternativeVertex;
  
  /**
   * @return the alternativeVertex
   */
  public final AbstractVertex getAlternativeVertex() {
    return alternativeVertex;
  }

  /**
   * @param alternativeVertex the alternativeVertex to set
   */
  public final void setAlternativeVertex(final AbstractVertex alternativeVertex) {
    this.alternativeVertex = alternativeVertex;
  }

  /**
   * {@inheritDoc}
   * @see org.mazur.algedit.structure.Vertex#getLabel()
   */
  public String getLabel() {
    return ConditionVertex.CONDITION_LABEL + getSignalIndex();
  }

  @Override
  public VertexType getType() {
    return VertexType.CONDITION;
  }

}
