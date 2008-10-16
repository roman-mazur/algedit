/**
 * 
 */
package org.mazur.algedit;

import org.mazur.algedit.actions.MainMediator;

/**
 * Logger.
 * @author Roman Mazur (IO-52)
 *
 */
public class Logger {

  /** Mediator. */
  private MainMediator mediator;
  /** Log4j logger. */
  private org.apache.log4j.Logger log;
  
  public Logger(final org.apache.log4j.Logger log, final MainMediator mediator) {
    this.log = log;
    this.mediator = mediator;
  }
  
  /**
   * Writes the log.
   * @param o object
   */
  public void info(final Object o) {
    if (mediator != null) {
      mediator.log(o != null ? o.toString() : null);
    }
    log.info(o);
  }
  
  /**
   * Writes the error.
   * @param o object
   */
  public void error(final Object o) {
    if (mediator != null) {
      mediator.error(o != null ? o.toString() : null);
    }
    log.error(o);
  }

  /**
   * Writes the error.
   * @param o object
   */
  public void error(final Object o, final Throwable e) {
    if (mediator != null) {
      mediator.error(o != null ? o.toString() : null);
    }
    log.error(o, e);
  }
}
