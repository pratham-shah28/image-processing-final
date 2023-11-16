import controller.Controller;
import controller.ControllerInterface;
import model.ImageCreator;
import model.ImageCreatorImpl;
import view.View;
import view.ViewInterface;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

/**
 * This is the main class that client uses to run the application.
 * The client can directly run the application or give command line argument to run the script.
 */
public class MainApp {
  /**
   * This is the main method that runs our app by creating view, model and controller object.
   *
   * @param args command line arguments as strings.
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      InputStream in = System.in;
      ViewInterface view = new View();
      ImageCreator imageCreator = new ImageCreatorImpl();
      ControllerInterface controller = new Controller(view, in, new HashMap<>(), imageCreator);
      controller.execute();
    } else {
      String input = String.format("run" + " " + "\"%s\"" + "\nexit", args[0]);
      InputStream in = new ByteArrayInputStream(input.getBytes());
      ViewInterface view = new View();
      ImageCreator imageCreator = new ImageCreatorImpl();
      ControllerInterface controller = new Controller(view, in, new HashMap<>(), imageCreator);
      controller.execute();
    }
  }
}