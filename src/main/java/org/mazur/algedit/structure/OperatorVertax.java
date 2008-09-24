/**
 * 
 */
package org.mazur.algedit.structure;

import java.util.LinkedList;

/**
 * Operator vertex.
 * @author Roman Mazur (IO-52)
 *
 */
public class OperatorVertax extends AbstractVertex {
  
  /** Label. */
  private static final String OPERATOR_LABEL = "Y";
  
  /**
   * {@inheritDoc}
   * @see org.mazur.algedit.structure.Vertex#getLabel()
   */
  public String getLabel() {
    return OperatorVertax.OPERATOR_LABEL + getSignalIndex();
  }

  @Override
  public String draw(final LinkedList<BackLink> links) {
    String next = getStraightVertex().draw(links);
    String linksStr = drawLinks(links);
    return linksStr + getLabel() + " " + next;
  }

}
