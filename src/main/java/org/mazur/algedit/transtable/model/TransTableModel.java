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
import org.mazur.algedit.transformers.NeighboringCoding;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class TransTableModel extends Model<List<TableRow>> {

  private int codeDigitsCount;
  
  /**
   * @return the codeDigitsCount
   */
  public final int getCodeDigitsCount() {
    return codeDigitsCount;
  }
  /**
   * @param codeDigitsCount the codeDigitsCount to set
   */
  public final void setCodeDigitsCount(int codeDigitsCount) {
    this.codeDigitsCount = codeDigitsCount;
  }
  
  public TransTableModel(final String name) {
    super(name);
  }
  public TransTableModel(final String name, final TableSerializer sm) {
    super(name, sm);
    codeDigitsCount = 0;
    for (TableRow row : getMainObject()) {
      int x = NeighboringCoding.getMinDigitsCount(row.getCodeFrom());
      if (codeDigitsCount < x) { codeDigitsCount = x; }
    }
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
