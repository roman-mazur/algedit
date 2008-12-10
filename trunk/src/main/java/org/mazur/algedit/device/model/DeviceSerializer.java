/**
 * 
 */
package org.mazur.algedit.device.model;

import java.util.List;

import org.mazur.algedit.SerializeableMatrix;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class DeviceSerializer implements SerializeableMatrix<List<AbstractDevice>> {

  private static final long serialVersionUID = -4905339175733055481L;
  private List<AbstractDevice> devices;
  
  public DeviceSerializer(final List<AbstractDevice> o) {
    devices = o;
  }
  
  public List<AbstractDevice> build() {
    return devices;
  }

}
