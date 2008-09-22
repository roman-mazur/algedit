/**
 * 
 */
package org.mazur.algedit.actions;

import java.awt.event.ActionEvent;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class NullAction extends EditorAction {

  /** serialVersionUID. */
  private static final long serialVersionUID = 2967732319514542534L;

  /**
   * {@inheritDoc}
   */
  public NullAction(final String name, final MainMediator mediator) {
    super(name, mediator);
  }

  /**
   * {@inheritDoc}
   */
  public void actionPerformed(final ActionEvent e) {
  }

}
