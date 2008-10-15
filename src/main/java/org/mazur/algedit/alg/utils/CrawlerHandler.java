/**
 * 
 */
package org.mazur.algedit.alg.utils;

import org.mazur.algedit.alg.model.AbstractVertex;
import org.mazur.algedit.alg.model.ConditionVertex;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public interface CrawlerHandler {

  void processVertex(final AbstractVertex v);
  void processCondition(final ConditionVertex v);
  void processNotEnd(final AbstractVertex prev, final AbstractVertex v);
  void setCrawler(final Crawler c);
  void newBrunch(final AbstractVertex v);
}
