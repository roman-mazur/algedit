/**
 * 
 */
package org.mazur.algedit;

/**
 * Model transformer.
 * @param <ST> source model type
 * @param <DT> destination model type
 * @author Roman Mazur (IO-52)
 */
public interface Transformer<ST extends Model<?>, DT extends Model<?>> {

  /**
   * @param source source model
   * @return destination model
   */
  DT transform(final ST source);
  
}
