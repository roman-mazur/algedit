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
import org.mazur.algedit.gui.GraphPanel;
import org.mazur.algedit.gui.ModelPanel;

/**
 * Graph model of the finite machine.
 * @author Roman Mazur (IO-52)
 *
 */
public class MiliGraphModel extends Model<List<MiliVertex>> {

  /**
   * {@inheritDoc}
   */
  public MiliGraphModel(final String name) {
    super(name);
  }
  
  /**
   * {@inheritDoc}
   */
  public MiliGraphModel(final String name, 
      final SerializeableMatrix<List<MiliVertex>> sm) {
    super(name, sm);
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

  @Override
  public ModelPanel<? extends Model<List<MiliVertex>>> createPanel() {
    return new GraphPanel(this);
  }

  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
      return null;
    }
  }
}
