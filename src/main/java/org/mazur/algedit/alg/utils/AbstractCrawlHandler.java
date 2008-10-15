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
public abstract class AbstractCrawlHandler implements CrawlerHandler {

  /**
   * {@inheritDoc}
   * @see org.mazur.algedit.alg.utils.CrawlerHandler#processCondition(org.mazur.algedit.alg.model.ConditionVertex)
   */
  public void processCondition(final ConditionVertex v) { }

  /**
   * {@inheritDoc}
   * @see org.mazur.algedit.alg.utils.CrawlerHandler#processNotEnd(org.mazur.algedit.alg.model.AbstractVertex, org.mazur.algedit.alg.model.AbstractVertex)
   */
  public void processNotEnd(final AbstractVertex prev, final AbstractVertex v) { }

  /**
   * {@inheritDoc}
   * @see org.mazur.algedit.alg.utils.CrawlerHandler#processVertex(org.mazur.algedit.alg.model.AbstractVertex)
   */
  public void processVertex(final AbstractVertex v) { }

  public void setCrawler(Crawler c) { }
  
  public void newBrunch(AbstractVertex v) { }
}