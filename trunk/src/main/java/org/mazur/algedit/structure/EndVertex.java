/**
 * 
 */
package org.mazur.algedit.structure;

import java.util.LinkedList;
import java.util.Set;

/**
 * End vertex.
 * @author Roman Mazur (IO-52)
 */
public class EndVertex extends AbstractVertex {

  /** Label. */
  private static final String END_LABEL = "E";

  private static final AbstractVertex NULL_VERTEX = new NullVertex();
  
  /**
   * {@inheritDoc}
   * @see org.mazur.algedit.structure.Vertex#getLabel()
   */
  public String getLabel() {
    return EndVertex.END_LABEL;
  }
  
  @Override
  public AbstractVertex getStraightVertex() {
    return EndVertex.NULL_VERTEX;
  }

  @Override
  public String draw(final LinkedList<BackLink> links, final Set<AbstractVertex> visited) {
    String linksStr = drawLinks(links);
    return linksStr + getLabel();
  }

  @Override
  public VertexType getType() {
    return VertexType.SPECIAL;
  }
}
