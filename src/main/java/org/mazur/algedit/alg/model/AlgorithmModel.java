/**
 * 
 */
package org.mazur.algedit.alg.model;

import java.io.IOException;
import java.io.ObjectOutputStream;

import org.mazur.algedit.Model;
import org.mazur.algedit.ModelType;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class AlgorithmModel extends Model<BeginVertex> {

  /** Size. */
  private int size;
  
  /**
   * Constructor.
   */
  public AlgorithmModel(final AlgorithmMatrix am) { 
    super(am);
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
   * {@inheritDoc}
   */
  @Override
  public void save(final ObjectOutputStream stream) throws IOException {
    stream.writeObject(new AlgorithmMatrix(getMainObject(), size));
  }

}
