/**
 * 
 */
package org.mazur.algedit;

import java.util.HashMap;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class LogEngine {

  /** Loggers pool. */
  private static HashMap<org.apache.log4j.Logger, Logger> loggers = new HashMap<org.apache.log4j.Logger, Logger>();
  
  public static Logger getLogger(final Class<?> clazz) {
    org.apache.log4j.Logger l = org.apache.log4j.Logger.getLogger(clazz);;
    Logger res = LogEngine.loggers.get(l);
    if (res == null) {
      res = new Logger(l, null);
      LogEngine.loggers.put(l, res);
    }
    return res;
  }
  
}
