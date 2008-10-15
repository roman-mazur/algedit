/**
 * 
 */
package org.mazur.algedit.utils;

import org.mazur.algedit.alg.AbstractVertex;
import org.mazur.algedit.alg.ConditionVertex;

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
