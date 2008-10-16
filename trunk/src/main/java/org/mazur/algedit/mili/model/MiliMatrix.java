/**
 * 
 */
package org.mazur.algedit.mili.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.mazur.algedit.SerializeableMatrix;

/**
 * Matrixes to represent the Mili graph.
 * @author Roman Mazur (IO-52)
 *
 */
public class MiliMatrix implements SerializeableMatrix<List<MiliVertex>> {

  /** serialVersionUID. */
  private static final long serialVersionUID = 2151862358375652038L;

  /** Connectivity matrix. */
  private int[][] connectivity;
  /** Transitions description. */
  private Map<Integer, MiliTransition> transitions;
  
  private static int key(final int i, final int j) {
    return i * 31 + j;
  }
  
  /**
   * Constructor.
   * @param vertexes machine
   */
  public MiliMatrix(final List<MiliVertex> vertexes) {
    LinkedList<MiliTransition> tList = new LinkedList<MiliTransition>();
    int size = vertexes.size();
    connectivity = new int[size][size];
    transitions = new HashMap<Integer, MiliTransition>();
    for (MiliVertex mv : vertexes) {
      for (MiliTransition mt : mv.getOutgoings()) {
        int i = mv.getIndex(), j = mt.getSource().getIndex(); 
        connectivity[i][j] = 1;
        transitions.put(MiliMatrix.key(i, j), mt);
        tList.add(mt);
      }
    }
  }
  
  /**
   * {@inheritDoc}
   */
  public List<MiliVertex> build() {
    List<MiliVertex> result = new ArrayList<MiliVertex>(connectivity.length);
    for (int i = 0; i < connectivity.length; i++) {
      MiliVertex mv = new MiliVertex();
      mv.setIndex(i);
      for (int j = 0; j < connectivity.length; j++) {
        if (connectivity[i][j] == 1) {
          mv.getOutgoings().add(transitions.get(MiliMatrix.key(i, j)));
        }
      }
      result.add(mv);
    }
    return result;
  }

}
