/**
 * 
 */
package org.mazur.algedit;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * Model type.
 * @author Roman Mazur (IO-52)
 */
public enum ModelType {

  /** Types. */
  ALGORITHM("alg"), GRAPH("grh");
  
  static {
    ModelType.ALGORITHM.setDescription("Algotithm");
    ModelType.GRAPH.setDescription("Graph of the finite machine");
  }
  
  /** Extensions. */
  private String[] extensions;
  /** Description. */
  private String description;
  /** File filter. */
  private FileFilter filter;
  
  /**
   * Constructor.
   * @param extensions extensions
   */
  private ModelType(final String... extensions) {
    this.extensions = extensions;
  }
  
  /**
   * @return the filter
   */
  public final FileFilter getFilter() {
    return filter;
  }

  private void setDescription(final String desc) {
    this.description = desc;
    this.filter = new FileFilter() {
      @Override
      public boolean accept(final File f) {
        for (String ext : ModelType.this.extensions) {
          if (ext.equals(ModelType.getExtension(f))) {
            return true;
          }
        }
        return false;
      }
      @Override
      public String getDescription() {
        return ModelType.this.description;
      }
    };
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
  
  private static String getExtension(final File file) {
    if (file == null) { return null; }
    String name = file.getName();
    int i = name.lastIndexOf('.');
    if (i >= 0 && i < name.length() - 1) {
      return name.substring(i + 1).toLowerCase();
    }
    return null;
  }

  
  /**
   * @param f file
   * @return model type
   */
  public static ModelType byFile(final File f) {
    return byExtension(ModelType.getExtension(f));
  }
  
  /**
   * @return default extension
   */
  public String getDefaultExtension() {
    return extensions[0];
  }
  
}
