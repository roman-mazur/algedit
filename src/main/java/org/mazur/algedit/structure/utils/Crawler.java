/**
 * 
 */
package org.mazur.algedit.structure.utils;

import java.util.HashSet;
import java.util.LinkedList;

import org.mazur.algedit.structure.AbstractVertex;
import org.mazur.algedit.structure.ConditionVertex;
import org.mazur.algedit.structure.NullVertex;
import org.mazur.algedit.structure.VertexType;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class Crawler {
  HashSet<AbstractVertex> visited = new HashSet<AbstractVertex>();
  LinkedList<AbstractVertex> alternatives = new LinkedList<AbstractVertex>();

  private AbstractVertex start;
  private CrawlerHandler handler;
  public Crawler(final AbstractVertex start, final CrawlerHandler h) {
    this.start = start;
    this.handler = h;
  }
  
  public void crawl() {
    AbstractVertex current = start, prev = null;
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
      System.out.println("@@@@@@@@@" + alternatives.peek());
      current = alternatives.pop();
    } while (true);
  }

  /**
   * @param start the start to set
   */
  public final void setStart(AbstractVertex start) {
    this.start = start;
  }
  
}
