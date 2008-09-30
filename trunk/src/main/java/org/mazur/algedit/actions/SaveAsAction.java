/**
 * 
 */
package org.mazur.algedit.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

/**
 * Save action.
 * @author Roman Mazur (IO-52)
 *
 */
public class SaveAsAction extends EditorAction {

  /** serialVersionUID. */
  private static final long serialVersionUID = -9122259336874890270L;

  public SaveAsAction(MainMediator mediator) {
    super("Save as...", mediator);
  }

  /**
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  public void actionPerformed(final ActionEvent e) {
    JFileChooser fc = new JFileChooser();
    int res = fc.showSaveDialog(getMediator().getEditorFrame());
    if (res == JFileChooser.APPROVE_OPTION) {
      File f = fc.getSelectedFile();
      getMediator().saveAlgorithm(f);
    }
  }

}
