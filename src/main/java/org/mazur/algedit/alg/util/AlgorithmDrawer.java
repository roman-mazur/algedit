/**
 * 
 */
package org.mazur.algedit.alg.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.mazur.algedit.alg.model.AbstractVertex;
import org.mazur.algedit.alg.model.BeginVertex;
import org.mazur.algedit.alg.model.ConditionVertex;
import org.mazur.algedit.alg.model.VertexType;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class AlgorithmDrawer {

  private AbstractVertex start;
  private int linkIndex = 1;
  
  private HashMap<AbstractVertex, List<AbstractVertex>> links = new HashMap<AbstractVertex, List<AbstractVertex>>();
  private HashMap<Brunch, Integer> linkIndexes = new HashMap<Brunch, Integer>();
  private LinkedList<AbstractVertex> forDrawing = new LinkedList<AbstractVertex>();

  private Crawler crawler = new Crawler(start, new AbstractCrawlHandler() {
    public void processCondition(ConditionVertex v) {
      addLink(v, v.getAlternativeVertex());
    }
    public void processNotEnd(AbstractVertex prev, AbstractVertex v) {
      addLink(prev, v);
    }
    public void processVertex(final AbstractVertex v) {
      forDrawing.add(v);
    }
    public void setCrawler(Crawler c) {
    }
  });
  
  public AlgorithmDrawer(final BeginVertex start) {
    this.start = start;
  }
  
  private void addLink(final AbstractVertex from, final AbstractVertex to) {
    List<AbstractVertex> linksToCurrent = links.get(to);
    if (linksToCurrent == null) {
      linksToCurrent = new LinkedList<AbstractVertex>();
      links.put(to, linksToCurrent);
    }
    System.out.println("link from " + from.getLabel() + " to " + to.getLabel());
    linksToCurrent.add(from);
  }
  
  private int getLinkIndex(final AbstractVertex from, final AbstractVertex to) {
    Brunch b = new Brunch(from, to);
    Integer index = linkIndexes.get(b);
    if (index == null) {
      index = linkIndex++;
      linkIndexes.put(b, index);
    }
    return index;
  }
  
  private String drawVertex(final AbstractVertex vertex) {
    List<AbstractVertex> from = links.get(vertex);
    String a = "";
    if (from != null) {
      for (AbstractVertex av : from) {
        a += "_" + getLinkIndex(av, vertex);
      }
    }
    String b = "";
    if (vertex.getType() == VertexType.CONDITION) {
      b = "^" + getLinkIndex(vertex, ((ConditionVertex)vertex).getAlternativeVertex());
    }
    AbstractVertex next = vertex.getStraightVertex();
    if (next != null && ((from = links.get(next))) != null) {
      if (from.contains(vertex)) {
        b += "^" + getLinkIndex(vertex, next);
      }
    }
    return a + vertex.getLabel() + b;
  }
  
  public String draw() {
    crawler.setStart(start);
    crawler.crawl();
    String result = "";
    for (AbstractVertex av : forDrawing) {
      result += drawVertex(av) + " ";
    }
    return result;
  }
  
}
