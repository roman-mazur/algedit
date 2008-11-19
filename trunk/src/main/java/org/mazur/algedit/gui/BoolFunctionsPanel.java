/**
 * 
 */
package org.mazur.algedit.gui;

import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.JLabel;

import org.mazur.algedit.boolfunc.model.BoolFunction;
import org.mazur.algedit.boolfunc.model.BoolFunctionModel;

/**
 * @author Roman Mazur (IO-52)
 */
public class BoolFunctionsPanel extends ModelPanel<BoolFunctionModel> {

  private static final long serialVersionUID = 4218611765501400379L;

  public BoolFunctionsPanel(final BoolFunctionModel model) {
    super(model);
    setLayout(new GridLayout(model.getTransFunctions().size() 
        + model.getOutFunctions().size() + 1, 1));
    for (BoolFunction f : model.getOutFunctions()) {
      add(new JLabel(f.getDescription()));
    }
    add(new JLabel());
    for (BoolFunction f : model.getTransFunctions()) {
      add(new JLabel(f.getDescription()));
    }
  }

  @Override
  public Icon getIcon() {
    return null;
  }

}
