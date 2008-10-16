package org.mazur.algedit.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.mazur.algedit.Model;
import org.mazur.algedit.ModelType;
import org.mazur.algedit.alg.model.AlgorithmModel;
import org.mazur.algedit.gui.EditorFrame;
import org.mazur.algedit.gui.ModelPanel;

/**
 * Main mediator for the algorithm editor.
 * @author Roman Mazur (IO-52)
 *
 */
public class MainMediator {

  /** "Noname". */
  private static final String NONAME = "noname";
  
  /** Index for new documents. */
  private int documentsIndex = 0;
  
  /** Editor frame. */
  private EditorFrame editorFrame;
  
  /**
   * Sets file filters.
   * @param fc file chooser
   */
  private static void initFileChooser(final JFileChooser fc) {
    for (ModelType mt : ModelType.values()) {
      fc.addChoosableFileFilter(mt.getFilter());
    }
  }
  
  /**
   * Creates the mediator.
   */
  public MainMediator() { super(); }
  
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
    log("Creating new document...");
    String name = MainMediator.NONAME + (++documentsIndex) + "." 
      + ModelType.ALGORITHM.getDefaultExtension();
    AlgorithmModel model = new AlgorithmModel(name);
    editorFrame.addModelTab(model.createPanel());
  }
  
  public void log(final String text) {
    editorFrame.log(text);
  }
  
  public void error(final String msg) {
    editorFrame.log("ERROR: " + msg);
  }
  
  public void transformCurrentAlg() {
//    AlgorithmContent ac = editorFrame.getCurrentEditor().getContent();
//    List<MiliVertex> miliSet = new GraphTransformer().toMiliGraph(ac.getBuilder().getBeginVertex());
//    GraphPanel gp = new GraphPanel(miliSet);
//    editorFrame.openNewTab(gp, "Mili Machine");
//    log(miliSet.toString());
  }
  
  /**
   * Opens the model file.
   * @throws IOException exception
   */
  public void openFile() throws IOException {
    JFileChooser fc = new JFileChooser();
    MainMediator.initFileChooser(fc);
    int res = fc.showOpenDialog(editorFrame);
    if (res == JFileChooser.APPROVE_OPTION) {
      File f = fc.getSelectedFile();
      Model<?> m = Model.load(f.getName(), new FileInputStream(f), ModelType.byFile(f));
      editorFrame.addModelTab(m.createPanel());
    }
  }
  
  /**
   * Saves the model file.
   * @throws IOException exception
   */
  public void saveFile() throws IOException {
    ModelPanel<? extends Model<?>> mp = editorFrame.getCurrentPanel();
    if (mp == null) { return; }
    JFileChooser fc = new JFileChooser();
    MainMediator.initFileChooser(fc);
    int res = fc.showSaveDialog(editorFrame);
    if (res == JFileChooser.APPROVE_OPTION) {
      File f = fc.getSelectedFile();
      mp.getModel().save(f);
      editorFrame.renew();
    }  
  }
}
