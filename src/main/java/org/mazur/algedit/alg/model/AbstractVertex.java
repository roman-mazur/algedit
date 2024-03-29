/**
 * 
 */
package org.mazur.algedit.alg.model;


/**
 * Abstract algorithm vertex.
 * @author Roman Mazur (IO-52)
 *
 */
public abstract class AbstractVertex {

  /** Vertex number. */
  private int number = 0;
  /** Signal index. */
  private int signalIndex = 0;
  /** Link index. */
  private int linkIndex = 0;
  private int symbolIndex = 0;
  
  /**
   * @return the symbolIndex
   */
  public final int getSymbolIndex() {
    return symbolIndex;
  }

  /**
   * @param symbolIndex the symbolIndex to set
   */
  public final void setSymbolIndex(int symbolIndex) {
    this.symbolIndex = symbolIndex;
  }

  /** Next vertex. */
  private AbstractVertex straightVertex = null;
  
  private ValidationType validationType = ValidationType.NORMAL;
  

  /**
   * @return the validationType
   */
  public final ValidationType getValidationType() {
    return validationType;
  }

  /**
   * @param validationType the validationType to set
   */
  public final void setValidationType(final ValidationType validationType) {
    this.validationType = validationType;
  }

  /**
   * @return the signalIndex
   */
  public final int getSignalIndex() {
    return signalIndex;
  }

  /**
   * @return vertex label
   */
  public abstract String getLabel();
  
  /**
   * @param signalIndex the signalIndex to set
   */
  public final void setSignalIndex(final int signalIndex) {
    this.signalIndex = signalIndex;
  }

  /**
   * @return the straightVertex
   */
  public AbstractVertex getStraightVertex() {
    return straightVertex;
  }

  /**
   * @param straightVertex the straightVertex to set
   */
  public final void setStraightVertex(final AbstractVertex straightVertex) {
    this.straightVertex = straightVertex;
  }

  /**
   * @param number the number to set
   */
  public final void setNumber(final int number) {
    this.number = number;
  }

  /**
   * {@inheritDoc}
   * @see org.mazur.algedit.structure.Vertex#getNumber()
   */
  public final int getNumber() {
    return number;
  }

  public abstract VertexType getType();

  /**
   * @return the linkIndex
   */
  public final int getLinkIndex() {
    return linkIndex;
  }

  /**
   * @param linkIndex the linkIndex to set
   */
  public final void setLinkIndex(final int linkIndex) {
    this.linkIndex = linkIndex;
  }

}

