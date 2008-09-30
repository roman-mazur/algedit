/**
 * 
 */
package org.mazur.algedit.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class OpenAction extends EditorAction {

  /** serialVersionUID. */
  private static final long serialVersionUID = 5670186904083133462L;

  public OpenAction(MainMediator mediator) {
    super("Open...", mediator);
  }

  /**
   * {@inheritDoc}
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  public void actionPerformed(final ActionEvent e) {
    JFileChooser fc = new JFileChooser();
    int res = fc.showOpenDialog(getMediator().getEditorFrame());
    if (res == JFileChooser.APPROVE_OPTION) {
      File f = fc.getSelectedFile();
      getMediator().loadAlgorithm(f);
    }

  }

}
