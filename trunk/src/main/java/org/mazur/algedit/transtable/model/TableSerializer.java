/**
 * 
 */
package org.mazur.algedit.transtable.model;

import java.util.List;

import org.mazur.algedit.SerializeableMatrix;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class TableSerializer implements SerializeableMatrix<List<TableRow>> {

  private static final long serialVersionUID = 1407220945514720829L;

  private List<TableRow> rows;
  
  public TableSerializer(final List<TableRow> rows) {
    this.rows = rows;
  }
  
  public List<TableRow> build() { return this.rows; }

}
