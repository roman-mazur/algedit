package org.mazur.algedit.actions;

import java.io.IOException;
import java.util.LinkedList;

import org.mazur.algedit.EditorFrame;
import org.mazur.algedit.structure.StructureBuilder;
import org.mazur.parser.Machine;
import org.mazur.parser.MachineFactory;
import org.mazur.parser.Parser;

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
}
