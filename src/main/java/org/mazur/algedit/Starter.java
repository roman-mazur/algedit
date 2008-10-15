/**
 * 
 */
package org.mazur.algedit;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import org.mazur.algedit.gui.EditorFrame;

/**
 * Starter for editor.
 * @author Roman Mazur (IO-52)
 *
 */
public final class Starter {

  /** Standard frame dimension. */
  private static final Dimension STANDARD_DIMENSION = new Dimension(600, 500);
  
  /** File to open. */
  private File file;
  
  /**
   * Constructor.
   */
  private Starter(final File file) {
    this.file = file;
    if (this.file != null) {
      Utils.log("Try to open " + file.getAbsolutePath());
    }
  }
  
  /**
   * Starts the editor.
   */
  public void start() {
    for (int i = 0; i < 255; i++) {
      System.out.print((char)i);
    }
    EditorFrame frame = new EditorFrame("algedit");
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(final WindowEvent e) {
        System.exit(0);
      }
    });
    frame.pack();
    frame.setSize(Starter.STANDARD_DIMENSION);
    frame.setVisible(true);
  }
  
  /**
   * Main method.
   * @param args arguments.
   */
  public static void main(final String[] args) {
    String fileName = null;
    if (args.length > 0) {
      fileName = args[0];
    }
    Starter s = new Starter(fileName != null ? new File(fileName) : null);
    s.start();
  }

}
