/**
 * 
 */
package org.mazur.algedit;

import java.awt.BorderLayout;
import java.awt.Container;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultStyledDocument;

import org.mazur.algedit.actions.MainMediator;
import org.mazur.algedit.actions.NewAction;
import org.mazur.algedit.actions.NullAction;
import org.mazur.algedit.components.AlgorithmContent;
import org.mazur.algedit.components.Editor;

/**
 * Main frame of the editor.
 * @author Roman Mazur (IO-52)
 *
 */
public class EditorFrame extends JFrame {
  
  /** "Noname". */
  private static final String NONAME = "noname";
  
  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("[dd-MM-yyyy hh:mm:ss]");
  
  /** Menu items. */
  private final String[] menus = {"File", "Edit", "Help"};

  /** Actions mediator. */
  private MainMediator mediator = new MainMediator();
  /** Menu subitems. */
  private final Action[][] menuItems = {
    {new NewAction(mediator), 
     new NullAction("Save", null), 
     new NullAction("Save as", null), 
     new NullAction("Open", null)},
     
    {new NullAction("Copy", null),
     new NullAction("Paste", null),
     new NullAction("Cut", null)},
     
    {new NullAction("About", null)}
  };
  
  /** Caption. */
  private String caption;
  
  /** Tabs for documents. */
  private JTabbedPane documentTabs = new JTabbedPane();
  
  /** Log area. */
  private JTextArea logArea = new JTextArea();
  
  /** Index for new documents. */
  private int documentsIndex = 0;
  
  /**
   * Constructor.
   */
  public EditorFrame(final String caption) {
    this.caption = caption;
    this.mediator.setEditorFrame(this);
    setTitle(caption);
    
    Container pane = getContentPane();
    pane.setLayout(new BorderLayout());
    
    JSplitPane splitter = new JSplitPane();
    splitter.setOrientation(JSplitPane.VERTICAL_SPLIT);
    
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    mainPanel.add(BorderLayout.CENTER, documentTabs);
    
    JScrollPane logPane = new JScrollPane();
    logPane.getViewport().add(logArea);
    
    splitter.setTopComponent(mainPanel);
    splitter.setBottomComponent(logPane);
    splitter.setOneTouchExpandable(true);
    
    pane.add(BorderLayout.NORTH, buildMenu());
    pane.add(BorderLayout.CENTER, splitter);
  }
  
  private JMenuBar buildMenu() {
    JMenuBar result = new JMenuBar();
    int index = 0;
    for (String menuName : menus) {
      JMenu menu = new JMenu(menuName);
      for (Action a : menuItems[index]) {
        JMenuItem item = new JMenuItem();
        item.setAction(a);
        menu.add(item);
      }
      result.add(menu);
      index++;
    }
    return result;
  }
  
  /**
   * @param msg message to write
   */
  public void log(final String msg) {
    Date now = new Date();
    String old = logArea.getText();
    logArea.setText(old + EditorFrame.DATE_FORMAT.format(now) + " --> " + msg + "\n");
  }
  
  /**
   * Creates new document.
   */
  public void createNew() {
    String name = EditorFrame.NONAME + (++documentsIndex);
    Editor e = new Editor();
    e.setContent(new AlgorithmContent(new DefaultStyledDocument(), mediator));
    documentTabs.add(name, e);
  }
}
