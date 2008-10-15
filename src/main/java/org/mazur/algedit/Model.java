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
import org.mazur.algedit.exceptions.NotSupportedModelTypeException;

/**
 * Model. 
 * @param <T> main model object
 * @author Roman Mazur (IO-52)
 *
 */
public abstract class Model<T> {

  /** Main object. */
  private T mainObject;
  
  /**
   * Constructor.
   * @param sm main model object
   */
  public Model(final SerializeableMatrix<T> sm) {
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
  protected final T getMainObject() {
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
  public static Model<?> load(final InputStream stream, final ModelType type) throws IOException {
    ObjectInputStream is = new ObjectInputStream(stream);
    try {
      switch (type) {
      case ALGORITHM:
        return new AlgorithmModel((AlgorithmMatrix)is.readObject());
      default:
        throw new NotSupportedModelTypeException();
      }
    } catch (ClassNotFoundException e) {
      throw new NotSupportedModelTypeException(e);
    }
  }
}
