/**
 * 
 */
package org.mazur.algedit.actions;

import javax.swing.AbstractAction;

/**
 * Abstract editor action.
 * @author Roman Mazur (IO-52)
 *
 */
public abstract class EditorAction extends AbstractAction {

  /** serialVersionUID. */
  private static final long serialVersionUID = -3229045768495391714L;
  
  /** Mediator. */
  private MainMediator mediator;

  /**
   * @param mediator the mediator to set
   */
  public final void setMediator(final MainMediator mediator) {
    this.mediator = mediator;
  }
  
  /** 
   * Constructor.
   * @param name action name
   * @param mediator mediator to set
   */
  public EditorAction(final String name, final MainMediator mediator) {
    super(name);
    setMediator(mediator);
  }
  
  /**
   * @return mediator
   */
  protected MainMediator getMediator() {
    return mediator;
  }
}
