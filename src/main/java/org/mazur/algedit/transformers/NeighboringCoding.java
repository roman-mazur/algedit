/**
 * 
 */
package org.mazur.algedit.transformers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.mazur.algedit.Transformer;
import org.mazur.algedit.exceptions.TransformException;
import org.mazur.algedit.mili.model.MiliGraphModel;
import org.mazur.algedit.mili.model.MiliTransition;
import org.mazur.algedit.mili.model.MiliVertex;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class NeighboringCoding implements Transformer<MiliGraphModel, MiliGraphModel> {

  /** Max count of digits.  */
  private static final int MAX_DIGITS = 6;
  
  private Template currentTemplate;
  private MiliGraphModel result;
  private HashSet<MiliVertex> visited = null;
  
  private int futureCount = 0;
  
  public String getName() {
    return "Neigboring coding";
  }

  public static int getMinDigitsCount(final int vCount) {
    if (vCount == 0) { return 0; }
    int v = vCount;
    int res = 31;
    while ((v & (1 << 31)) == 0) { v <<= 1; res--; }
    return res;
  }
  
  private void setVertexToResult(final MiliVertex mv) {
    if (mv.getIndex() < result.getMainObject().size()) {
      result.getMainObject().set(mv.getIndex(), mv);
    } else {
      result.getMainObject().add(mv);
    }
  }
  
  private MiliVertex getVertexFromResult(final int index) {
    if (index < result.getMainObject().size()) {
      return result.getMainObject().get(index);
    }
    return null;
  }
  
  private void saveVertexPosition(final MiliVertex vertex, final int number, final List<Integer> outNumbers) {
    MiliVertex v = getVertexFromResult(vertex.getIndex());
    if (v == null) {
      v = new MiliVertex();
      v.setIndex(vertex.getIndex());
      setVertexToResult(v);
    }
    v.setCode(currentTemplate.codes[number]);
    int index = 0;
    for (MiliTransition mt : vertex.getOutgoings()) {
      MiliTransition newMt = new MiliTransition();
      MiliVertex dest = getVertexFromResult(mt.getTarget().getIndex());
      if (dest == null) {
        dest = new MiliVertex();
        dest.setIndex(mt.getTarget().getIndex());
        dest.setCode(currentTemplate.codes[outNumbers.get(index)]);
      }
      newMt.setSource(v);
      newMt.setTarget(dest);
      newMt.setConditions(mt.getConditions());
      newMt.setYSignal(mt.getYSignal());
      setVertexToResult(dest);
      v.getOutgoings().add(newMt);
      index++;
    }
  }
  
  private boolean isNeighbore(final int c1, final int c2) {
    int temp = c1 ^ c2;
    int res = 0;
    while (temp != 0) {
      res += (temp & 1);
      temp >>= 1;
    }
    return res == 1;
  }
  
  private MiliTransition getResultTransition(final MiliVertex from, final MiliVertex to) {
    MiliVertex rFrom = result.getMainObject().get(from.getIndex());
    for (MiliTransition t : rFrom.getOutgoings()) {
      if (t.getTarget().getIndex() == to.getIndex()) { return t; } 
    }
    return null;
  }
  
  private boolean setVertex(final int templateVertexNumber, final MiliTransition transition, final MiliVertex vertex) {
    if (visited.contains(vertex)) { 
      return isNeighbore(result.getMainObject().get(vertex.getIndex()).getCode(), transition.getSource().getCode()); 
    }
    visited.add(vertex);
    System.out.println(vertex);
    List<Integer> templateVertexNumbers = currentTemplate.findNeighborePlace(templateVertexNumber);
    int presentCount = 0;
    for (MiliTransition mt : vertex.getOutgoings()) {
      if (visited.contains(mt.getTarget())) { presentCount++; }
    }
    if (templateVertexNumbers.size() < vertex.getOutgoings().size() - presentCount) {
      visited.remove(vertex);
      return false; 
    }
    saveVertexPosition(vertex, templateVertexNumber, templateVertexNumbers);
    int index = 0;
    HashSet<MiliVertex> setted = new HashSet<MiliVertex>();
    for (MiliTransition mt : vertex.getOutgoings()) {
      boolean visit = visited.contains(mt.getTarget());
      int rotateCnt = 0;
      boolean success;
      do {
        currentTemplate.configureMask(templateVertexNumbers, vertex.getOutgoings().size());
        int number = -1;
        if (!visit) { number = templateVertexNumbers.get(index); }
        success = setVertex(number, getResultTransition(vertex, mt.getTarget()), mt.getTarget());
        if (!success && !visit) { Collections.rotate(templateVertexNumbers, 1); rotateCnt++; }
      } while (!success && !visit && rotateCnt < templateVertexNumbers.size());
      if (!success) {
        currentTemplate.configureMask(templateVertexNumbers, 0);
        visited.removeAll(setted);
        visited.remove(vertex);
        if (futureCount < currentTemplate.codes.length) {
          futureCount++;
          MiliVertex additionalVertex = new MiliVertex();
          additionalVertex.setIndex(futureCount - 1);
          MiliVertex temp = new MiliVertex();
          temp.setIndex(additionalVertex.getIndex());
          if (futureCount < result.getMainObject().size()) {
            result.getMainObject().set(temp.getIndex(), temp);
          } else {
            result.getMainObject().add(temp);
          }
          transition.setTarget(temp);
          MiliTransition t = new MiliTransition();
          t.setTarget(vertex);
          additionalVertex.getOutgoings().add(t);
          if (!setVertex(templateVertexNumber, transition, additionalVertex)) {
            futureCount--;
            result.getMainObject().remove(additionalVertex.getIndex());
            return false;
          }
          return true;
        }
        result.getMainObject().remove(vertex.getIndex());
        return false; 
      }
      setted.add(mt.getTarget());
      if (!visit) { index++; }
    }
    return true;
  }
  
  private MiliGraphModel t(final MiliGraphModel source) throws TransformException {
    int count = NeighboringCoding.getMinDigitsCount(source.getMainObject().size());
    MiliTransition transition = new MiliTransition();
    MiliVertex vertex = source.getMainObject().get(0);
    transition.setTarget(vertex);
    boolean done = false;
    result = new MiliGraphModel(source.getName());
    visited = new HashSet<MiliVertex>();
    while (!done && count < NeighboringCoding.MAX_DIGITS) {
      currentTemplate = new Template(count);
      visited.clear();
      result.setMainObject(new ArrayList<MiliVertex>(source.getMainObject().size()));
      futureCount = source.getMainObject().size();
      done = setVertex(0, transition, vertex);
      if (!done) { 
        result.setMainObject(new ArrayList<MiliVertex>(source.getMainObject().size()));
        futureCount = source.getMainObject().size();
        visited.clear(); done = setVertex(1, transition, vertex); 
      }
      count++;
    }
    if (done) { return this.result; }
    throw new TransformException("Too many vertexes.");
  }
  
  public MiliGraphModel transform(MiliGraphModel source) throws TransformException {
    MiliGraphModel res = new NeighboringCoding().t(source);
    for (MiliVertex vertex : res.getMainObject()) {
      List<MiliTransition> dublicates = new LinkedList<MiliTransition>();
      HashSet<DublicateTrans> dt = new HashSet<DublicateTrans>();
      for (MiliTransition mt : vertex.getOutgoings()) {
        DublicateTrans trans = new DublicateTrans();
        trans.from = mt.getSource().getIndex(); trans.to = mt.getTarget().getIndex();
        if (dt.contains(trans)) {
          dublicates.add(mt);
        } else {
          dt.add(trans);
        }
      }
      vertex.getOutgoings().removeAll(dublicates);
    }
    return res;
  }

  private static class DublicateTrans {
    private int from, to;
    private DublicateTrans() {};
    @Override
    public boolean equals(Object obj) {
      return ((DublicateTrans)obj).from == from && ((DublicateTrans)obj).to == to; 
    }
    @Override
    public int hashCode() {
      return from * 31 + to;
    }
  }
  
  private class Template {
    private int dCount;
    private int[] codes = null;
    private boolean[] mask = null;
    private int[][] connectivity = null;
    private void setConnection(final int i, final int j, final int x) {
      connectivity[i][j] = (connectivity[j][i] = x);
    }
    private Template(final int dCount) {
      this.dCount = dCount;
      int size = 1 << dCount;
      codes = new int[size];
      mask = new boolean[size];
      connectivity = new int[size][size];
      
      for (int i = 0; i < size; i++) {
        codes[i] = i ^ (i >> 1);
        mask[i] = false;
      }
      for (int i = 0; i < size >> 1; i++) {
        int s = i << 1;
        int inc = s;
        for (int j = 0; j < dCount; j++) {
          inc += 1 << j;
          inc %= size;
          setConnection(s, inc, 1);
        }
      }
    }
    public void configureMask(List<Integer> code, int outsCount) {
      for (int i = 0; i < code.size(); i++) {
        mask[code.get(i)] = i < outsCount;
      }
    }
    
    public List<Integer> findNeighborePlace(final int templateVertexNumber) {
      int touchedCount = 0;
      mask[templateVertexNumber] = true;
      List<Integer> res = new ArrayList<Integer>(dCount);
      for (int i = 0; i < connectivity.length && touchedCount < dCount; i++) {
        if (connectivity[templateVertexNumber][i] == 1) {
          touchedCount++;
          if (!mask[i]) {
            res.add(i);
          }
        }
      }
      return res;
    }
  }
}
