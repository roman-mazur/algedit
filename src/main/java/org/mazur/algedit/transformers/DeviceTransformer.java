/**
 * 
 */
package org.mazur.algedit.transformers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.mazur.algedit.Transformer;
import org.mazur.algedit.boolfunc.model.BoolFunction;
import org.mazur.algedit.boolfunc.model.BoolFunctionModel;
import org.mazur.algedit.boolfunc.model.ConjunctionTerm;
import org.mazur.algedit.device.model.AbstractDevice;
import org.mazur.algedit.device.model.DeviceModel;
import org.mazur.algedit.device.model.In;
import org.mazur.algedit.device.model.Nor;
import org.mazur.algedit.device.model.Outable;
import org.mazur.algedit.device.model.RSTriger;
import org.mazur.algedit.exceptions.TransformException;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class DeviceTransformer implements Transformer<BoolFunctionModel, DeviceModel> {

  private int intCounter = 0;
  
  public String getName() {
    return "Device transformer";
  }

  private List<Outable> group(final List<Outable> inputs, boolean k) {
    if (inputs.size() == 1) { return inputs; }
    List<Outable> result = new LinkedList<Outable>();
    
    if (inputs.size() <= 3) {
      Nor nor = new Nor();
      nor.setInputs(inputs);
      while (nor.getInputs().size() < 3) {
        nor.getInputs().add(inputs.get(inputs.size() - 1));
      }
      nor.setOutName("internal" + intCounter++);
      nor.setInternal(true);
      if (k) {
        result.add(nor);
        return result;
      }
      ArrayList<Outable> ins = new ArrayList<Outable>(3);
      ins.add(nor); ins.add(nor); ins.add(nor);
      Nor nor2 = new Nor();
      nor2.setInputs(ins);
      nor2.setOutName("internal" + intCounter++);
      nor2.setInternal(true);
      result.add(nor2);
      return result;
    }
    
    int c = 0;
    List<Outable> list = null;
    for (Outable in : inputs) {
      if (c == 0) {
        if (list != null) {
          result.add(group(list, false).get(0));
        }
        list = new ArrayList<Outable>(3);
      }
      list.add(in);
      c++;
      if (c == 3) { c = 0; }
    }
    if (c > 0) { result.add(group(list, false).get(0)); }
    return group(result, true);
  }
  
  private Nor nor(final BoolFunction func) {
    if (func.getDisjunction().size() == 0) { return null; }
    List<Outable> lastIns = new LinkedList<Outable>();
    for (ConjunctionTerm term : func.getDisjunction()) {
      List<Outable> inputs = term.getIns();
      for (Outable i : inputs) {
        ((In)i).setInverse(!((In)i).isInverse());
      }
      lastIns.add(group(inputs, true).get(0));
    }
    Nor res =(Nor)group(lastIns, false).get(0); 
    res.setOutName(func.getName() + func.getIndex());
    res.setInternal(false);
    return res;
  }
  
  public DeviceModel transform(final BoolFunctionModel source)
      throws TransformException {
    List<AbstractDevice> devices = new ArrayList<AbstractDevice>(source.getOutFunctions().size() 
        + (source.getTransFunctions().size() >> 1));
    for (BoolFunction func : source.getOutFunctions()) {
      Nor d = nor(func);
      if (d == null) { continue; }
      devices.add(d);
    }
    Iterator<BoolFunction> it = source.getTransFunctions().iterator();
    int i = 0;
    while (it.hasNext()) {
      RSTriger t = new RSTriger();
      t.setIndex(i++);
      t.setOutName("Q");
      t.setR(nor(it.next()));
      t.setS(nor(it.next()));
      devices.add(t);
    }
    DeviceModel res = new DeviceModel(source.getName());
    res.setMainObject(devices);
    return res;
  }

}
