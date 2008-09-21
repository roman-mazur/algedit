/**
 * 
 */
package org.mazur.algedit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Different utilities.
 * @author Roman Mazur (IO-52)
 *
 */
public final class Utils {

  /** Hidden constructor. */
  private Utils() { }

  private static DateFormat DATE_FORMAT = new SimpleDateFormat("[yyyy-MM-dd hh-mm-ss]");
  
  /**
   * @param message message for log 
   */
  public static void log(final Object message) {
    Date d = new Date();
    System.out.println(Utils.DATE_FORMAT.format(d) + " --> " + message);
  }
}
