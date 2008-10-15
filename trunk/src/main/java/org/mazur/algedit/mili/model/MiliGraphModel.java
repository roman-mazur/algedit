/**
 * 
 */
package org.mazur.algedit.mili.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import org.mazur.algedit.Model;
import org.mazur.algedit.ModelType;
import org.mazur.algedit.SerializeableMatrix;

/**
 * Graph model of the finite machine.
 * @author Roman Mazur (IO-52)
 *
 */
public class MiliGraphModel extends Model<List<MiliVertex>> {

  /**
   * {@inheritDoc}
   */
  public MiliGraphModel(final SerializeableMatrix<List<MiliVertex>> sm) {
    super(sm);
  }

  /**
   * {@inheritDoc}
   * @see org.mazur.algedit.Model#getType()
   */
  @Override
  public ModelType getType() {
    return ModelType.GRAPH;
  }

  /**
   * {@inheritDoc}
   * @see org.mazur.algedit.Model#save(java.io.ObjectOutputStream)
   */
  @Override
  protected void save(final ObjectOutputStream stream) throws IOException {
    stream.writeObject(new MiliMatrix(getMainObject()));
  }

}
