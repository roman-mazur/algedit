package org.mazur.algedit.device.model;

import org.mazur.algedit.device.util.PortmapInfo;

public class RSTriger extends AbstractDevice {

  /** serialVersionUID. */
  private static final long serialVersionUID = -7647243256352075734L;
  
  private Outable r, s;
  
  private int index;
  
  /**
   * @return the index
   */
  public int getIndex() {
    return index;
  }

  /**
   * @param index the index to set
   */
  public void setIndex(final int index) {
    this.index = index;
  }

  public static String baseVHDL() {
    StringBuilder res = new StringBuilder();
    res.append("entity RS_Trigger is\r\n");
    res.append("  port(\r\n");
    res.append("    CLK: in bit;\r\n");
    res.append("    R: in bit := '0';\r\n");
    res.append("    S: in bit := '0';\r\n");
    res.append("    Q, nQ: out bit\r\n");
    res.append("  );\r\n");
    res.append("end RS_Trigger;\r\n");
    res.append("architecture BEHAV_RS_Trigger of RS_Trigger is\r\n");
    res.append("  signal Q0: bit := '0';\r\n");
    res.append("begin\r\n");
    res.append("  MAIN_PROC: process(CLK, R, S)\r\n");
    res.append("  begin\r\n");
    res.append("    if CLK'event and CLK = '1' then\r\n");
    res.append("      if R = '1' then Q0 <= '0'; end if;\r\n");
    res.append("      if S = '1' then Q0 <= '1'; end if;\r\n");
    res.append("      Q <= Q0;\r\n");
    res.append("      nQ <= not Q0;\r\n");
    res.append("    end if;\r\n");
    res.append("  end process;\r\n");
    res.append("end BEHAV_RS_Trigger;\r\n");
    return res.toString();
  }
  
  /**
   * @return the r
   */
  public Outable getR() {
    return r;
  }

  /**
   * @return the s
   */
  public Outable getS() {
    return s;
  }

  /**
   * @param r the r to set
   */
  public void setR(Outable r) {
    this.r = r;
  }

  /**
   * @param s the s to set
   */
  public void setS(Outable s) {
    this.s = s;
  }

  @Override
  public int getInCount() {
    // TODO Auto-generated method stub
    return 0;
  }

  public boolean out() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String name() {
    return getOutName() + index;
  }
  
  @Override
  public String toString() {
    return "RS[R=" + getR() + ", S=" + getS() + "]";
  }

  public String vhdl() {
    StringBuilder res = new StringBuilder();
    if (getR() instanceof AbstractDevice) {
      res.append(getR().vhdl()).append("\r\n");
    }
    if (getS() instanceof AbstractDevice) {
      res.append(getS().vhdl()).append("\r\n");
    }
    
    res.append("entity ").append(name()).append(" is\r\n");
    res.append("  port(\r\n");
    res.append("    R_in, S_in, CLK_in: in bit;\r\n");
    res.append("    ").append(name()).append(": out bit;\r\n");
    res.append("    n").append(name()).append(": out bit\r\n");
    res.append("  );\r\n");
    res.append("end ").append(name()).append(";\r\n");
    res.append("architecture BEHAV_").append(name()).append(" of ").append(name()).append(" is\r\n");
    res.append("begin\r\n");
    res.append("  Q_new: entity work.RS_Trigger(BEHAV_RS_Trigger)\r\n");
    res.append("    port map(\r\n");
    res.append("      S => S_in, R => R_in, CLK => CLK_in, Q => ").append(name()).append(",");
    res.append(" nQ => n").append(name()).append("\r\n");
    res.append("    );\r\n");
    res.append("end BEHAV_").append(name()).append(";\r\n");
    res.append("\r\n");
    return res.toString();
  }

  @Override
  public void portmap(PortmapInfo info) {
    if (getR() instanceof AbstractDevice) {
      ((AbstractDevice)getR()).portmap(info);
    }
    if (getS() instanceof AbstractDevice) {
      ((AbstractDevice)getS()).portmap(info);
    }
  }

  @Override
  public PortmapInfo mainPortMap() {
    PortmapInfo res = new PortmapInfo(), temp = new PortmapInfo();
    portmap(temp);
    for (String n : temp.getInputsSet()) {
      if (n.startsWith("x")) { res.getInputsSet().add(n); }
    }
    res.getInternalSet().add(name()); res.getInternalSet().add("n" + name());
    
    PortmapInfo ri = ((AbstractDevice)getR()).mainPortMap();
    res.getInputsSet().addAll(ri.getInputsSet()); res.getInternalSet().addAll(ri.getInternalSet());
    PortmapInfo si = ((AbstractDevice)getS()).mainPortMap();
    res.getInputsSet().addAll(si.getInputsSet()); res.getInternalSet().addAll(si.getInternalSet());
    
    StringBuilder s = new StringBuilder(ri.getPortmapCode() + si.getPortmapCode());
    s.append(name()).append("_entity: entity work.").append(name()).append("(BEHAV_").append(name()).append(")\r\n");
    s.append("  port map(\r\n");
    s.append("    CLK_in => CLK,\r\n");
    s.append("    R_in => R").append(index).append("_internal,\r\n");
    s.append("    S_in => S").append(index).append("_internal,\r\n");
    s.append("    Q").append(index).append(" => ").append(name()).append("_internal,\r\n");
    s.append("    nQ").append(index).append(" => n").append(name()).append("_internal\r\n");
    s.append("  );\r\n");
    res.setPortmapCode(s.toString());
    return res;
  }
}
