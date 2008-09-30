package org.mazur.algedit.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import org.mazur.algedit.EditorFrame;
import org.mazur.algedit.components.AlgorithmContent;
import org.mazur.algedit.structure.AlgorithmMatrix;
import org.mazur.algedit.structure.BeginVertex;
import org.mazur.algedit.structure.StructureBuilder;
import org.mazur.parser.Machine;
import org.mazur.parser.MachineFactory;

/**
 * Main mediator for the algorithm editor.
 * @author Roman Mazur (IO-52)
 *
 */
public class MainMediator {

  /** Editor frame. */
  private EditorFrame editorFrame;
  
  /** Pool of machines. */
  private LinkedList<Machine> machinesPool = new LinkedList<Machine>();
  
  /** Initial count of parsers in the pool. */
  private final static int INITIAL_PARSERS_COUNT = 2;
  
  /**
   * Creates the mediator.
   */
  public MainMediator() {
    for (int i = 0; i < MainMediator.INITIAL_PARSERS_COUNT; i++) {
      machinesPool.add(createMachine());
    }
  }
  
  private static Machine createMachine() {
    MachineFactory mf = new MachineFactory("alg.par", new StructureBuilder());
    try {
      return mf.build();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * @return parser from the pool
   */
  public synchronized Machine getMachine() {
    if (machinesPool.isEmpty()) {
      return createMachine();
    }
    return machinesPool.getFirst();
  }
  
  /**
   * @param parser parser to release
   */
  public synchronized void releaseMachine(final Machine parser) {
    machinesPool.add(parser);
  }
  
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
    editorFrame.log("Creating new document...");
    editorFrame.createNew();
  }
  
  public void log(final String text) {
    editorFrame.log(text);
  }
  
  public void error(final String msg) {
    editorFrame.log("ERROR: " + msg);
  }
  
  public void saveAlgorithm(final File file) {
    AlgorithmContent ac = editorFrame.getCurrentEditor().getContent();
    StructureBuilder builder = ac.getBuilder();
    AlgorithmMatrix m = new AlgorithmMatrix(builder.getBeginVertex(), builder.size());
    try {
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
      out.writeObject(m);
      out.close();
      log("Saved to " + file.getAbsolutePath());
    } catch (IOException e) {
      error(e.getMessage());
    }
  }
  
  public void loadAlgorithm(final File file) {
    try {
      ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
      Object o = in.readObject();
      if (!(o instanceof AlgorithmMatrix)) {
        error("Unknown format for " + file.getAbsolutePath());
        return;
      }
      AlgorithmMatrix m = (AlgorithmMatrix)o;
      BeginVertex bv = m.buildAlgorithm();
      editorFrame.openNewTab(bv.draw(), file.getName());
      log("Loaded from " + file.getAbsolutePath());
    } catch (Exception e) {
      error(e.getMessage());
    }
  }

  /**
   * @return the editorFrame
   */
  public final EditorFrame getEditorFrame() {
    return editorFrame;
  }

}
