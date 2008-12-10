/**
 * 
 */
package org.mazur.algedit.device.util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.mazur.algedit.device.model.AbstractDevice;
import org.mazur.algedit.device.model.DeviceModel;
import org.mazur.algedit.device.model.Nor;
import org.mazur.algedit.device.model.RSTriger;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class DeviceDrawer {
  private DeviceModel model;
  public DeviceDrawer(final DeviceModel model) {
    this.model = model;
  }
  public static String setToStr(final Set<String> set, final String prefix) {
    StringBuilder res = new StringBuilder();
    for (String inName : set) {
      res.append(inName).append(prefix);
      res.append(", ");
    }
    if (res.length() > 0) { res.delete(res.length() - 2, res.length()); }
    return res.toString();
  }
  
  @Override
  public String toString() {
    Set<String> allInputs = new HashSet<String>(), allOutputs = new HashSet<String>(),
                allInternals = new HashSet<String>();
    List<PortmapInfo> mainPortMaps = new LinkedList<PortmapInfo>();
    StringBuilder s = new StringBuilder();
    s.append(Nor.baseVHDL());
    s.append("\r\n");
    s.append(RSTriger.baseVHDL());
    s.append("\r\n");
    for (AbstractDevice device : model.getMainObject()) {
      s.append(device.vhdl());
      s.append("\r\n");
      PortmapInfo pInfo = device.mainPortMap();
      allOutputs.addAll(pInfo.getOutputSet());
      allInternals.addAll(pInfo.getInternalSet());
      allInputs.addAll(pInfo.getInputsSet());
      mainPortMaps.add(pInfo);
    }
    
    s.append("entity main is\r\n");
    s.append("  port(\r\n");
    s.append("    CLK: in bit;\r\n");
    s.append("    ").append(setToStr(allInputs, "_in")).append(": in bit;\r\n");
    s.append("    ").append(setToStr(allOutputs, "_out")).append(": out bit\r\n");
    s.append("  );\r\n");
    s.append("end main;\r\n");
    s.append("architecture BEHAV_main of main is\r\n");
    s.append("  signal ").append(setToStr(allInternals, "_internal")).append(": bit;\r\n");
    s.append("begin\r\n");
    for (PortmapInfo pInfo : mainPortMaps) {
      s.append(pInfo);
    }
    s.append("end BEHAV_main;\r\n");
    return s.toString();
  }
}
