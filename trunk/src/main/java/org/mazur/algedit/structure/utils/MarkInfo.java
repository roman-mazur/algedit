package org.mazur.algedit.structure.utils;

import org.mazur.algedit.structure.ValidationType;

public class MarkInfo {

  private int start, length;
  private ValidationType validationType;
  
  public MarkInfo(int length, int start, ValidationType validationType) {
    super();
    this.length = length;
    this.start = start;
    this.validationType = validationType;
  }
  /**
   * @return the start
   */
  public final int getStart() {
    return start;
  }
  /**
   * @return the length
   */
  public final int getLength() {
    return length;
  }
  /**
   * @return the validationType
   */
  public final ValidationType getValidationType() {
    return validationType;
  }
  /**
   * @param start the start to set
   */
  public final void setStart(int start) {
    this.start = start;
  }
  /**
   * @param length the length to set
   */
  public final void setLength(int length) {
    this.length = length;
  }
  /**
   * @param validationType the validationType to set
   */
  public final void setValidationType(ValidationType validationType) {
    this.validationType = validationType;
  }
  
}
