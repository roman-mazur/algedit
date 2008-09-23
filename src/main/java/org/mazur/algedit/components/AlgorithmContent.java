/**
 * 
 */
package org.mazur.algedit.components;

import java.awt.Color;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import org.mazur.algedit.actions.MainMediator;

/**
 * Algorithm context.
 * @author Roman Mazur (IO-52)
 *
 */
public class AlgorithmContent implements DocumentListener {

  /** Font family. */
  private static final String FONT_FAMILY = "Courier";
  /** Font size. */
  private static final int FONT_SIZE = 12;
  
  /** Styles. */
  public static final String NONE_STYLE = "none",
                             BASIC_STYLE = "basic",
                             ARROW_STYLE = "style",
                             ERROR_STYLE = "error";
  
  /** Document. */
  private DefaultStyledDocument doc;

  /** Mediator. */
  private MainMediator mediator;
  
  /** Styles. */
  private final static StyleContext STYLES = new StyleContext();
  static {
    Style none = AlgorithmContent.STYLES.addStyle("none", null);
    
    Style base = AlgorithmContent.STYLES.addStyle("base", none);
    StyleConstants.setFontFamily(base, AlgorithmContent.FONT_FAMILY);
    StyleConstants.setFontSize(base, AlgorithmContent.FONT_SIZE);
    
    Style arrow = AlgorithmContent.STYLES.addStyle("arrow", base);
    StyleConstants.setBold(arrow, true);
    
    Style error = AlgorithmContent.STYLES.addStyle("error", base);
    StyleConstants.setBold(error, true);
    StyleConstants.setForeground(error, Color.RED);
  }
  
  /**
   * @param doc document
   * @param STYLES styles
   */
  public AlgorithmContent(final DefaultStyledDocument doc, final MainMediator mediator) {
    this.doc = doc;
    this.mediator = mediator;
    this.doc.addDocumentListener(this);
  }
  
  /**
   * Adds the text to the document.
   * @param content para content
   */
  public void addText(final String content) {
    try {
      doc.insertString(doc.getLength(), content, AlgorithmContent.STYLES.getStyle("base"));
    } catch (BadLocationException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * @param offset offset
   * @param length length
   * @param style style
   */
  public void changeStyle(final int offset, int length, final String style) {
    try {
      String text = doc.getText(offset, length);
      doc.remove(offset, length);
      doc.insertString(offset, text, AlgorithmContent.STYLES.getStyle(style));
    } catch (BadLocationException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * @return the document
   */
  public final DefaultStyledDocument getDoc() { return doc; }

  
  /**
   * {@inheritDoc}
   */
  public void changedUpdate(final DocumentEvent e) {
    // TODO Auto-generated method stub
    
  }

  /**
   * {@inheritDoc}
   */
  public void insertUpdate(final DocumentEvent e) {
    // TODO Auto-generated method stub
    
  }

  /**
   * {@inheritDoc}
   */
  public void removeUpdate(final DocumentEvent e) {
    // TODO Auto-generated method stub
    
  }
}
