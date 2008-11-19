/**
 * 
 */
package org.mazur.algedit.boolfunc.model;

import java.io.Serializable;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class Input implements Serializable {

  private static final long serialVersionUID = 7484316953976266028L;

  private String name;
  private int number;
  
  public Input(final String name, final int number) {
    this.name = name;
    this.number = number;
  }
  
  /**
   * @return the name
   */
  public final String getName() {
    return name;
  }
  /**
   * @return the number
   */
  public final int getNumber() {
    return number;
  }
  /**
   * @param name the name to set
   */
  public final void setName(final String name) {
    this.name = name;
  }
  /**
   * @param number the number to set
   */
  public final void setNumber(final int number) {
    this.number = number;
  }

  public String getDescription() {
    return name + " - "+ number;
  }
}
