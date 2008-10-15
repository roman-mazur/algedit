/**
 * 
 */
package org.mazur.algedit.alg;

import java.util.LinkedList;
import java.util.Set;

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
  public String draw(final LinkedList<BackLink> links, final Set<AbstractVertex> visited) {
    String next = "";
    String al = "";
    if (getLinkIndex() != 0) {
      BackLink bl = new BackLink();
      bl.setVertex(getStraightVertex());
      bl.setNumber(getLinkIndex());
      links.add(bl);
      al += "^" + getLinkIndex();
    } else if (!visited.contains(getStraightVertex())) {
      next = getStraightVertex().draw(links, visited);
      visited.add(getStraightVertex());
    }
    String linksStr = drawLinks(links);
    return linksStr + getLabel() + al + " " + next;
  }

  @Override
  public VertexType getType() {
    return VertexType.OPERATOR;
  }
}
