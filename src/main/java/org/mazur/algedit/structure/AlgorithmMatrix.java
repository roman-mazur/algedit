/**
 * 
 */
package org.mazur.algedit.structure;

import java.io.Serializable;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class AlgorithmMatrix implements Serializable {

  /** serialVersionUID. */
  private static final long serialVersionUID = 2225101470424995359L;

  /** Size. */
  private int size = 0;
  
  /** Connectivity matrix. */
  private byte[][] connectivity;
  /** Info array. */
  private VertexInfo[] info;
  
  /**
   * Constructor.
   * @param structure begin vertex
   */
  public AlgorithmMatrix(final BeginVertex structure, final int size) {
    this.size = size;
    connectivity = new byte[this.size][this.size];
    info = new VertexInfo[this.size];
    build(structure);
  }
  
  private void build(final AbstractVertex vertex) {
    AbstractVertex v = vertex;
    while(v != null) {
      info[v.getNumber()] = new VertexInfo(v); 
      if (v.getStraightVertex() == null) { break; }
      connectivity[v.getNumber()][v.getStraightVertex().getNumber()] = 1;
      if (v.getType() == VertexType.CONDITION) {
        int i = ((ConditionVertex)v).getAlternativeVertex().getNumber();
        connectivity[v.getNumber()][i] = 1;
      }
      v = v.getStraightVertex();
    }
  }
  
  /**
   * @return matrix size
   */
  public final int size() {
    return size;
  }
  
  /**
   * @param index vertex index
   * @return vertex type
   */
  public final VertexType getVertexType(final int index) {
    return info[index].type;
  }
  
  /**
   * @param index vertex index
   * @return signal index
   */
  public final Integer getSignalIndex(final int index) {
    return info[index].signalIndex;
  }
  
  private static AbstractVertex createVertex(final VertexInfo info) {
    AbstractVertex res = null;
    switch (info.type) {
    case CONDITION: 
      res = new ConditionVertex(); 
      res.setSignalIndex(info.signalIndex);
      return res;
    case OPERATOR:
      res = new OperatorVertax();
      res.setSignalIndex(info.signalIndex);
      return res;
    case SPECIAL:
      res = new EndVertex();
      return res;
    default:
      return res;
    }
  }
  
  private AbstractVertex getVertex(final AbstractVertex[] scope, final int index) {
    AbstractVertex res = scope[index];
    if (res != null) { return res; }
    res = createVertex(info[index]);
    res.setNumber(index);
    scope[index] = res;
    return res;
  }
  
  /**
   * @return algorithm structure
   */
  public BeginVertex buildAlgorithm() {
    AbstractVertex[] vertexes = new AbstractVertex[size];
    BeginVertex res = new BeginVertex();
    vertexes[0] = res;
    
    int linkIndex = 1;
    for (int i = 0; i < size; i++) {
      VertexInfo vi = info[i];
      AbstractVertex v = getVertex(vertexes, i);
      boolean condition = v.getType() == VertexType.CONDITION;
      boolean straight = false;
      for (int j = 1; j < size; j++) {
        if (connectivity[i][j] == 1) {
          AbstractVertex linkTo = getVertex(vertexes, j);
          if (condition && j == vi.alternativeIndex) {
            ((ConditionVertex)v).setAlternativeVertex(linkTo);
            ((ConditionVertex)v).setLinkIndex(linkIndex++);
            condition = false;
          } else {
            straight = true;
            v.setStraightVertex(linkTo);
          }
        }
        if (!condition && straight) { break; }
      }
    }
    
    return res;
  }
  
  /**
   * Vertex info to save.
   * @author Roman Mazur (IO-52)
   */
  private class VertexInfo implements Serializable {
    /** serialVersionUID. */
    private static final long serialVersionUID = 5774214292143992736L;
    
    /** Type. */
    private VertexType type;
    /** Signal. */
    private Integer signalIndex;
    /** Alternative. */
    private int alternativeIndex = -1;
    
    /** Constructor. */
    public VertexInfo(final AbstractVertex vertex) {
      type = vertex.getType();
      signalIndex = vertex.getSignalIndex();
      if (type == VertexType.CONDITION) {
        alternativeIndex = ((ConditionVertex)vertex).getAlternativeVertex().getNumber();
      }
    }
  }
}
