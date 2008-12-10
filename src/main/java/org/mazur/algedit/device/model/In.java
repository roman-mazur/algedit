/**
 * 
 */
package org.mazur.algedit.device.model;

import java.util.Set;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class In implements Outable {

  private static final long serialVersionUID = -5626997902484157643L;
  private boolean value;
  private boolean inverse = true;
  /**
   * @return the inverse
   */
  public boolean isInverse() {
    return inverse;
  }

  /**
   * @param inverse the inverse to set
   */
  public void setInverse(boolean inverse) {
    this.inverse = inverse;
  }

  private String name;
  
  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @see org.mazur.algedit.device.model.Outable#out()
   */
  public boolean out() {
    return inverse ? !value : value;
  }

  public void set(final boolean v) {
    value = v;
  }
  
  @Override
  public String toString() {
    return "(" + name + "=" + value + ")";
  }

  public String vhdl() {
    return name;
  }

  public String name() {
    return getName();
  }
}
