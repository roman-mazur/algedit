/**
 * 
 */
package org.mazur.algedit.boolfunc.model;

import org.mazur.algedit.SerializeableMatrix;
import org.mazur.algedit.boolfunc.model.BoolFunctionModel.FunctionsHolder;

/**
 * @author Roman Mazur (IO-52)
 */
public class BoolFunctionSerializer implements SerializeableMatrix<FunctionsHolder> {

  private static final long serialVersionUID = 4647394090273749359L;

  private FunctionsHolder holder;
  
  public BoolFunctionSerializer(final FunctionsHolder holder) {
    this.holder = holder;
  }
  
  public BoolFunctionModel.FunctionsHolder build() {
    return holder;
  }

}
