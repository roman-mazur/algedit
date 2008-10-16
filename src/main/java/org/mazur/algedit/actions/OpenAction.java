/**
 * 
 */
package org.mazur.algedit.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;

import org.mazur.algedit.LogEngine;
import org.mazur.algedit.Logger;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class OpenAction extends EditorAction {

  /** serialVersionUID. */
  private static final long serialVersionUID = 5670186904083133462L;

  /** Logger. */
  private final Logger log = LogEngine.getLogger(OpenAction.class);
  
  public OpenAction(MainMediator mediator) {
    super("Open...", mediator);
  }

  /**
   * {@inheritDoc}
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  public void actionPerformed(final ActionEvent e) {
    try {
      getMediator().openFile();
    } catch (IOException ioe) {
      log.error("Cannot open the file.", ioe);
    }
  }

}
