package org.mazur.algedit.actions;

import org.mazur.algedit.EditorFrame;

/**
 * Main mediator for the algorithm editor.
 * @author Roman Mazur (IO-52)
 *
 */
public class MainMediator {

  /** Editor frame. */
  private EditorFrame editorFrame;
  
  /**
   * @param editorFrame editorFrame to set
   */
  public void setEditorFrame(final EditorFrame editorFrame) {
    this.editorFrame = editorFrame;
  }
  
  /**
   * Opens the new document.
   */
  public void createNewAlgorithm() {
    editorFrame.createNew();
  }
}
