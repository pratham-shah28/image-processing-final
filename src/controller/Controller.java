package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.ImageCreator;
import view.ViewInterface;
import model.Image;


/**
 * This is the controller class that coordinates between the model and view, while catering to user.
 * This class handles IO operations i.e. it takes input from user
 * and delegates work to the model or view.
 * This class implements the ControllerInterface.
 */
public class Controller implements ControllerInterface {
  protected ViewInterface view;
  private Map<String, Image> images;
  protected InputStream in;
  private ImageCreator imageCreator;
  protected String path;
  protected String imageName;
  protected String outputName;
  protected String redImageName;
  protected String greenImageName;
  protected String blueImageName;
  protected double factor;
  protected int b;
  protected int m;
  protected int w;
  private ControllerUtil controllerUtil;
  protected List<String> commandParts;

  /**
   * Constructs the controller using the given view, model, input stream and image hashmap.
   *
   * @param view   View object.
   * @param in     InputStream object.
   * @param images Hashmap of images.
   */
  public Controller(ViewInterface view, InputStream in, HashMap<String, Image> images,
                    ImageCreator imageCreator) {
    if (view == null || in == null || images == null || imageCreator == null) {
      throw new IllegalArgumentException("Controller cannot be created. Check arguments");
    }
    this.view = view;
    this.in = in;
    this.images = images;
    this.controllerUtil = new ControllerUtil();
    this.imageCreator = imageCreator;
    path = null;
    imageName = null;
    outputName = null;
    redImageName = null;
    greenImageName = null;
    blueImageName = null;
    factor = 0;
    b = 0;
    m = 0;
    w = 0;
  }

  @Override
  public void execute() {
    view.welcomeMessage();
    view.enterCommandPrompt();
    Scanner scanner = new Scanner(in);
    while (scanner.hasNextLine()) {
      String input = scanner.nextLine();
      if (input.equals("exit")) {
        view.showOutput("fin.");
        break;
      }
      List<String> commandParts = getCommandComponents(input);
      String currCommand = commandParts.get(0);
      if (currCommand.equals("help")) {
        view.showCommandList();
        return;
      }
      parseCommand(currCommand, commandParts);
      runCommand(currCommand);
    }
  }

  /**
   * This method split a command into respective components and return a list of the same.
   *
   * @param input command to be split into components.
   * @return a list containing command components.
   */
  protected List<String> getCommandComponents(String input) {
    List<String> commandParts = new ArrayList<>();
    try {
      Pattern pattern = Pattern.compile("\"([^\"]*)\"|([^\\s]+)");
      Matcher matcher = pattern.matcher(input);

      while (matcher.find()) {
        String matchedArg = matcher.group(1);
        if (matchedArg != null) {
          commandParts.add(matchedArg);
        } else {
          commandParts.add(matcher.group(2));
        }
      }
    } catch (Exception e) {
      view.showOutput("" + e);
    }
    return commandParts;
  }

  /**
   * This method runs the given commands and calls the respective model method
   * to apply transformation on the image.
   *
   * @param currCommand the current command to be run.
   */
  protected void runCommand(String currCommand) {
    String status = "";
    switch (currCommand) {
      case "load":
        try {
          status = controllerUtil.loadImage(path, imageName, images, imageCreator);
          view.showOutput(status);
        } catch (Exception e) {
          view.showOutput(status);
        } finally {
          view.enterCommandPrompt();
        }
        break;
      case "brighten":
        try {
          images.put(outputName, images.get(imageName).brighten((int) factor));
          view.showOutput("Brighten: Success");
        } catch (Exception e) {
          view.showOutput("Brighten: Failed: " + e);
          view.imageDoesNotExists();
        } finally {
          view.enterCommandPrompt();
        }
        break;
      case "vertical-flip":
        try {
          images.put(outputName, images.get(imageName).flipVertical());
          view.showOutput("Vertical Flip: Success");
        } catch (Exception e) {
          view.showOutput("Vertical FLip: Failed: " + e);
          view.imageDoesNotExists();
        } finally {
          view.enterCommandPrompt();
        }
        break;
      case "horizontal-flip":
        try {
          images.put(outputName, images.get(imageName).flipHorizontal());
          view.showOutput("Horizontal Flip: Success");
        } catch (Exception e) {
          view.showOutput("Horizontal Flip: Failed: " + e);
          view.imageDoesNotExists();
        } finally {
          view.enterCommandPrompt();
        }
        break;
      case "rgb-split":
        try {
          images.put(redImageName, images.get(imageName).redComponent());
          images.put(greenImageName, images.get(imageName).greenComponent());
          images.put(blueImageName, images.get(imageName).blueComponent());
          view.showOutput("RGB split: Success");
        } catch (Exception e) {
          view.showOutput("RGB Split: Failed: " + e);
          view.imageDoesNotExists();
        } finally {
          view.enterCommandPrompt();
        }
        break;
      case "rgb-combine":
        try {
          images.put(imageName, images.get(redImageName).combine(images.get(redImageName),
                  images.get(greenImageName),
                  images.get(blueImageName)));
          view.showOutput("RGB combine: Success");
        } catch (Exception e) {
          view.showOutput("RGB Combine: Failed: " + e);
          view.imageDoesNotExists();
        } finally {
          view.enterCommandPrompt();
        }
        break;
      case "blur":
        try {
          images.put(outputName, images.get(imageName).blur());
          view.showOutput("Blur: Success");
        } catch (Exception e) {
          view.showOutput("Blur: Failed: " + e);
          view.imageDoesNotExists();
        } finally {
          view.enterCommandPrompt();
        }
        break;
      case "sharpen":
        try {
          images.put(outputName, images.get(imageName).sharpen());
          view.showOutput("Sharpen: Success");
        } catch (Exception e) {
          view.showOutput("Sharpen: Failed: " + e);
          view.imageDoesNotExists();
        } finally {
          view.enterCommandPrompt();
        }
        break;
      case "value-component":
        try {
          images.put(outputName, images.get(imageName).value());
          view.showOutput("Value Component: Success");
        } catch (Exception e) {
          view.showOutput("Value Component: Failed: " + e);
          view.showCommandList();
        } finally {
          view.enterCommandPrompt();
        }
        break;
      case "intensity-component":
        try {
          images.put(outputName, images.get(imageName).intensity());
          view.showOutput("Intensity Component: Success");
        } catch (Exception e) {
          view.showOutput("Intensity Component: Failed: " + e);
          view.imageDoesNotExists();
        } finally {
          view.enterCommandPrompt();
        }
        break;
      case "luma-component":
        try {
          images.put(outputName, images.get(imageName).luma());
          view.showOutput("Luma Component: Success");
        } catch (Exception e) {
          view.showOutput("Luma Component: Failed: " + e);
          view.imageDoesNotExists();
        } finally {
          view.enterCommandPrompt();
        }
        break;
      case "red-component":
        try {
          images.put(outputName, images.get(imageName).redComponent());
          view.showOutput("Red Component: Success");
        } catch (Exception e) {
          view.showOutput("Red Component: Failed: " + e);
          view.imageDoesNotExists();
        } finally {
          view.enterCommandPrompt();
        }
        break;
      case "green-component":
        try {
          images.put(outputName, images.get(imageName).greenComponent());
          view.showOutput("Green Component: Success");
        } catch (Exception e) {
          view.showOutput("Green Component: Failed: " + e);
          view.imageDoesNotExists();
        } finally {
          view.enterCommandPrompt();
        }
        break;
      case "blue-component":
        try {
          images.put(outputName, images.get(imageName).blueComponent());
          view.showOutput("Blue Component: Success");
        } catch (Exception e) {
          view.showOutput("Blue Component: Failed: " + e);
          view.imageDoesNotExists();
        } finally {
          view.enterCommandPrompt();
        }
        break;
      case "sepia":
        try {
          images.put(outputName, images.get(imageName).sepia());
          view.showOutput("Sepia: Success");
        } catch (Exception e) {
          view.showOutput("Sepia: Failed: " + e);
          view.imageDoesNotExists();
        } finally {
          view.enterCommandPrompt();
        }
        break;
      case "run":
        try {
          int lastDotIndex = path.lastIndexOf('.');
          String format = path.substring(lastDotIndex + 1);
          if (format.equals("txt")) {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
              runCommand(line);
            }
            view.showOutput("Run: Success");
          } else {
            throw new IllegalArgumentException("Please input text file only.");
          }
          break;
        } catch (Exception e) {
          view.showOutput("Run: Failed: " + e);
        } finally {
          view.enterCommandPrompt();
        }
        break;
      case "save":
        try {
          status = controllerUtil.saveImage(path, images.get(imageName));
          view.showOutput(status);
        } catch (Exception e) {
          view.showOutput(status);
          view.imageDoesNotExists();
        } finally {
          view.enterCommandPrompt();
        }
        break;
      default:
        view.enterCommandPrompt();
    }
  }

  /**
   * This method parses the command and its components.
   * It identifies and stores the components based on the command syntax.
   *
   * @param currCommand  the current command to be parsed.
   * @param commandParts list containing command parts.
   */
  protected void parseCommand(String currCommand, List<String> commandParts) {
    if (currCommand.equals("run")) {
      try {
        path = commandParts.get(1);
      } catch (Exception e) {
        view.showCommandList();
      }
    } else if (currCommand.equals("brighten") || currCommand.equals("compress")) {
      try {
        factor = Double.parseDouble(commandParts.get(1));
        imageName = commandParts.get(2);
        outputName = commandParts.get(3);
      } catch (Exception e) {
        view.showCommandList();
      }
    } else if (currCommand.equals("load") || currCommand.equals("save")) {
      try {
        path = commandParts.get(1);
        imageName = commandParts.get(2);
      } catch (Exception e) {
        view.showCommandList();
      }
    } else if (currCommand.equals("rgb-split") || currCommand.equals("rgb-combine")) {
      try {
        imageName = commandParts.get(1);
        redImageName = commandParts.get(2);
        greenImageName = commandParts.get(3);
        blueImageName = commandParts.get(4);
      } catch (Exception e) {
        view.showCommandList();
      }
    } else if (currCommand.equals("sepia")
            || currCommand.equals("blur")
            || currCommand.equals("sharpen")
            || currCommand.equals("vertical-flip")
            || currCommand.equals("horizontal-flip")
            || currCommand.equals("red-component")
            || currCommand.equals("green-component")
            || currCommand.equals("blue-component")
            || currCommand.equals("intensity-component")
            || currCommand.equals("luma-component")
            || currCommand.equals("value-component")
            || currCommand.equals("histogram")
            || currCommand.equals("color-correct")) {
      try {
        imageName = commandParts.get(1);
        outputName = commandParts.get(2);
      } catch (Exception e) {
        view.showCommandList();
      }
    } else if (currCommand.equals("levels-adjust")) {
      try {
        b = Integer.parseInt(commandParts.get(1));
        m = Integer.parseInt(commandParts.get(2));
        w = Integer.parseInt(commandParts.get(3));
        imageName = commandParts.get(4);
        outputName = commandParts.get(5);
      } catch (Exception e) {
        view.showCommandList();
      }
    } else {
      view.showOutput("Invalid Command. Press 'help' to list the supported commands.");
    }
  }
}
