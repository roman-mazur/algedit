/**
 * 
 */
package org.mazur.algedit.device.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import org.mazur.algedit.Model;
import org.mazur.algedit.ModelType;
import org.mazur.algedit.SerializeableMatrix;
import org.mazur.algedit.gui.DevicePanel;
import org.mazur.algedit.gui.ModelPanel;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class DeviceModel extends Model<List<AbstractDevice>> {

  public DeviceModel(final String name) {
    super(name);
  }
  
  public DeviceModel(final String name, final SerializeableMatrix<List<AbstractDevice>> sm) {
    super(name, sm);
  }

  @Override
  public ModelPanel<? extends Model<List<AbstractDevice>>> createPanel() {
    return new DevicePanel(this);
  }

  @Override
  public ModelType getType() {
    return ModelType.DEVICE;
  }

  @Override
  protected void save(final ObjectOutputStream stream) throws IOException {
    stream.writeObject(new DeviceSerializer(getMainObject()));
  }

}
