/**
 * 
 */
package org.mazur.algedit.mili.model;

import java.util.List;

import org.mazur.algedit.SerializeableMatrix;

/**
 * Matrixes to represent the Mili graph.
 * @author Roman Mazur (IO-52)
 *
 */
public class MiliMatrix implements SerializeableMatrix<List<MiliVertex>> {

  /** serialVersionUID. */
  private static final long serialVersionUID = 2151862358375652038L;

  private List<MiliVertex> vertexes;
  
  /**
   * Constructor.
   * @param vertexes machine
   */
  public MiliMatrix(final List<MiliVertex> vertexes) {
    this.vertexes = vertexes;
  }
  
  /**
   * {@inheritDoc}
   */
  public List<MiliVertex> build() {
    return vertexes;
  }

}
