/**
 * 
 */
package org.mazur.algedit;

import java.io.Serializable;

/**
 * Matrix representing the model.
 * @param <T> main model object type
 * @author Roman Mazur (IO-52)
 *
 */
public interface SerializeableMatrix<T> extends Serializable {

  /**
   * @return main object of the model
   */
  T build();
  
}
