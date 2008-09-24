/**
 * 
 */
package org.mazur.algedit.structure;

import java.util.LinkedList;

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
  public String draw(LinkedList<BackLink> links) {
    return getLabel() + " " + getStraightVertex().draw(links);
  }

  public String draw() {
    LinkedList<BackLink> links = new LinkedList<BackLink>();
    return draw(links);
  }

}
