/**
 * 
 */
package org.mazur.algedit;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

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

import org.mazur.algedit.actions.MainMediator;
import org.mazur.algedit.actions.NewAction;

/**
 * Main frame of the editor.
 * @author Roman Mazur (IO-52)
 *
 */
public class EditorFrame extends JFrame {
  
  /** Standard frame dimension. */
  private static final Dimension STANDARD_DIMENSION = new Dimension(600, 500);
  
  /** Menu items. */
  private final String[] menus = {"File", "Edit", "Help"};

  /** Actions mediator. */
  private MainMediator mediator = null;
  /** Menu subitems. */
  private final Action[][] menuItems = {
    {new NewAction(mediator), 
     null, 
     null, 
     null},
     
    {null,
     null,
     null},
     
    {null}
  };
  
  /** Caption. */
  private String caption;
  
  /** Tabs for documents. */
  private JTabbedPane documentTabs = new JTabbedPane();
  
  /** Log area. */
  private JTextArea logArea = new JTextArea();
  
  /**
   * Constructor.
   */
  public EditorFrame(final String caption) {
    this.caption = caption;
    this.mediator = new MainMediator();
    this.mediator.setEditorFrame(this);
    setTitle(caption);
    
    Container pane = getContentPane();
    pane.setLayout(new BorderLayout());
    
    JSplitPane splitter = new JSplitPane();
    splitter.setOrientation(JSplitPane.VERTICAL_SPLIT);
    
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    mainPanel.add(BorderLayout.NORTH, buildMenu());
    mainPanel.add(BorderLayout.CENTER, documentTabs);
    mainPanel.setSize(EditorFrame.STANDARD_DIMENSION);
    
    JScrollPane logPane = new JScrollPane();
    logPane.getViewport().add(logArea);
    
    splitter.setTopComponent(mainPanel);
    splitter.setBottomComponent(logPane);
    splitter.setOneTouchExpandable(true);
    pane.add("Center", splitter);
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
   * Creates new document.
   */
  public void createNew() {
    
  }
}
