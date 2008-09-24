/**
 * 
 */
package org.mazur.algedit.components;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.JTextComponent;

/**
 * Editor panel.
 * @author Roman Mazur (IO-52)
 *
 */
public class Editor extends JPanel {
  
  /** serialVersionUID. */
  private static final long serialVersionUID = 1776878022825927215L;
  
  /** Text component. */
  private JTextComponent textComponent = null;
  
  /** Constructor. */
  public Editor() {
    super(true);
    textComponent = new JTextPane();
    textComponent.setDragEnabled(true);
    
    setLayout(new BorderLayout());
    add(BorderLayout.CENTER, textComponent);
  }
  
  public void setContent(final AlgorithmContent content) {
    textComponent.setDocument(content.getDoc());
    content.setEditor(this);
  }
  
  public int getCurrentPosition() {
    return textComponent.getCaretPosition();
  }
  
  public void setCurrentPosition(final int pos) {
    textComponent.setCaretPosition(pos);
  }
}
