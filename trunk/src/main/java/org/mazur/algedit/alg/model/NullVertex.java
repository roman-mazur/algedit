/**
 * 
 */
package org.mazur.algedit.alg.model;


/**
 * @author Roman Mazur (IO-52)
 *
 */
public class NullVertex extends AbstractVertex {

  /**
   * @see org.mazur.algedit.alg.model.AbstractVertex#getLabel()
   */
  @Override
  public String getLabel() {
    return "NULL-VERTEX";
  }

  /**
   * @see org.mazur.algedit.alg.model.AbstractVertex#getType()
   */
  @Override
  public VertexType getType() {
    return VertexType.SPECIAL;
  }

}
