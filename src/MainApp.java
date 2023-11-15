import controller.Controller;
import controller.ControllerInterface;
import model.ImageCreator;
import model.ImageCreatorImpl;
import view.View;
import view.ViewInterface;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;

/**
 * A class to run the application.
 */

public class MainApp {
  /**
   * Main method to run this class.
   *
   * @param     args String arguments.
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      InputStream in = System.in;
      ViewInterface view = new View();
      ImageCreator imageCreator = new ImageCreatorImpl();
      ControllerInterface controller = new Controller(view, in, new HashMap<>(), imageCreator);
      controller.execute();
    }
    else {
      String input = String.format("run" + " " + "\"%s\"" + "\nexit", args[0]);
      System.out.println("input: " + input);
      InputStream in = null;
      System.out.println(input);
      in = new ByteArrayInputStream(input.getBytes());
      ViewInterface view = new View();
      ImageCreator imageCreator = new ImageCreatorImpl();
      ControllerInterface controller = new Controller(view, in, new HashMap<>(), imageCreator);
      controller.execute();
    }
  }
}