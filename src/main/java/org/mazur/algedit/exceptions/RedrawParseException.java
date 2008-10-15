package org.mazur.algedit.exceptions;

import java.util.List;

import org.mazur.algedit.alg.model.AbstractVertex;

public class RedrawParseException extends RuntimeException {

  private static final long serialVersionUID = -4162158101898683744L;

  private List<AbstractVertex> redrawList;

  public RedrawParseException(List<AbstractVertex> redrawList) {
    super();
    this.redrawList = redrawList;
  }

  /**
   * @return the redrawList
   */
  public final List<AbstractVertex> getRedrawList() {
    return redrawList;
  }
  
}
