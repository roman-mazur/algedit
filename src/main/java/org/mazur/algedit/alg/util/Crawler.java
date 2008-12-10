/**
 * 
 */
package org.mazur.algedit.alg.util;

import java.util.HashSet;
import java.util.LinkedList;

import org.mazur.algedit.alg.model.AbstractVertex;
import org.mazur.algedit.alg.model.ConditionVertex;
import org.mazur.algedit.alg.model.NullVertex;
import org.mazur.algedit.alg.model.VertexType;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class Crawler {
  private HashSet<AbstractVertex> visited = new HashSet<AbstractVertex>();
  private LinkedList<AbstractVertex> alternatives = new LinkedList<AbstractVertex>();
  private AbstractVertex prev = null;
  
  /**
   * @return the alternatives
   */
  public final LinkedList<AbstractVertex> getAlternatives() {
    return alternatives;
  }

  private AbstractVertex start;
  private CrawlerHandler handler;
  public Crawler(final AbstractVertex start, final CrawlerHandler h) {
    this.start = start;
    this.handler = h;
    this.handler.setCrawler(this);
  }
  
  public void crawl() {
    AbstractVertex current = start;
    do {
      while (!(current instanceof NullVertex) && !visited.contains(current)) {
        visited.add(current);
        handler.processVertex(current);
        System.out.println(current + " " + ((current != null) ? current.getStraightVertex() : ""));
        if (current.getType() == VertexType.CONDITION) {
          alternatives.push(((ConditionVertex)current).getAlternativeVertex());
          handler.processCondition((ConditionVertex)current);
        }
        prev = current;
        current = current.getStraightVertex();
      }
      if (!(current instanceof NullVertex) && prev != null) {
        handler.processNotEnd(prev, current);
      }
      if (alternatives.isEmpty()) { break; }
      current = alternatives.pop();
      handler.newBrunch(current);
    } while (true);
  }

  /**
   * @return the prev
   */
  public final AbstractVertex getPrev() {
    return prev;
  }

  /**
   * @param start the start to set
   */
  public final void setStart(AbstractVertex start) {
    this.start = start;
  }
  
}
