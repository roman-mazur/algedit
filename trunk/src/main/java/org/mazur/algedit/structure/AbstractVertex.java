/**
 * 
 */
package org.mazur.algedit.structure;

import java.util.LinkedList;
import java.util.ListIterator;

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
  /** Next vertex. */
  private AbstractVertex straightVertex = null;
  
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

  protected String drawLinks(final LinkedList<BackLink> links) {
    String result = "";
    ListIterator<BackLink> iterator = links.listIterator();
    while (iterator.hasNext()) {
      BackLink bl = iterator.next();
      if (bl.getVertex() == this) {
        iterator.remove();
        result += "_" + bl.getNumber();
      }
    }
    return result;
  }
  
  public abstract String draw(final LinkedList<BackLink> links);
  
  public abstract VertexType getType();
}
