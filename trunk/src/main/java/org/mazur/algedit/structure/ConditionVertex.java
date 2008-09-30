/**
 * 
 */
package org.mazur.algedit.structure;

import java.util.LinkedList;

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
  
  /** Link index. */
  private int linkIndex = 0;
  
  /**
   * @return the linkIndex
   */
  public final int getLinkIndex() {
    return linkIndex;
  }

  /**
   * @param linkIndex the linkIndex to set
   */
  public final void setLinkIndex(final int linkIndex) {
    this.linkIndex = linkIndex;
  }

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
  public String draw(final LinkedList<BackLink> links) {
    BackLink bl = new BackLink();
    bl.setVertex(alternativeVertex);
    bl.setNumber(linkIndex);
    links.add(bl);
    
    String next = getStraightVertex().draw(links);
    String linksStr = drawLinks(links);
    return linksStr + getLabel() + "^" + linkIndex + " " + next;
  }

  @Override
  public VertexType getType() {
    return VertexType.CONDITION;
  }

}
