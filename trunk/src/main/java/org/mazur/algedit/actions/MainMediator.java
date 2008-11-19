package org.mazur.algedit.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.mazur.algedit.Model;
import org.mazur.algedit.ModelType;
import org.mazur.algedit.Transformer;
import org.mazur.algedit.alg.model.AlgorithmModel;
import org.mazur.algedit.exceptions.TransformException;
import org.mazur.algedit.gui.EditorFrame;
import org.mazur.algedit.gui.ModelPanel;
import org.mazur.algedit.mili.model.MiliGraphModel;
import org.mazur.algedit.transformers.BoolFunctionsTransformer;
import org.mazur.algedit.transformers.GraphTransformer;
import org.mazur.algedit.transformers.NeighboringCoding;
import org.mazur.algedit.transformers.TransTableTransformer;
import org.mazur.algedit.transtable.model.TransTableModel;

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
    list = new LinkedList<Transformer<Model<?>,Model<?>>>();
    list.add(new NeighboringCoding());
    list.add(new TransTableTransformer());
    TRANSFORMERS.put(MiliGraphModel.class, list);
    list = new LinkedList<Transformer<Model<?>,Model<?>>>();
    list.add(new BoolFunctionsTransformer());
    TRANSFORMERS.put(TransTableModel.class, list);
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
    Transformer<Model<?>, Model<?>> transformer = null;
    if (list.size() == 1) {
      transformer = (Transformer<Model<?>, Model<?>>)list.get(0);
    } else {
      String[] options = new String[list.size()];
      int i = 0;
      for (Object o : list) { options[i++] = ((Transformer<Model<?>, Model<?>>)o).getName(); }
      i = JOptionPane.showOptionDialog(editorFrame, "What transformer would you choose?", 
          "Transform", 
          JOptionPane.DEFAULT_OPTION, 
          JOptionPane.QUESTION_MESSAGE, 
          null, 
          options, 
          options[0]);
      transformer = (Transformer<Model<?>, Model<?>>)list.get(i);
    }
     
    log("Transforming operation: " + transformer.getName());
    try {
      editorFrame.addModelTab(transformer.transform(mp.getModel()).createPanel());
    } catch (TransformException e) {
      error(e.getMessage());
    }
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
      if (!mp.getModel().getType().getFilter().accept(f)) {
        f = new File(f.getParentFile(), f.getName() + "." + mp.getModel().getType().getDefaultExtension());
      }
      mp.getModel().save(f);
      editorFrame.renew();
    }  
  }
}
