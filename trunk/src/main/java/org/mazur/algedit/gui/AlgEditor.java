/**
 * 
 */
package org.mazur.algedit.gui;

import java.awt.BorderLayout;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.JTextComponent;

import org.mazur.algedit.alg.model.AlgorithmModel;
import org.mazur.algedit.alg.utils.AlgorithmDrawer;

/**
 * Editor panel.
 * @author Roman Mazur (IO-52)
 *
 */
public class AlgEditor extends ModelPanel<AlgorithmModel> {
  
  /** serialVersionUID. */
  private static final long serialVersionUID = 1776878022825927215L;
  
  /** Icon. */
  private static Icon icon = null; 
  static {
    URL url = AlgEditor.class.getResource("alg.gif");
    icon = new ImageIcon(url);
  }
  
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
    
    AlgorithmDrawer drawer = new AlgorithmDrawer(model.getMainObject());
    content.addText(drawer.draw());
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
  public Icon getIcon() {
    return AlgEditor.icon;
  }
}
