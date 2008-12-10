/**
 * 
 */
package org.mazur.algedit.device.util;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class PortmapInfo {

  private Set<String> inputsSet = new HashSet<String>();
  private Set<String> internalSet = new HashSet<String>();
  private Set<String> outputSet = new HashSet<String>();
  /**
   * @return the outputSet
   */
  public Set<String> getOutputSet() {
    return outputSet;
  }
  /**
   * @param outputSet the outputSet to set
   */
  public void setOutputSet(Set<String> outputSet) {
    this.outputSet = outputSet;
  }

  private String portmapCode = "";
  /**
   * @return the inputsSet
   */
  public Set<String> getInputsSet() {
    return inputsSet;
  }
  /**
   * @return the internalSet
   */
  public Set<String> getInternalSet() {
    return internalSet;
  }
  /**
   * @return the portmapCode
   */
  public String getPortmapCode() {
    return portmapCode;
  }
  /**
   * @param inputsSet the inputsSet to set
   */
  public void setInputsSet(Set<String> inputsSet) {
    this.inputsSet = inputsSet;
  }
  /**
   * @param internalSet the internalSet to set
   */
  public void setInternalSet(Set<String> internalSet) {
    this.internalSet = internalSet;
  }
  /**
   * @param portmapCode the portmapCode to set
   */
  public void setPortmapCode(String portmapCode) {
    this.portmapCode = portmapCode;
  }

  @Override
  public String toString() {
    return portmapCode;
  }
}
