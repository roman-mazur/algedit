/**
 * 
 */
package org.mazur.algedit.boolfunc.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.mazur.algedit.Model;
import org.mazur.algedit.ModelType;
import org.mazur.algedit.SerializeableMatrix;
import org.mazur.algedit.gui.BoolFunctionsPanel;
import org.mazur.algedit.gui.ModelPanel;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class BoolFunctionModel extends Model<BoolFunctionModel.FunctionsHolder> {

  /** Holder. */
  private FunctionsHolder holder;
  
  public BoolFunctionModel(final String name) {
    super(name);
    holder = new FunctionsHolder();
  }

  public BoolFunctionModel(final String name, final SerializeableMatrix<BoolFunctionModel.FunctionsHolder> sm) {
    super(name, sm);
  }

  @Override
  public ModelPanel<? extends Model<BoolFunctionModel.FunctionsHolder>> createPanel() {
    return new BoolFunctionsPanel(this);
  }

  @Override
  public ModelType getType() { return ModelType.BOOL_FUNCTIONS; }

  @Override
  protected void save(final ObjectOutputStream stream) throws IOException {
    stream.writeObject(new BoolFunctionSerializer(holder));
  }

  public void setOutFunctions(final int count) {
    holder.outFunctions = new ArrayList<BoolFunction>(count);
  }
  
  public void setTransFunctions(final int count) {
    holder.transFunctions = new ArrayList<BoolFunction>(count);
  }

  public List<BoolFunction> getOutFunctions() { return holder.outFunctions; }
  public List<BoolFunction> getTransFunctions() { return holder.transFunctions; }
  
  static final class FunctionsHolder implements Serializable {
    private static final long serialVersionUID = -1268018407207051636L;
    private List<BoolFunction> outFunctions;
    private List<BoolFunction> transFunctions;
    private FunctionsHolder() { }
  }
}
