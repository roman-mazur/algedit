/**
 * 
 */
package org.mazur.algedit.gui;

import java.awt.BorderLayout;

import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.JTextComponent;

import org.mazur.algedit.alg.model.AlgorithmModel;

/**
 * Editor panel.
 * @author Roman Mazur (IO-52)
 *
 */
public class AlgEditor extends ModelPanel<AlgorithmModel> {
  
  /** serialVersionUID. */
  private static final long serialVersionUID = 1776878022825927215L;
  
  /** Text component. */
  private JTextComponent textComponent = null;
  /** Content. */
  private AlgorithmContent content;
  
  
  /** Constructor. */
  public AlgEditor(final AlgorithmModel model) {
    super(model);
    textComponent = new JTextPane();
    textComponent.setDragEnabled(true);
    
    setLayout(new BorderLayout());
    add(BorderLayout.CENTER, textComponent);

    this.content = new AlgorithmContent(new DefaultStyledDocument(), model);
    textComponent.setDocument(content.getDoc());
    content.setEditor(this);
  }
  
  /**
   * @return the content
   */
  public final AlgorithmContent getContent() {
    return content;
  }

  public int getCurrentPosition() {
    return textComponent.getCaretPosition();
  }
  
  public void setCurrentPosition(final int pos) {
    textComponent.setCaretPosition(pos);
  }

  @Override
  public String getShortName() {
    return "&";
  }
}
