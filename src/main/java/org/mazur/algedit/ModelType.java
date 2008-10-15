/**
 * 
 */
package org.mazur.algedit;

/**
 * Model type.
 * @author Roman Mazur (IO-52)
 */
public enum ModelType {

  /** Types. */
  ALGORITHM("alg"), GRAPH("grh");
  
  static {
    ModelType.ALGORITHM.description = "Algotithm";
    ModelType.GRAPH.description = "Graph of the finite machine";
  }
  
  /** Extensions. */
  private String[] extensions;
  private String description;
  
  /**
   * Constructor.
   * @param extensions extensions
   */
  private ModelType(final String... extensions) {
    this.extensions = extensions;
  }
  
  /**
   * @return the description
   */
  public final String getDescription() {
    return description;
  }

  /**
   * @return model type
   */
  public static ModelType byExtension(final String ext) {
    for (ModelType mt : ModelType.values()) {
      for (String e : mt.extensions) {
        if (e.equals(ext)) { return mt; }
      }
    }
    return null;
  }
  
  /**
   * @return default extension
   */
  public String getDefaultExtension() {
    return extensions[0];
  }
}
