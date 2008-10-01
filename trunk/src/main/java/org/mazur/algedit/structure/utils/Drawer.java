/**
 * 
 */
package org.mazur.algedit.structure.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.mazur.algedit.structure.AbstractVertex;
import org.mazur.algedit.structure.BeginVertex;
import org.mazur.algedit.structure.ConditionVertex;
import org.mazur.algedit.structure.NullVertex;
import org.mazur.algedit.structure.VertexType;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class Drawer {

  private AbstractVertex start;
  private int linkIndex = 1;
  
  private HashSet<AbstractVertex> visited = new HashSet<AbstractVertex>();
  
  private LinkedList<AbstractVertex> alternatives = new LinkedList<AbstractVertex>();
  private HashMap<AbstractVertex, List<AbstractVertex>> links = new HashMap<AbstractVertex, List<AbstractVertex>>();
  private HashMap<Branch, Integer> linkIndexes = new HashMap<Branch, Integer>();
  private LinkedList<AbstractVertex> forDrawing = new LinkedList<AbstractVertex>();
  
  public Drawer(final BeginVertex start) {
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
    Branch b = new Branch(from, to);
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
    AbstractVertex current = start, prev = null;
    do {
      prev = null;
      while (!(current instanceof NullVertex) && !visited.contains(current)) {
        visited.add(current);
        System.out.println("pushing " + current.getLabel());
        forDrawing.add(current);
        if (current.getType() == VertexType.CONDITION) {
          alternatives.push(((ConditionVertex)current).getAlternativeVertex());
          addLink(current, ((ConditionVertex)current).getAlternativeVertex());
        }
        prev = current;
        current = current.getStraightVertex();
      }
      if (!(current instanceof NullVertex) && prev != null) {
        addLink(prev, current);
      }
      if (alternatives.isEmpty()) { break; }
      current = alternatives.pop();
    } while (true);
    String result = "";
    for (AbstractVertex av : forDrawing) {
      result += drawVertex(av) + " ";
    }
    return result;
  }
  
}
