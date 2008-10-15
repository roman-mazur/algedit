/**
 * 
 */
package org.mazur.algedit.alg;

import java.io.IOException;
import java.io.ObjectOutputStream;

import org.mazur.algedit.Model;
import org.mazur.algedit.ModelType;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class AlgorithmModel extends Model {

  /** Begin vertex. */
  private BeginVertex beginVertex;
  
  /** Size. */
  private int size;
  
  /**
   * Constructor.
   */
  public AlgorithmModel(final Object savedObject) { 
    beginVertex = ((AlgorithmMatrix)savedObject).buildAlgorithm();
  }

  /**
   * @param size the size to set
   */
  public final void setSize(final int size) {
    this.size = size;
  }

  /**
   * {@inheritDoc}
   * @see org.mazur.algedit.Model#getType()
   */
  @Override
  public final ModelType getType() {
    return ModelType.ALGORITHM;
  }

  /**
   * @return the beginVertex
   */
  public final BeginVertex getBeginVertex() {
    return beginVertex;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void save(final ObjectOutputStream stream) throws IOException {
    stream.writeObject(new AlgorithmMatrix(beginVertex, size));
  }

}
