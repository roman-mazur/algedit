/**
 * 
 */
package org.mazur.algedit.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.mazur.parser.AbstractHandlersFactory;
import org.mazur.parser.Machine;
import org.mazur.parser.MachineException;
import org.mazur.parser.ParseException;
import org.mazur.parser.SymbolHandler;
import org.mazur.parser.Validator;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class StructureBuilder extends AbstractHandlersFactory implements Validator {

  /** Initial buffer size. */
  private static final int INITIAL_BUFFER_SIZE = 15;
  
  /** Vertex index. */
  private int vertexIndex = -1;
  
  /** Vertexes buffer. */
  private ArrayList<AbstractVertex> vertexes = new ArrayList<AbstractVertex>(StructureBuilder.INITIAL_BUFFER_SIZE);
  private LinkedList<BackLink> lastLinks = new LinkedList<BackLink>();
  /** Back links. */
  private HashMap<Integer, BackLink> backLinks = new HashMap<Integer, BackLink>();
  
  /** Current characters. */
  private String currentCharacters = "";
  
  private AbstractVertex getLastVertex() {
    return vertexes.get(vertexIndex);
  }
  
  private void setSignalIndex() {
    System.out.println("SIGNAL: " + currentCharacters + " for " + vertexIndex);
    int signalNumber = Integer.parseInt(currentCharacters);
    getLastVertex().setSignalIndex(signalNumber);
    currentCharacters = "";
  }

  private void addToArray(final AbstractVertex vertex) {
    if (vertexIndex >= 0) {
      getLastVertex().setStraightVertex(vertex);
    }
    vertexIndex++;
    if (vertexes.size() == vertexIndex) {
      vertexes.add(vertex);
    } else {
      vertexes.set(vertexIndex, vertex);
    }
  }
  
  private void processLastLinks(final AbstractVertex vertex) {
    for (BackLink bl : lastLinks) {
      bl.setVertex(vertex);
      BackLink present = backLinks.get(bl.getNumber());
      if (present == null) {
        backLinks.put(bl.getNumber(), bl);
        continue;
      }
      System.out.println("OK");
      if (!(present.getVertex() instanceof ConditionVertex)) { throw new MachineException(); }
      ConditionVertex cv = (ConditionVertex)present.getVertex();
      cv.setAlternativeVertex(vertex);
      backLinks.remove(present.getNumber());
    }
    lastLinks.clear();
  }
  
  private void addBegin() {
    addToArray(new BeginVertex());
    System.out.println("addBegin finish " + vertexIndex + " / " + vertexes.size());
  }
  
  private void addEnd() {
    AbstractVertex v = new EndVertex();
    addToArray(v);
    processLastLinks(v);
    System.out.println("addEnd finish " + vertexIndex);
  }
  
  private void startCondition(final Character c) {
    ConditionVertex v = new ConditionVertex();
    getLastVertex().setStraightVertex(v);
    processLastLinks(v);
  }
  
  private void startOperator(final Character c) {
    OperatorVertax v = new OperatorVertax();
    getLastVertex().setStraightVertex(v);
    processLastLinks(v);
  }

  private void startLink(final Character c) {
    
  }

  private void startLinking(final Character c) {
    
  }

  private void character(final Character c) {
    currentCharacters += c;
  }

  private void endCondition(final Character c) {
    addToArray(getLastVertex().getStraightVertex());
    setSignalIndex();
  }

  private void endOperator(final Character c) {
    addToArray(getLastVertex().getStraightVertex());
    setSignalIndex();
  }

  private void endLink(final Character c) {
    int linkNumber = Integer.parseInt(currentCharacters);
    System.out.println("END LINK!!!! " + currentCharacters);
    ConditionVertex cv = (ConditionVertex)getLastVertex();
    cv.setLinkIndex(linkNumber);
    currentCharacters = "";
    BackLink present = backLinks.get(linkNumber);
    if (present == null) {
      System.out.println("OK");
      BackLink bl = new BackLink();
      bl.setVertex(getLastVertex());
      bl.setNumber(linkNumber);
      bl.setDirection(false);
      backLinks.put(bl.getNumber(), bl);
    } else {
      if (!present.isDirection()) {
        throw new MachineException();
      }
      cv.setAlternativeVertex(present.getVertex());
      backLinks.remove(linkNumber);
    }
  }

  private void endLinking(final Character c) {
    System.out.println("END LINKING!!!! " + currentCharacters);
    BackLink bl = new BackLink();
    bl.setNumber(Integer.parseInt(currentCharacters));
    currentCharacters = "";
    lastLinks.add(bl);
  }

  /**
   * {@inheritDoc}
   * @see org.mazur.parser.AbstractHandlersFactory#createHandler(java.lang.String)
   */
  @Override
  protected SymbolHandler createHandler(final String operation) {
    FunctionName name = FunctionName.byString(operation);
    if (name == null) { System.out.println(operation + "@@@@@@@@@@@@@@"); }
    switch (name) {
    case ERROR: 
      return new SymbolHandler() { 
        @Override
        protected void run(Character arg0, Machine arg1) {
          throw new MachineException();
        } 
      };
    case SKIP: 
      return new SymbolHandler() { 
        @Override
        protected void run(Character arg0, Machine arg1) { } 
      };
    case ADD_BEGIN:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          addBegin();
        }
      };
    case ADD_END:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          addEnd();
        }
      };
    case START_CONDITION:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          startCondition(c);
        }
      };
    case START_LINK:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          startLink(c);
        }
      };
    case START_LINKING:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          startLinking(c);
        }
      };
    case START_OPERATOR:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          startOperator(c);
        }
      };
    case CHARACTER:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          character(c);
        }
      };
    case END_CONDITION:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          endCondition(c);
        }
      };
    case END_LINK:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          endLink(c);
        }
      };
    case END_LINKING:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          endLinking(c);
        }
      };
    case END_OPERATOR:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          endOperator(c);
        }
      };
    case END_CONDITION_START_LINK:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          endCondition(c);
          startLink(c);
        }
      };
    case END_LINK_START_CONDITION:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          endLink(c);
          startCondition(c);
        }
      };
    case END_LINK_START_LINKING:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          endLink(c);
          startLinking(c);
        }
      };
    case END_LINK_START_OPERATOR:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          endLink(c);
          startOperator(c);
        }
      };
    case END_LINKING_START_CONDITION:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          endLinking(c);
          startCondition(c);
        }
      };
    case END_LINKING_START_OPERATOR:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          endLinking(c);
          startOperator(c);
        }
      };
    case END_OPERATOR_START_CONDITION:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          endOperator(c);
          startCondition(c);
        }
      };
    case END_OPERATOR_START_LINKING:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          endOperator(c);
          startLinking(c);
        }
      };
    case END_START_LINKING:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          endLinking(c);
          startLinking(c);
        }
      };
    case END_START_OPERATOR:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          endOperator(c);
          startOperator(c);
        }
      };
    case END_CONDITION_ADD_END:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          endCondition(c);
          addEnd();
        }
      };
    case END_LINK_ADD_END:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          endLink(c);
          addEnd();
        }
      };
    case END_LINKING_ADD_END:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          endLinking(c);
          addEnd();
        }
      };
    case END_OPERATOR_ADD_END:
      return new SymbolHandler() {
        protected void run(final Character c, final Machine m) {
          endOperator(c);
          addEnd();
        }
      };
    default:
      System.out.println("UNKNOWN " + operation);
    }
    return new SymbolHandler() {
      @Override
      protected void run(final Character c, final Machine m) { };
    };  
  }

  private enum FunctionName {
    ERROR("error"),
    SKIP("skip"),
    ADD_BEGIN("addBegin"),
    ADD_END("addEnd"),
    START_CONDITION("startCondition"),
    START_OPERATOR("startOperator"),
    START_LINKING("startLinking"),
    START_LINK("startLink"),
    END_CONDITION("endCondition"),
    END_OPERATOR("endOperator"),
    END_LINKING("endLinking"),
    END_LINK("endLink"),
    CHARACTER("character"),
    END_OPERATOR_START_CONDITION("endOperatorStartCondition"),
    END_OPERATOR_ADD_END("endOperatorAddEnd"),
    END_START_OPERATOR("endStartOperator"),
    END_LINK_START_CONDITION("endLinkStartCondition"),
    END_LINK_START_OPERATOR("endLinkStartOperator"),
    END_LINK_START_LINKING("endLinkStartLinking"),
    END_LINK_ADD_END("endLinkAddEnd"),
    END_LINKING_START_CONDITION("endLinkingStartCondition"),
    END_LINKING_START_OPERATOR("endLinkingStartOperator"),
    END_LINKING_ADD_END("endLinkingAddEnd"),
    END_START_LINKING("endStartLinking"),
    END_CONDITION_START_LINK("endConditionStartLink"),
    END_CONDITION_ADD_END("endConditionAddEnd"),
    END_OPERATOR_START_LINKING("endOperatorStartLinking");
    
    private String name;
    private FunctionName(final String name) {
      this.name = name;
    }
    public static FunctionName byString(final String name) {
      for (final FunctionName n : FunctionName.values()) {
        if (n.name.equals(name)) { return n; }
      }
      return null;
    }
  }
  
  public BeginVertex getBeginVertex() {
    return (BeginVertex)vertexes.get(0);
  }

  public void reset() {
    vertexIndex = -1;
    for (int i = 0; i < vertexes.size(); i++) {
      vertexes.set(i, null);
    }
    lastLinks.clear();
    backLinks.clear();
    System.out.println("RESET");
  }

  /**
   * {@inheritDoc}
   */
  public void finishCheck() throws ParseException {
    // TODO Auto-generated method stub
    
  }
}
