/**
 * 
 */
package org.mazur.algedit.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;

import org.mazur.algedit.LogEngine;
import org.mazur.algedit.Logger;

/**
 * Save action.
 * @author Roman Mazur (IO-52)
 *
 */
public class SaveAsAction extends EditorAction {

  /** serialVersionUID. */
  private static final long serialVersionUID = -9122259336874890270L;

  /** Logger. */
  private Logger log = LogEngine.getLogger(SaveAsAction.class);
  
  public SaveAsAction(MainMediator mediator) {
    super("Save as...", mediator);
  }

  /**
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  public void actionPerformed(final ActionEvent e) {
    try {
      getMediator().saveFile();
    } catch (IOException ioe) {
      log.error("Cannot save file.", ioe);
    }
  }

}
