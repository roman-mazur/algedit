package org.mazur.algedit.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;

import org.mazur.algedit.Model;
import org.mazur.algedit.ModelType;
import org.mazur.algedit.Transformer;
import org.mazur.algedit.alg.model.AlgorithmModel;
import org.mazur.algedit.gui.EditorFrame;
import org.mazur.algedit.gui.ModelPanel;
import org.mazur.algedit.transformers.GraphTransformer;

/**
 * Main mediator for the algorithm editor.
 * @author Roman Mazur (IO-52)
 *
 */
@SuppressWarnings("unchecked")
public class MainMediator {

  /** "Noname". */
  private static final String NONAME = "noname";
  
  private static final Map<Class, List> TRANSFORMERS = 
    new HashMap<Class, List>();
  static {
    List list = new LinkedList<Transformer<Model<?>,Model<?>>>();
    list.add(new GraphTransformer());
    TRANSFORMERS.put(AlgorithmModel.class, list);
  }
  
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
  
  public void transform() {
    ModelPanel<? extends Model<?>> mp = editorFrame.getCurrentPanel();
    List list = MainMediator.TRANSFORMERS.get(mp.getModel().getClass());
    if (list == null || list.size() == 0) { return; }
    Transformer<Model<?>, Model<?>> transformer = (Transformer<Model<?>, Model<?>>)list.get(0);
    Model<?> result = transformer.transform(mp.getModel());
    editorFrame.addModelTab(result.createPanel());
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
