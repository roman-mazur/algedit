/**
 * 
 */
package org.mazur.algedit.actions;

import java.awt.event.ActionEvent;

/**
 * Create a new algorithm.
 * @author Roman Mazur (IO-52)
 *
 */
public class NewAction extends EditorAction {

  /** serialVersionUID. */
  private static final long serialVersionUID = -7484055858388732145L;

  /**
   * {@inheritDoc}
   */
  public NewAction(final MainMediator mediator) {
    super("New", mediator);
  }

  /**
   * {@inheritDoc}
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  public void actionPerformed(final ActionEvent e) {
    getMediator().createNewAlgorithm();
  }

}
