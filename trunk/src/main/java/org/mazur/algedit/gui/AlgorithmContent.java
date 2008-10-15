/**
 * 
 */
package org.mazur.algedit.gui;

import java.awt.Color;
import java.io.File;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import org.mazur.algedit.actions.MainMediator;
import org.mazur.algedit.alg.AbstractVertex;
import org.mazur.algedit.alg.StructureBuilder;
import org.mazur.algedit.alg.ValidationType;
import org.mazur.algedit.exceptions.RedrawParseException;
import org.mazur.parser.Machine;
import org.mazur.parser.ParseException;
import org.mazur.parser.Parser;

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
                             ARROW_STYLE = "arrow",
                             ERROR_STYLE = "error";

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
    StyleConstants.setFontSize(error, (Integer)base.getAttribute(StyleConstants.FontSize) + 2);
    
    Style unreach = AlgorithmContent.STYLES.addStyle(ValidationType.UNREACHABLE.toString(), base);
    StyleConstants.setBold(unreach, true);
    StyleConstants.setItalic(unreach, true);
    StyleConstants.setForeground(unreach, Color.MAGENTA);
    StyleConstants.setFontSize(error, (Integer)base.getAttribute(StyleConstants.FontSize) + 2);
  }
  
  /** Document. */
  private DefaultStyledDocument doc;

  /** Mediator. */
  private MainMediator mediator;
  
  /** String to parse. */
  private StringBuilder parsingString = new StringBuilder();
  
  /** Parser. */
  private Parser parser = null;
  /** Builder. */
  private StructureBuilder builder = null;
  /** Analyzer. */
  private Analyzer analyzer = new Analyzer();
  
  /** Last edit time. */
  private long lastEditTime = System.currentTimeMillis();
  /** Change flag. */
  private boolean changed = false;
  private boolean internalChange = false;
  
  private Editor editor;
  
  private File source;
  
  /**
   * @param doc document
   * @param mediator main mediator
   */
  public AlgorithmContent(final DefaultStyledDocument doc, final MainMediator mediator) {
    this.doc = doc;
    this.mediator = mediator;
    this.doc.addDocumentListener(this);
    Machine m = this.mediator.getMachine();
    this.parser = new Parser("UTF-8");
    this.parser.setMachine(m);
    this.builder = (StructureBuilder)m.getBuilder();
    
    this.analyzer.setContent(this);
    new Thread(this.analyzer).start();
    this.mediator.log("Analyzer started.");
  }
  
  /**
   * @return the lastEditTime
   */
  public final long getLastEditTime() {
    return lastEditTime;
  }

  /**
   * @return the changed
   */
  public final boolean isChanged() {
    return changed;
  }

  /**
   * Adds the text to the document.
   * @param content para content
   */
  public void addText(final String content) {
    internalChange = true;
    try {
      doc.insertString(doc.getLength(), content, 
          AlgorithmContent.STYLES.getStyle(AlgorithmContent.BASIC_STYLE));
    } catch (BadLocationException e) {
      e.printStackTrace();
    }
    internalChange = false;
  }
  
  /**
   * @param offset offset
   * @param length length
   * @param style style
   */
  public void changeStyle(final int offset, int length, final String style) {
    internalChange = true;
    try {
      String text = doc.getText(offset, length);
      int p = editor.getCurrentPosition();
      doc.remove(offset, length);
      Style s = AlgorithmContent.STYLES.getStyle(style);
      if (s == null) { s = AlgorithmContent.STYLES.getStyle(AlgorithmContent.BASIC_STYLE); }
      doc.insertString(offset, text, s);
      editor.setCurrentPosition(p);
    } catch (BadLocationException e) {
      e.printStackTrace();
    }
    internalChange = false;
  }
  
  /**
   * @return the document
   */
  public final DefaultStyledDocument getDoc() { return doc; }

  private void processException(final ParseException e) {
    String msg = e.getDetailMaeesage();
    if (msg != null) {
      mediator.error(msg);
      changeStyle(0, doc.getLength(), AlgorithmContent.ERROR_STYLE);
      return;
    }
    int o = e.getOffset();
    if (e.getCharacter() == ' ') { o--; }
    changeStyle(o, 1, AlgorithmContent.ERROR_STYLE);
    mediator.error(e.getMessage());
  }
  
  private void markVertex(final AbstractVertex av, final String text) throws BadLocationException {
    int i = av.getSymbolIndex() + 1;
    while (i < text.length() && text.charAt(i) >= '0' && text.charAt(i) <= '9') { i++; }
    changeStyle(av.getSymbolIndex(), i - av.getSymbolIndex(), av.getValidationType().toString());
  }
  
  private void processRedraw(final RedrawParseException e) {
    mediator.error("Algorithm mistakes are found!!!!");
    try {
      String text = doc.getText(0, doc.getLength());
      for (AbstractVertex av : e.getRedrawList()) {
        markVertex(av, text);
      }
    } catch (BadLocationException ee) {
      ee.printStackTrace();
    }
  }
  
  public synchronized void check() {
    changed = false;
    try {
      changeStyle(0, doc.getLength(), AlgorithmContent.BASIC_STYLE);
      parser.parse(doc.getText(0, doc.getLength()) + " ");
      //System.out.println(new Drawer(builder.getBeginVertex()).draw());
    } catch (ParseException e) {
      processException(e);
    } catch (RedrawParseException e) {
      processRedraw(e); 
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    
  }
  
  private void touch() {
    if (internalChange) { return; }
    changed = true;
    lastEditTime = System.currentTimeMillis();
  }
  
  private void addToParsingString(final int offset, final char c) {
    parsingString.insert(offset, c);
  }

  private void removeFromParsingString(final int offset, final int length) {
    parsingString.delete(offset, offset + length);
  }
  
  /**
   * {@inheritDoc}
   */
  public void changedUpdate(final DocumentEvent e) {
    mediator.log(parsingString.toString() + " e ");
  }

  /**
   * {@inheritDoc}
   */
  public void insertUpdate(final DocumentEvent e) {
    String t;
    try {
      t = e.getDocument().getText(e.getOffset(), e.getLength());
    } catch (BadLocationException ex) {
      throw new RuntimeException(ex);
    }
    
    for (int i = 0; i < t.length(); i++) {
      addToParsingString(e.getOffset() + i, t.charAt(i));
    }
    touch();
  }

  /**
   * {@inheritDoc}
   */
  public void removeUpdate(final DocumentEvent e) {
    removeFromParsingString(e.getOffset(), e.getLength());
    touch();
  }
  
  public void destroy() {
    this.analyzer.setStopped(true);
  }
  
  public void setEditor(final Editor editor) {
    this.editor = editor;
  }

  /**
   * @return the source
   */
  public final File getSource() {
    return source;
  }

  /**
   * @param source the source to set
   */
  public final void setSource(File source) {
    this.source = source;
  }

  /**
   * @return the builder
   */
  public final StructureBuilder getBuilder() {
    return builder;
  }

}
