/**
 * 
 */
package org.mazur.algedit.structure.utils;

import org.mazur.algedit.structure.AbstractVertex;
import org.mazur.algedit.structure.ConditionVertex;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public interface CrawlerHandler {

  void processVertex(final AbstractVertex v);
  void processCondition(final ConditionVertex v);
  void processNotEnd(final AbstractVertex prev, final AbstractVertex v);
}
