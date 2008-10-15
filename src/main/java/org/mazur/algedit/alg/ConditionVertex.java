/**
 * 
 */
package org.mazur.algedit.alg;

import java.util.LinkedList;
import java.util.Set;

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
  public String draw(final LinkedList<BackLink> links, final Set<AbstractVertex> visited) {
    BackLink bl = new BackLink();
    bl.setVertex(alternativeVertex);
    bl.setNumber(getLinkIndex());
    links.add(bl);
    
    String next = "";
    if (!visited.contains(getStraightVertex())) {
      next = getStraightVertex().draw(links, visited);
      visited.add(getStraightVertex());
    }
    String linksStr = drawLinks(links);
    return linksStr + getLabel() + "^" + getLinkIndex() + " " + next + " _" + getLinkIndex();
  }

  @Override
  public VertexType getType() {
    return VertexType.CONDITION;
  }

}
