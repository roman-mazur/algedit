package org.mazur.algedit.actions;

import java.awt.event.ActionEvent;

public class TransformAction extends EditorAction {

  /** serialVersionUID. */
  private static final long serialVersionUID = 1687931006205393578L;

  public TransformAction(final MainMediator mediator) {
    super("Transform", mediator);
  }

  public void actionPerformed(final ActionEvent e) {
    getMediator().transformCurrentAlg();
  }

}
