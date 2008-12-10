/**
 * 
 */
package org.mazur.algedit.device.model;

import java.util.List;
import java.util.Set;

import org.mazur.algedit.device.util.PortmapInfo;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public abstract class AbstractDevice implements Outable {

  private static final long serialVersionUID = 2059485640655309899L;
  private List<Outable> inputs;

  private String outName;
  
  /**
   * @return the outName
   */
  public String getOutName() {
    return outName;
  }

  /**
   * @param outName the outName to set
   */
  public void setOutName(String outName) {
    this.outName = outName;
  }

  /**
   * @return the inputs
   */
  public List<Outable> getInputs() {
    return inputs;
  }

  /**
   * @param inputs the inputs to set
   */
  public void setInputs(final List<Outable> inputs) {
    this.inputs = inputs;
  }  
  
  public abstract int getInCount();

  public String name() {
    return getOutName();
  }
  
  public abstract void portmap(final PortmapInfo info);
  
  public abstract PortmapInfo mainPortMap();
}
