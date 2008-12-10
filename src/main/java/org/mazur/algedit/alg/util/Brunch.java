package org.mazur.algedit.alg.util;

import org.mazur.algedit.alg.model.AbstractVertex;

class Brunch {
  private AbstractVertex from, to;
  public Brunch(final AbstractVertex from, final AbstractVertex to) {
    this.from = from; this.to = to;
  }
  @Override
  public int hashCode() {
    return from.hashCode() * 31 + to.hashCode();
  }
  @Override
  public boolean equals(final Object obj) {
    if (!(obj instanceof Brunch)) { return false; }
    Brunch b = (Brunch)obj;
    return this.from == b.from && this.to == b.to; 
  }
}
