/**
 * 
 */
package org.mazur.algedit.gui;

/**
 * Analyzer of intervals between inputs. 
 * @author Roman Mazur (IO-52)
 *
 */
public class Analyzer implements Runnable {

  /** Interval. */
  private static final int INTERVAL = 1000;
  
  /** Content. */
  private AlgorithmContent content;
  
  /** Stopped flag. */
  private boolean stopped = false;
  
  /**
   * @param stopped the stopped to set
   */
  public final void setStopped(final boolean stopped) {
    this.stopped = stopped;
  }

  /**
   * @param content the content to set
   */
  public final void setContent(final AlgorithmContent content) {
    this.content = content;
  }

  public void run() {
    while (!stopped) {
      if (content.isChanged()) {
        long dif = System.currentTimeMillis() - content.getLastEditTime();
        if (dif >= Analyzer.INTERVAL) {
          try {
            this.content.check();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
      try {
        Thread.sleep(Analyzer.INTERVAL / 2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
