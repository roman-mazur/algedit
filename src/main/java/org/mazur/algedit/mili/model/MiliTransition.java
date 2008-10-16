/**
 * 
 */
package org.mazur.algedit.mili.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Roman Mazur (IO-52)
 *
 */
public class MiliTransition implements Serializable {

  /** serialVersionUID. */
  private static final long serialVersionUID = -8816751531390153040L;

  /** Source and target. */
  private MiliVertex source, target;

  private int ySignal = -1;
  private List<TransmitionCondition> conditions = new LinkedList<TransmitionCondition>();
  
  /**
   * @return the ySignal
   */
  public final int getYSignal() {
    return ySignal;
  }

  /**
   * @return the xSignal
   */
  public final List<TransmitionCondition> getConditions() {
    return conditions;
  }

  /**
   * @param signal the ySignal to set
   */
  public final void setYSignal(int signal) {
    ySignal = signal;
  }

  /**
   * @param signal the xSignal to set
   */
  public void addCondition(final int signal, final boolean desc) {
    conditions.add(new TransmitionCondition(signal, desc));
  }

  /**
   * @return the source
   */
  public final MiliVertex getSource() { return source; }

  /**
   * @return the target
   */
  public final MiliVertex getTarget() { return target; }

  /**
   * @param source the source to set
   */
  public final void setSource(final MiliVertex source) {
    this.source = source;
  }

  /**
   * @param target the target to set
   */
  public final void setTarget(final MiliVertex target) {
    this.target = target;
  }

  @Override
  public String toString() {
    return "{to" + target.getCode() + ",y" + ySignal + ", " + conditions + "}";
  }
  
  public class TransmitionCondition {
	private int index = -1;
	private boolean desc = true;
	
	private TransmitionCondition(final int index, final boolean desc) {
	  this.index = index;
	  this.desc = desc;
	}

	public final int getIndex() { return index; }

	public final boolean isDesc() { return desc; }
	
	@Override
	public String toString() {
      return (!desc ? "~" : "") + "x" + index;
	}
  }
}
