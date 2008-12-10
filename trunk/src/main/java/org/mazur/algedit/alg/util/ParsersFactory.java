/**
 * 
 */
package org.mazur.algedit.alg.util;

import java.io.IOException;
import java.util.LinkedList;

import org.mazur.algedit.alg.model.AlgorithmModel;
import org.mazur.parser.Machine;
import org.mazur.parser.MachineFactory;
import org.mazur.parser.Parser;

/**
 * Parser factory for algorithms editor.
 * @author Roman Mazur (IO-52)
 *
 */
public class ParsersFactory {
  
  /** Instance. */
  private static ParsersFactory instance = new ParsersFactory();
  
  /** Pool of machines. */
  private LinkedList<Machine> machinesPool = new LinkedList<Machine>();
  
  /** Initial count of parsers in the pool. */
  private static final int INITIAL_MACHINES_COUNT = 2;
  
  /** Description file of the machine for parsing the algorithms. */
  private static final String MACHINE_DESC_FILE = "alg.par";
  /** Encoding of the description file. */
  private static final String DESC_ENCODING = "UTF-8";
  
  /** Hidden constructor. */
  private ParsersFactory() {
    for (int i = 0; i < ParsersFactory.INITIAL_MACHINES_COUNT; i++) {
      machinesPool.add(createMachine());
    }
  }
  
  private static Machine createMachine() {
    MachineFactory mf = new MachineFactory(ParsersFactory.MACHINE_DESC_FILE, new StructureBuilder());
    try {
      return mf.build();
      
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * @return parser from the pool
   */
  private synchronized Machine getMachine() {
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
   * @param model algorithm model
   * @return parser to edit the defined model
   */
  public Parser createParser(final AlgorithmModel model) {
    Machine m = getMachine();
    StructureBuilder sb = (StructureBuilder)m.getBuilder();
    sb.setModel(model);
    Parser result = new Parser(ParsersFactory.DESC_ENCODING);
    result.setMachine(m);
    return result;
  }

  /**
   * @return the instance
   */
  public static final ParsersFactory getInstance() {
    return instance;
  }
}
