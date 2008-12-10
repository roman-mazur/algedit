/**
 * 
 */
package org.mazur.algedit.gui;

import java.awt.BorderLayout;
import java.awt.TextArea;

import javax.swing.Icon;

import org.mazur.algedit.device.model.DeviceModel;
import org.mazur.algedit.device.util.DeviceDrawer;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class DevicePanel extends ModelPanel<DeviceModel> {

  private static final long serialVersionUID = 6048146342551288028L;

  public DevicePanel(final DeviceModel model) {
    super(model);
    setLayout(new BorderLayout());
    String text = new DeviceDrawer(getModel()).toString();
    add(BorderLayout.CENTER, new TextArea(text));
  }

  @Override
  public Icon getIcon() {
    return null;
  }

}
