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
  
  private static String getValue(final ArrayList<String> v, final int i) {
    if (i < v.size()) { return v.get(i); }
    return "";
  }
  public static void setValue(final ArrayList<String> v, final int i, final String value) {
    while (v.size() <= i) { v.add(""); }
    v.set(i, value);
  }
  public static void merge(final ArrayList<String> n1, final ArrayList<String> n2) {
    int l = n1.size(); if (l < n2.size()) { l = n2.size(); }
    for (int i = 0; i < l; i++) {
      String v1 = getValue(n1, i);
      if (v1.length() == 0) {
        setValue(n1, i, getValue(n2, i));
      }
    }
  }
  public String[] getNames() {
    ArrayList<String> names = new ArrayList<String>();
    for (BoolFunction f : holder.outFunctions) {
      merge(names, f.getNames());
    }
    for (BoolFunction f : holder.transFunctions) {
      merge(names, f.getNames());
    }
    return names.toArray(new String[1]);
  }
  
  static final class FunctionsHolder implements Serializable {
    private static final long serialVersionUID = -1268018407207051636L;
    private List<BoolFunction> outFunctions;
    private List<BoolFunction> transFunctions;
    private FunctionsHolder() { }
  }
}
