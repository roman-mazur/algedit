/**
 * 
 */
package org.mazur.algedit.transtable.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import org.mazur.algedit.Model;
import org.mazur.algedit.ModelType;
import org.mazur.algedit.gui.ModelPanel;
import org.mazur.algedit.gui.TransTablePanel;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class TransTableModel extends Model<List<TableRow>> {

  public TransTableModel(final String name) {
    super(name);
  }
  public TransTableModel(final String name, final TableSerializer sm) {
    super(name, sm);
  }

  @Override
  public ModelPanel<? extends Model<List<TableRow>>> createPanel() {
    return new TransTablePanel(this);
  }

  @Override
  public ModelType getType() {
    return ModelType.TRANS_TABLE;
  }

  @Override
  protected void save(final ObjectOutputStream stream) throws IOException {
    stream.writeObject(new TableSerializer(getMainObject()));
  }

}
