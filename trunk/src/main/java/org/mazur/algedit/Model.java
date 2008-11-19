/**
 * 
 */
package org.mazur.algedit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.mazur.algedit.alg.model.AlgorithmMatrix;
import org.mazur.algedit.alg.model.AlgorithmModel;
import org.mazur.algedit.boolfunc.model.BoolFunctionModel;
import org.mazur.algedit.boolfunc.model.BoolFunctionSerializer;
import org.mazur.algedit.exceptions.NotSupportedModelTypeException;
import org.mazur.algedit.gui.ModelPanel;
import org.mazur.algedit.mili.model.MiliGraphModel;
import org.mazur.algedit.mili.model.MiliMatrix;
import org.mazur.algedit.transtable.model.TableSerializer;
import org.mazur.algedit.transtable.model.TransTableModel;

/**
 * Model. 
 * @param <T> main model object
 * @author Roman Mazur (IO-52)
 *
 */
public abstract class Model<T> {

  /** Model name. */
  private String name;
  
  /** Main object. */
  private T mainObject = null;
  
  /** 
   * Constructor.
   * @param name model name 
   */
  public Model(final String name) {
    super();
    setName(name);
  }
  
  /**
   * Constructor.
   * @param sm main model object
   */
  public Model(final String name, final SerializeableMatrix<T> sm) {
    this(name);
    mainObject = sm.build();
  }
  
  /**
   * @param mainObject the mainObject to set
   */
  public final void setMainObject(final T mainObject) {
    this.mainObject = mainObject;
  }

  /**
   * @return the mainObject
   */
  public final T getMainObject() {
    return mainObject;
  }

  /**
   * @param stream stream to use for saving
   * @throws IOException I/O exception
   */
  protected abstract void save(final ObjectOutputStream stream) throws IOException;
  
  /**
   * @param stream stream to use for saving
   * @throws IOException I/O exception
   */
  public void save(final OutputStream stream) throws IOException {
    save(new ObjectOutputStream(stream));
  }
  
  /**
   * @param file file to use for saving
   * @throws IOException I/O exception
   */
  public void save(final File file) throws IOException {
    FileOutputStream os = new FileOutputStream(file);
    save(os);
    setName(file.getName());
  }
  
  /**
   * @param baseDir base directory
   * @param name file name
   * @throws IOException I/O exception
   */
  public void save(final File baseDir, final String name) throws IOException {
    save(new File(baseDir, name + "." + getType().getDefaultExtension()));
  }
  
  /**
   * @return model type
   */
  public abstract ModelType getType();
  
  /**
   * @param stream stream to load
   * @return model from the stream
   * @exception IOException I/O exception
   */
  public static Model<?> load(final String name, final InputStream stream, final ModelType type) throws IOException {
    ObjectInputStream is = new ObjectInputStream(stream);
    try {
      switch (type) {
      case ALGORITHM:
        return new AlgorithmModel(name, (AlgorithmMatrix)is.readObject());
      case GRAPH:
        return new MiliGraphModel(name, (MiliMatrix)is.readObject());
      case TRANS_TABLE:
        return new TransTableModel(name, (TableSerializer)is.readObject());
      case BOOL_FUNCTIONS:
        return new BoolFunctionModel(name, (BoolFunctionSerializer)is.readObject());
      default:
        throw new NotSupportedModelTypeException();
      }
    } catch (ClassNotFoundException e) {
      throw new NotSupportedModelTypeException(e);
    }
  }
  
  /**
   * @return panel to deal with this model
   */ 
  public abstract ModelPanel<? extends Model<T>> createPanel();
  
  /**
   * @return the name
   */
  public final String getName() {
    return name;
  }

  /**
   * @param name name
   */
  protected final void setName(final String name) {
    this.name = name;
  }
}
