/**
 * 
 */
package org.mazur.algedit.exceptions;

/**
 * "Not supported model type".
 * @author Roman Mazur (IO-52)
 */
public class NotSupportedModelTypeException extends RuntimeException {

  /** serialVersionUID. */
  private static final long serialVersionUID = 1242606122932991465L;

  /**
   * Constructor.
   */
  public NotSupportedModelTypeException() {
    this("Unknown model type");
  }

  /**
   * Constructor.
   * @param message message
   */
  public NotSupportedModelTypeException(final String message) {
    super(message);
  }

  /**
   * Constructor.
   * @param cause cause of the exception
   */
  public NotSupportedModelTypeException(final Throwable cause) {
    super(cause);
  }

  /**
   * Constructor.
   * @param message message
   * @param cause cause of the exception
   */
  public NotSupportedModelTypeException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
