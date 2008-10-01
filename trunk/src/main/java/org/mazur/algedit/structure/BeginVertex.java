/**
 * 
 */
package org.mazur.algedit.structure;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

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
  public String draw(LinkedList<BackLink> links, final Set<AbstractVertex> visited) {
    String main = getLabel() + " " + getStraightVertex().draw(links, visited);
    while (!links.isEmpty()) {
      main += " " + links.getFirst().getVertex().draw(links, visited);
    }
    return main;
  }

  public String draw() {
    return draw(new LinkedList<BackLink>(), new HashSet<AbstractVertex>());
  }

  @Override
  public VertexType getType() {
    return VertexType.SPECIAL;
  }

}
