/**
 * 
 */
package org.mazur.algedit.structure.utils;

import java.util.List;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class RedrawInfo {
  private String content;
  private List<MarkInfo> marks;
  /**
   * @return the content
   */
  public final String getContent() {
    return content;
  }
  /**
   * @return the marks
   */
  public final List<MarkInfo> getMarks() {
    return marks;
  }
  public RedrawInfo(String content, List<MarkInfo> marks) {
    super();
    this.content = content;
    this.marks = marks;
  }
  /**
   * @param content the content to set
   */
  public final void setContent(String content) {
    this.content = content;
  }
  /**
   * @param marks the marks to set
   */
  public final void setMarks(List<MarkInfo> marks) {
    this.marks = marks;
  }
}
