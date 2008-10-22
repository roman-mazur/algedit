/**
 * 
 */
package org.mazur.algedit.transformers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.mazur.algedit.Transformer;
import org.mazur.algedit.alg.model.AbstractVertex;
import org.mazur.algedit.alg.model.AlgorithmModel;
import org.mazur.algedit.alg.model.BeginVertex;
import org.mazur.algedit.alg.model.ConditionVertex;
import org.mazur.algedit.alg.model.EndVertex;
import org.mazur.algedit.alg.model.VertexType;
import org.mazur.algedit.alg.utils.AbstractCrawlHandler;
import org.mazur.algedit.alg.utils.Crawler;
import org.mazur.algedit.mili.model.MiliGraphModel;
import org.mazur.algedit.mili.model.MiliTransition;
import org.mazur.algedit.mili.model.MiliVertex;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class GraphTransformer implements Transformer<AlgorithmModel, MiliGraphModel> {

  /** Next vertex code. */
  private int nextVertexCode = 0;
  
  private List<MiliVertex> toMiliGraph(final BeginVertex begin) {
    final LinkedList<MiliVertex> result = new LinkedList<MiliVertex>();
    Crawler crawler = new Crawler(begin, new AbstractCrawlHandler() {
      private LinkedList<ConditionPair> alts = new LinkedList<ConditionPair>();
      private HashMap<AbstractVertex, MiliVertex> beforeVertexes = new HashMap<AbstractVertex, MiliVertex>();
      private MiliVertex lastMiliVertex;
      private AbstractVertex lastVertex;
      private ConditionVertex lastCondition;
      
      private void add(final AbstractVertex v) {
    	System.out.println("lastCond = " + lastCondition);
    	add(v, lastCondition);
      }
      
      private void add(final AbstractVertex v, final ConditionVertex cond) {
        MiliVertex mv = new MiliVertex();
        mv.setIndex(nextVertexCode);
        mv.setCode(nextVertexCode++);
        result.add(mv);
        System.out.println("before " + lastMiliVertex);
        if (lastMiliVertex != null) {
          MiliTransition t = new MiliTransition();
          t.setSource(lastMiliVertex);
          t.setTarget(mv);
          if (v.getType() == VertexType.OPERATOR) {
            t.setYSignal(v.getSignalIndex());
          }
          if (cond != null) {
        	t.addCondition(cond.getSignalIndex(), cond == lastCondition);
          }
          lastMiliVertex.getOutgoings().add(t);
        }
        beforeVertexes.put(v.getStraightVertex(), mv);
        lastMiliVertex = mv;
        System.out.println("after " + lastMiliVertex);
      }
      
      @Override
      public void processVertex(AbstractVertex v) {
    	if (v.getType() == VertexType.CONDITION) {
    	  lastCondition = (ConditionVertex)v;
    	} else {
    	  if (lastVertex == null || lastVertex.getType() != VertexType.CONDITION) { lastCondition = null; }
    	}
        if (v instanceof BeginVertex || v.getType() == VertexType.OPERATOR) {
          add(v);
          return;
        }
        if (v instanceof EndVertex) {
          if (!beforeVertexes.containsKey(v)) { add(v); }
          return;
        }
        lastVertex = v;
      }
      @Override
      public void processCondition(ConditionVertex v) {
        alts.push(new ConditionPair(v, lastMiliVertex));
      }
      @Override
      public void newBrunch(AbstractVertex v) {
        ConditionPair cp = alts.pop();
        lastMiliVertex = cp.prev;
        MiliVertex target = beforeVertexes.get(v);
        if (target != null) {
          MiliTransition t = new MiliTransition();
          t.setSource(lastMiliVertex);
          t.setTarget(target);
          if (v.getType() == VertexType.OPERATOR) {
            t.setYSignal(v.getSignalIndex());
          }
      	  t.addCondition(cp.cv.getSignalIndex(), false);
          lastMiliVertex.getOutgoings().add(t);
          lastMiliVertex = target;
        } else {
          add(v, cp.cv);
        }
      }
    });
    crawler.crawl();
    return result;
  }
  
  private static class ConditionPair {
    private ConditionVertex cv;
    private MiliVertex prev;
    private ConditionPair(final ConditionVertex cv, final MiliVertex prev) {
      this.cv = cv;
      this.prev = prev;
    }
  }

  /**
   * {@inheritDoc}
   */
  public MiliGraphModel transform(final AlgorithmModel source) {
    GraphTransformer implementor = new GraphTransformer();
    List<MiliVertex> main = implementor.toMiliGraph(source.getMainObject());
    MiliGraphModel model = new MiliGraphModel(source.getName());
    model.setMainObject(main);
    return model;
  }

  public String getName() {
    return "Algotithm -> Mili graph";
  }
  
}
