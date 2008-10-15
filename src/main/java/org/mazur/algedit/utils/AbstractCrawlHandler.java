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
public abstract class AbstractCrawlHandler implements CrawlerHandler {

  /**
   * {@inheritDoc}
   * @see org.mazur.algedit.utils.CrawlerHandler#processCondition(org.mazur.algedit.alg.ConditionVertex)
   */
  public void processCondition(final ConditionVertex v) { }

  /**
   * {@inheritDoc}
   * @see org.mazur.algedit.utils.CrawlerHandler#processNotEnd(org.mazur.algedit.alg.AbstractVertex, org.mazur.algedit.alg.AbstractVertex)
   */
  public void processNotEnd(final AbstractVertex prev, final AbstractVertex v) { }

  /**
   * {@inheritDoc}
   * @see org.mazur.algedit.utils.CrawlerHandler#processVertex(org.mazur.algedit.alg.AbstractVertex)
   */
  public void processVertex(final AbstractVertex v) { }

  public void setCrawler(Crawler c) { }
  
  public void newBrunch(AbstractVertex v) { }
}