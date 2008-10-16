/**
 * 
 */
package org.mazur.algedit.alg.model;

import java.io.IOException;
import java.io.ObjectOutputStream;

import org.mazur.algedit.Model;
import org.mazur.algedit.ModelType;
import org.mazur.algedit.gui.AlgEditor;
import org.mazur.algedit.gui.ModelPanel;

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
  public AlgorithmModel(final String name) {
    super(name);
    BeginVertex bv = new BeginVertex();
    EndVertex ev = new EndVertex();
    ev.setNumber(1);
    bv.setStraightVertex(ev);
    size = 2;
    setMainObject(bv);
  }
  
  /**
   * Constructor.
   * @param am algorithm matrix
   */
  public AlgorithmModel(final String name, final AlgorithmMatrix am) { 
    super(name, am);
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

  @Override
  public ModelPanel<? extends Model<BeginVertex>> createPanel() {
    return new AlgEditor(this);
  }

}
