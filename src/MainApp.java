import controller.ControllerInterface;
//import controller.ControllerPro;
import controller.ControllerPro;
import controller.ControllerProGUI;
import model.ImageCreator;
import model.ImageCreatorImpl;
import view.View;
import view.ViewGUI;
import view.ViewGUIInterface;
import view.ViewInterface;

import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

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
      ViewGUIInterface view = new ViewGUI();
      ImageCreator imageCreator = new ImageCreatorImpl();
      ActionListener controller = new ControllerProGUI(view, in, new HashMap<>(), imageCreator);
    } else {
      if (args[0].equals("-file")) {
        String input = String.format("run" + " " + "\"%s\"" + "\nexit", args[1]);
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ViewInterface view = new View();
        ImageCreator imageCreator = new ImageCreatorImpl();
        ControllerPro controllerPro =
                new ControllerPro(view, in, new HashMap<>(), imageCreator);
      }
      else if (args[0].equals("-text")) {
        InputStream in = System.in;
        ViewInterface view = new View();
        ImageCreator imageCreator = new ImageCreatorImpl();
        ControllerInterface controller = new ControllerPro(view, in, new HashMap<>(), imageCreator);
        controller.execute();
      }
      else {
        System.out.println("Please use proper syntax.");
      }
    }
  }
}