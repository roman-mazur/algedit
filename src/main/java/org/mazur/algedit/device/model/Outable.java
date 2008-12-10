package org.mazur.algedit.device.model;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Roman Mazur (IO-52)
 */
public interface Outable extends Serializable {
  boolean out();
  String name();
  String vhdl();
}
