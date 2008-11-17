/**
 * 
 */
package org.mazur.algedit.gui;

import java.awt.BorderLayout;
import java.util.Arrays;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.mazur.algedit.transtable.model.TableRow;
import org.mazur.algedit.transtable.model.TransTableModel;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class TransTablePanel extends ModelPanel<TransTableModel> {

  private static final long serialVersionUID = -6708197071004466705L;

  private JTable table = new JTable();
  
  /**
   * Constructor.
   * @param model model
   */
  public TransTablePanel(final TransTableModel model) {
    super(model);
    TableModel tModel = new AbstractTableModel() {
      private static final long serialVersionUID = 2492650966798667156L;
      private final String[] captions = {"From", "To", "From - code", "To - code", 
          "Conditions", "Operator signals", "RS"};
      public int getColumnCount() { return 7; }
      public int getRowCount() { return model.getMainObject().size() + 1; }
      public Object getValueAt(final int rowIndex, final int columnIndex) {
        int ri = rowIndex - 1;
        if (ri < 0) { return captions[columnIndex]; }
        TableRow row = model.getMainObject().get(ri);
        switch (columnIndex) {
        case 0: return row.getNameFrom();
        case 1: return row.getNameTo();
        case 2: return Integer.toBinaryString(row.getCodeFrom());
        case 3: return Integer.toBinaryString(row.getCodeTo());
        case 4: return row.getCondString();
        case 5: return Arrays.toString(row.getOperatorSignals());
        case 6: return Arrays.toString(row.getRsFunctions());
        default:
          return null;
        }
      }
    };
    table.setModel(tModel);
    setLayout(new BorderLayout());
    add(BorderLayout.CENTER, table);
  }

  @Override
  public Icon getIcon() {
    return null;
  }

}
