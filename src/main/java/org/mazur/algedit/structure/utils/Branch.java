package org.mazur.algedit.structure.utils;

import org.mazur.algedit.structure.AbstractVertex;

public class Branch {
  private AbstractVertex from, to;
  public Branch(final AbstractVertex from, final AbstractVertex to) {
    this.from = from; this.to = to;
  }
  @Override
  public int hashCode() {
    return from.hashCode() * 31 + to.hashCode();
  }
  @Override
  public boolean equals(final Object obj) {
    if (!(obj instanceof Branch)) { return false; }
    Branch b = (Branch)obj;
    return this.from == b.from && this.to == b.to; 
  }
}
