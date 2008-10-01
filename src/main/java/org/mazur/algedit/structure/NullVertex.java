/**
 * 
 */
package org.mazur.algedit.structure;

import java.util.LinkedList;
import java.util.Set;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class NullVertex extends AbstractVertex {

  /**
   * @see org.mazur.algedit.structure.AbstractVertex#draw(java.util.LinkedList, java.util.Set)
   */
  @Override
  public String draw(LinkedList<BackLink> links, Set<AbstractVertex> visited) {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @see org.mazur.algedit.structure.AbstractVertex#getLabel()
   */
  @Override
  public String getLabel() {
    return "NULL-VERTEX";
  }

  /**
   * @see org.mazur.algedit.structure.AbstractVertex#getType()
   */
  @Override
  public VertexType getType() {
    return VertexType.SPECIAL;
  }

}
