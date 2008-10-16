/**
 * 
 */
package org.mazur.algedit.gui;

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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.mazur.algedit.Model;
import org.mazur.algedit.actions.MainMediator;
import org.mazur.algedit.actions.NewAction;
import org.mazur.algedit.actions.NullAction;
import org.mazur.algedit.actions.OpenAction;
import org.mazur.algedit.actions.SaveAsAction;
import org.mazur.algedit.actions.TransformAction;

/**
 * Main frame of the editor.
 * @author Roman Mazur (IO-52)
 *
 */
public class EditorFrame extends JFrame {
  
  /** serialVersionUID. */
  private static final long serialVersionUID = -7866639833323708860L;

  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("[dd-MM-yyyy hh:mm:ss]");
  
  /** Menu items. */
  private final String[] menus = {"File", "Edit", "Help"};

  /** Actions mediator. */
  private MainMediator mediator = new MainMediator();
  /** Menu subitems. */
  private final Action[][] menuItems = {
    {new NewAction(mediator), 
     new NullAction("Save", null), 
     new SaveAsAction(mediator), 
     new OpenAction(mediator)},
     
    {new NullAction("Copy", null),
     new NullAction("Paste", null),
     new NullAction("Cut", null),
     new TransformAction(mediator)},
     
    {new NullAction("About", null)}
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
    
    documentTabs.addChangeListener(new ChangeListener() {
      public void stateChanged(final ChangeEvent e) {
        changePanel(getCurrentPanel());
      }
    });
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
//    String name = EditorFrame.NONAME + (++documentsIndex);
//    AlgEditor e = new AlgEditor();
//    e.setContent();
//    documentTabs.add(name, e);
  }
  
  public void openNewTab(final String text, final String name) {
//    AlgEditor e = new AlgEditor();
//    AlgorithmContent ac = new AlgorithmContent(new DefaultStyledDocument(), mediator);
//    ac.addText(text);
//    e.setContent(ac);
//    documentTabs.add(name, e);
  }

  public void openNewTab(final JPanel panel, final String name) {
	  documentTabs.add(panel, name);
  }
  
  public AlgEditor getCurrentEditor() {
    return (AlgEditor)documentTabs.getSelectedComponent();
  }
  
  
  /**
   * @param panel panel to select
   */
  private void changePanel(final ModelPanel<? extends Model<?>> panel) {
    setTitle(this.caption + " [" + panel.getCaption() + "] " + " - " + panel.getDescription());
  }
  
  /**
   * Open the new tab for dealing with the some model.
   * @param panel model panel
   */
  public void addModelTab(final ModelPanel<? extends Model<?>> panel) {
    documentTabs.addTab(panel.getCaption(), panel.getIcon(), panel, panel.getDescription());
    changePanel(panel);
  }
  
  /**
   * @return currently selected model tab
   */
  @SuppressWarnings("unchecked")
  public ModelPanel<? extends Model<?>> getCurrentPanel() {
    return (ModelPanel)documentTabs.getSelectedComponent();
  }
  
  /**
   * Renew the frame.
   */
  public void renew() {
    changePanel(getCurrentPanel());
    documentTabs.setTitleAt(documentTabs.getSelectedIndex(), getCurrentPanel().getCaption());
  }
}
