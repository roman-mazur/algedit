/**
 * 
 */
package org.mazur.algedit.transtable.model;

import java.io.Serializable;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class TableRow implements Serializable {
  private static final long serialVersionUID = -5140132166974107191L;

  private String nameFrom, nameTo;
  private int codeFrom, codeTo;
  private byte[] conditionSignals, operatorSignals;
  private RSFunction[] rsFunctions;
  
  /**
   * @return the nameFrom
   */
  public final String getNameFrom() {
    return nameFrom;
  }


  /**
   * @return the nameTo
   */
  public final String getNameTo() {
    return nameTo;
  }


  /**
   * @return the codeFrom
   */
  public final int getCodeFrom() {
    return codeFrom;
  }


  /**
   * @return the codeTo
   */
  public final int getCodeTo() {
    return codeTo;
  }


  /**
   * @return the conditionSignals
   */
  public final byte[] getConditionSignals() {
    return conditionSignals;
  }


  /**
   * @return the operatorSignals
   */
  public final byte[] getOperatorSignals() {
    return operatorSignals;
  }


  /**
   * @return the rsFunctions
   */
  public final RSFunction[] getRsFunctions() {
    return rsFunctions;
  }


  /**
   * @param nameFrom the nameFrom to set
   */
  public final void setNameFrom(String nameFrom) {
    this.nameFrom = nameFrom;
  }


  /**
   * @param nameTo the nameTo to set
   */
  public final void setNameTo(String nameTo) {
    this.nameTo = nameTo;
  }


  /**
   * @param codeFrom the codeFrom to set
   */
  public final void setCodeFrom(int codeFrom) {
    this.codeFrom = codeFrom;
  }


  /**
   * @param codeTo the codeTo to set
   */
  public final void setCodeTo(int codeTo) {
    this.codeTo = codeTo;
  }


  /**
   * @param conditionSignals the conditionSignals to set
   */
  public void setConditionSignals(int n) {
    this.conditionSignals = new byte[n];
    for (int i = 0; i < this.conditionSignals.length; i++) {
      this.conditionSignals[i] = -1;
    }
  }


  public String getCondString() {
    String res = "[";
    for (byte c : this.conditionSignals) {
      res += (c >= 0 ? String.valueOf(c) : "-") + " ";
    }
    return res + "]";
  }
  
  /**
   * @param operatorSignals the operatorSignals to set
   */
  public final void setOperatorSignals(int n) {
    this.operatorSignals = new byte[n];
  }


  /**
   * @param rsFunctions the rsFunctions to set
   */
  public final void setRsFunctions(RSFunction[] rsFunctions) {
    this.rsFunctions = rsFunctions;
  }
  
  public final void setRsFunction(final int d, final int r, final int s) {
    RSFunction f = new RSFunction();
    f.r = (byte)r; f.s = (byte)s;
    rsFunctions[d] = f;
  }
  
  public String getRSString() {
    String res = "[";
    for (int i = rsFunctions.length - 1; i >= 0; i--) {
      res += rsFunctions[i].toString() + "]";
    }
    return res + "]";
  }
  
  public static class RSFunction implements Serializable {
    private static final long serialVersionUID = -3215467683664408603L;
    private byte r, s;
    private RSFunction() {};
    @Override
    public String toString() {
      return (r >= 0 ? String.valueOf(r) : "-") + (s >= 0 ? String.valueOf(s) : "-");
    }
  }
}
