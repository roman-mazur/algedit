/**
 * 
 */
package org.mazur.algedit.gui;

import javax.swing.Icon;
import javax.swing.JPanel;

import org.mazur.algedit.Model;

/**
 * Panel dedicated for working with the model.
 * @param <T> model type
 * @author Roman Mazur (IO-52)
 */
public abstract class ModelPanel<T extends Model<?>> extends JPanel {

  /** serialVersionUID. */
  private static final long serialVersionUID = 3724410475239129859L;

  /** Instance of the model. */
  private transient T model;
  
  /**
   * Constructor.
   * @param model model to deal with
   */
  public ModelPanel(final T model) {
    this.model = model;
  }
  
  /**
   * @return the model
   */
  public T getModel() { return model; }
  
  /** 
   * @return the short name
   */
  public abstract Icon getIcon(); 

  /** 
   * @return the short name
   */
  public String getDescription() {
    return model.getType().getDescription();
  }
  
  /**
   * @return panel caption
   */
  public String getCaption() {
    return model.getName();
  }
}
