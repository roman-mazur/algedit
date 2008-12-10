/**
 * 
 */
package org.mazur.algedit.device.model;

import org.mazur.algedit.device.util.PortmapInfo;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class Nor extends AbstractDevice {
  
  private static final long serialVersionUID = 509157972836062245L;

  private boolean internal = false;
  
  /**
   * @return the internal
   */
  public boolean isInternal() {
    return internal;
  }

  /**
   * @param internal the internal to set
   */
  public void setInternal(boolean internal) {
    this.internal = internal;
  }

  public static String baseVHDL() {
    StringBuilder res = new StringBuilder();
    res.append("entity NOR3 is\r\n");
    res.append("  port(\r\n");
    res.append("    x1, x2, x3: in bit;\r\n");
    res.append("    y: out bit\r\n");
    res.append("  );\r\n");
    res.append("end NOR3;\r\n");
    res.append("architecture BEHAV_NOR3 of NOR3 is\r\n");
    res.append("begin\r\n");
    res.append("  MAIN_PROC: process(x1, x2, x3)\r\n");
    res.append("  begin\r\n");
    res.append("    y <= not(x1 or x2 or x3);\r\n");
    res.append("  end process;\r\n");
    res.append("end BEHAV_NOR3;\r\n");
    return res.toString();
  }
  
  /**
   * {@inheritDoc}
   * @see org.mazur.algedit.device.model.Outable#out()
   */
  public boolean out() {
    boolean res = false;
    for (Outable out : getInputs()) {
      if (res |= out.out()) { break; };
    }
    return !res;
  }

  @Override
  public int getInCount() {
    return 3;
  }

  @Override
  public String toString() {
    return "[3NOR" + getInputs() + "]";
  }
  
  public String vhdl() {
    PortmapInfo pInfo = new PortmapInfo();
    portmap(pInfo);
    StringBuilder signals = new StringBuilder();
    for (String sName : pInfo.getInternalSet()) {
      signals.append(sName);
      signals.append(", ");
    }
    if (signals.length() > 0) { signals.delete(signals.length() - 2, signals.length()); }
    StringBuilder inputs = new StringBuilder();
    for (String inName : pInfo.getInputsSet()) {
      inputs.append(inName);
      inputs.append(", ");
    }
    if (inputs.length() > 0) { inputs.delete(inputs.length() - 2, inputs.length()); }
    
    StringBuilder res = new StringBuilder();
    res.append("entity ").append(name()).append(" is\r\n");
    res.append("  port(\r\n");
    if (inputs.length() > 0) {
      res.append("    ").append(inputs).append(": in bit;\r\n");
    }
    res.append("    ").append(name()).append(": out bit\r\n");
    res.append("  );\r\n");
    res.append("end ").append(name()).append(";\r\n");
    res.append("architecture BEHAV_").append(name()).append(" of ").append(name()).append(" is\r\n");
    if (signals.length() > 0) {
      res.append("  signal ").append(signals).append(": bit;\r\n");
    }
    res.append("begin\r\n");
    res.append(pInfo.getPortmapCode());
    res.append("end BEHAV_").append(name()).append(";\r\n");
    res.append("\r\n");
    return res.toString();
  }

  @Override
  public void portmap(final PortmapInfo info) {
    StringBuilder inMap = new StringBuilder();
    int i = 0;
    for (Outable in : getInputs()) {
      String name = in.name();
      if (in instanceof In) {
        if (((In)in).isInverse()) { name = "n" + name; }
        info.getInputsSet().add(name);
      } else if (in instanceof AbstractDevice) {
        ((AbstractDevice)in).portmap(info);
      }
      inMap.append("    x").append(++i).append(" => ").append(name).append(",\r\n");
    }
    if (!info.getInternalSet().contains(name())) {
      StringBuilder mapping = new StringBuilder(name() + "_entity:");
      mapping.append(" entity work.NOR3(BEHAV_NOR3)\r\n");
      mapping.append("  port map(\r\n");
      mapping.append(inMap);
      mapping.append("    y => ").append(name()).append("\r\n");
      mapping.append("  );\r\n");
      info.setPortmapCode(info.getPortmapCode() + mapping.toString());
    }
    if (internal) { info.getInternalSet().add(name()); }
  }

  @Override
  public PortmapInfo mainPortMap() {
    PortmapInfo res = new PortmapInfo(), temp = new PortmapInfo();
    portmap(temp);
    for (String n : temp.getInputsSet()) {
      if (n.startsWith("x")) { res.getInputsSet().add(n); }
    }
    if (name().startsWith("y")) { res.getOutputSet().add(name()); } 
    if (name().startsWith("R") || name().startsWith("S")) { res.getInternalSet().add(name()); }
    
    StringBuilder s = new StringBuilder();
    s.append(name()).append("_entity: entity work.").append(name()).append("(BEHAV_").append(name()).append(")\r\n");
    s.append("  port map(\r\n");
    for (String n : temp.getInputsSet()) {
      s.append("    ").append(n).append(" => ").append(n);
      s.append(n.startsWith("x") ? "_in" : "_internal");
      s.append(",\r\n");
    }
    s.append("    ").append(name()).append(" => ").append(name());
    s.append(name().startsWith("y") ? "_out" : "_internal");
    s.append("\r\n");
    s.append("  );\r\n");
    res.setPortmapCode(s.toString());
    return res;
  }

}
