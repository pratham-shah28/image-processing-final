package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Image;
import model.ImageCreator;
import view.ViewInterface;

/**
 * This class is an extended version of original Controller for additional functionalities such as
 * compression, histogram, color correction, level adjustment.
 * It retains support for all operations performed by original Controller.
 */
public class ControllerPro extends Controller implements ActionListener {

  private Map<String, Image> images;

  private Image img;

  private Image imgFinal;

  private Integer splitPerc;

  boolean split;


  /**
   * A constructor for ControllerPro class.
   *
   * @param view         View object.
   * @param in           InputStream object.
   * @param images       Hashmap of images.
   * @param imageCreator Factory for creating Image object.
   */
  public ControllerPro(ViewInterface view, InputStream in, HashMap<String, Image> images,
                       ImageCreator imageCreator) {
    super(view, in, images, imageCreator);
    this.images = images;
    split = false;

  }

  @Override
  public void execute() {
    this.view.setCommandButtonListener(this);
    view.welcomeMessage();
    view.enterCommandPrompt();
    Scanner scanner = new Scanner(in);
    while (scanner.hasNextLine()) {
      String input = scanner.nextLine();
      if (input.equals("exit")) {
        view.showOutput("fin.");
        break;
      }
      commandParts = getCommandComponents(input);
      String currCommand = commandParts.get(0);

      if (currCommand.equals("help")) {
        view.showCommandList();
        return;
      }
      parseCommand(currCommand, commandParts);
      runCommandPro(currCommand);
    }
  }

  private void runCommandPro(String currCommand) {
    switch (currCommand) {
      case "compress":
        try {
          if (factor >= 0 && factor <= 100) {
            if (factor != 0) {
              images.put(outputName, images.get(imageName).compress(factor));
              view.showOutput("Compress: Success");
            } else {
              images.put(outputName, images.get(imageName));
              view.showOutput("Compress: Success");
            }
          } else {
            view.showOutput("Compression factor has to be from 0 to 100");
          }
        } catch (Exception e) {
          view.showOutput("Compress: Failed: " + e);
          view.imageDoesNotExists();
        } finally {
          view.enterCommandPrompt();
        }
        break;

      case "histogram":
        try {
          images.put(outputName, images.get(imageName).createHistogram());
          view.showOutput("Histogram: Success");
        } catch (Exception e) {
          view.showOutput("Histogram: Failed: " + e);
          view.imageDoesNotExists();
        } finally {
          view.enterCommandPrompt();
        }
        break;

      case "color-correct":
        try {
          Image newImage = images.get(imageName).colorCorrect();
          if (commandParts.size() == 3) {
            images.put(outputName, newImage);
            view.showOutput("Color correct: Success");
          } else if (commandParts.get(3).equals("split")) {
            double perc = Double.parseDouble(commandParts.get(4));
            images.put(outputName, newImage.applySplit(images.get(imageName), perc));
            view.showOutput("Color correct: Success");
          } else {
            view.showCommandList();
          }
        } catch (Exception e) {
          view.showOutput("Color correct: Failed: " + e);
          view.imageDoesNotExists();
        } finally {
          view.enterCommandPrompt();
        }
        break;

      case "levels-adjust":
        try {
          if (b < 0 || m < 0 || w < 0 || b > 255 || m > 255 || w > 255) {
            throw new IllegalArgumentException("b, m, w values should be in the range 0 - 255");
          }
          if (!(b < m && b < w && m < w)) {
            throw new IllegalArgumentException("b, m, w values should be in ascending order");
          }
          Image newImage = images.get(imageName).adjustLevels(b, m, w);
          if (commandParts.size() == 6) {
            images.put(outputName, newImage);
            view.showOutput("Level adjust: Success");
          } else if (commandParts.get(6).equals("split")) {
            double perc = Double.parseDouble(commandParts.get(7));
            images.put(outputName, newImage.applySplit(images.get(imageName), perc));
            view.showOutput("Level adjust: Success");
          } else {
            view.showCommandList();
          }
        } catch (Exception e) {
          view.showOutput("Levels adjust failed: " + e);
        } finally {
          view.enterCommandPrompt();
        }
        break;
      case "sharpen":
        try {
          if (commandParts.size() == 3) {
            runCommand(currCommand);
          } else if (commandParts.get(3).equals("split")) {
            double perc = Double.parseDouble(commandParts.get(4));
            Image newImage = images.get(imageName).sharpen();
            images.put(outputName, newImage.applySplit(images.get(imageName), perc));
            view.showOutput("Sharpen: Success");
          } else {
            view.showCommandList();
          }
        } catch (Exception e) {
          view.showOutput("Sharpen: Failed: " + e);
          view.imageDoesNotExists();
        }
        break;
      case "blur":
        try {
          if (commandParts.size() == 3) {
            runCommand(currCommand);
          } else if (commandParts.get(3).equals("split")) {
            double perc = Double.parseDouble(commandParts.get(4));
            Image newImage = images.get(imageName).blur();
            images.put(outputName, newImage.applySplit(images.get(imageName), perc));
            view.showOutput("Blur: Success");
          } else {
            view.showCommandList();
          }
        } catch (Exception e) {
          view.showOutput("Blur: Failed: " + e);
          view.imageDoesNotExists();
        }
        break;

      case "luma-component":
        try {
          if (commandParts.size() == 3) {
            runCommand(currCommand);
          } else if (commandParts.get(3).equals("split")) {
            double perc = Double.parseDouble(commandParts.get(4));
            Image newImage = images.get(imageName).luma();
            images.put(outputName, newImage.applySplit(images.get(imageName), perc));
            view.showOutput("Luma Component: Success");
          } else {
            view.showCommandList();
          }
        } catch (Exception e) {
          view.showOutput("Luma Component: Failed: " + e);
          view.imageDoesNotExists();
        } finally {
          view.enterCommandPrompt();
        }
        break;
      case "sepia":
        try {
          if (commandParts.size() == 3) {
            runCommand(currCommand);
          } else {
            double perc = Double.parseDouble(commandParts.get(3));
            Image newImage = images.get(imageName).sepia();
            images.put(outputName, newImage.applySplit(images.get(imageName), perc));
            view.showOutput("Sepia: Success");
          }
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
              commandParts = getCommandComponents(line);
              currCommand = commandParts.get(0);
              if (currCommand.equals("help")) {
                view.showCommandList();
                return;
              }
              //System.out.println(line);
              parseCommand(currCommand, commandParts);
              runCommandPro(currCommand);
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
      default:
        runCommand(currCommand);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // Determine which button was clicked based on the ActionCommand
    split = view.getSplitMode();
    System.out.println(split);
    String command = e.getActionCommand();
//    System.out.println(command);
//    System.out.println(view.getSplit().getText());
//    System.out.println("SPLIT" + split);
    System.out.println("COMMAND: " + command);
    // System.out.println(view.saveOption());
    switch (command) {
      case "Apply":
        String selectedOption = (String) view.getComboBox().getSelectedItem();
        if ("red-component".equals(selectedOption)) {
          images.put("newImage", img.redComponent());
          img = img.redComponent();
//          view.updateImageLabel(img, img.createHistogram());

        } else if ("green-component".equals(selectedOption)) {
          images.put("newImage", img.greenComponent());
          img = img.greenComponent();
//          view.updateImageLabel(img, img.createHistogram());


        } else if ("blue-component".equals(selectedOption)) {
          images.put("newImage", img.blueComponent());
          img = img.blueComponent();


        } else if ("flip-vertical".equals(selectedOption)) {
          images.put("newImage", img.flipVertical());
          img = img.flipVertical();


        } else if ("flip-horizontal".equals(selectedOption)) {
          images.put("newImage", img.flipHorizontal());
          img = img.flipHorizontal();


        } else if ("blur".equals(selectedOption)) {
          images.put("newImage", img.blur());
          img = img.blur();


        } else if ("sharpen".equals(selectedOption)) {
          images.put("newImage", img.sharpen());

        } else if ("sepia".equals(selectedOption)) {
          images.put("newImage", img.sepia());
          img = img.sepia();

          // Add your code here for Option 8
        } else if ("greyscale".equals(selectedOption)) {
          images.put("newImage", img.luma());
          img = img.luma();

          System.out.println("greyscale");

        } else if ("color-correct".equals(selectedOption)) {
          images.put("newImage", img.colorCorrect());
          img = img.colorCorrect();

          System.out.println("color-correct");

        }
        if (split) {
          if (!(view.getSplit().getText().equals(""))) {
            try {
              splitPerc = Integer.parseInt(view.getSplit().getText());
            } catch (NumberFormatException ex) {
              view.showDialog("Please enter a valid integer.");
            }
          }
          else {
            splitPerc = 50;
          }
          if (splitPerc >= 1 && splitPerc <= 100) {
            view.updateImageLabel(images.get("newImage").applySplit(images.get("originalImage"), splitPerc),
                    images.get("newImage").applySplit(images.get("newImage"), splitPerc).createHistogram());
            if (view.saveOption() == 0) {
              images.put("newImage", img);
              images.put("originalImage", img);
            } else {
              images.put("newImage",images.get("newImage").applySplit(images.get("originalImage"), splitPerc) );
            }
          }
          else {
            view.showDialog("Please enter a number between 1 and 100.");
            break;
          }

        }
        view.updateImageLabel(images.get("newImage"), images.get("newImage").createHistogram());
        break;
      case "Load":
        System.out.println("Load button clicked!");
        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "png", "jpg", "ppm");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog((Component) view);

        if (result == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          BufferedImage image = null;
          try {
            image = ImageIO.read(selectedFile);
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
          img = processLoadImage(image);
          images.put("originalImage", img);
          images.put("newImage", img);
          view.updateImageLabel(img, img.createHistogram());
        }

        // Load logic
        break;

      case "Enable Split Mode":
        splitPerc = Integer.parseInt(view.getSplit().getText());
        split = !split;

      case "Set split percentage":
        if (!(view.getSplit().getText().equals(""))) {
          try {
            splitPerc = Integer.parseInt(view.getSplit().getText());
          } catch (NumberFormatException ex) {
            view.showDialog("Please enter a valid integer.");
          }
        }
        else {
          splitPerc = 50;
        }

        if (splitPerc >= 1 && splitPerc <= 100) {
//          if (splitPerc == null) {
//            splitPerc = 50;
//          }
//          imgFinal = images.get("newImage").applySplit(images.get("newImage"),splitPerc);
          System.out.println("here");
          //view.updateImageLabel(img, img.createHistogram());
          view.updateImageLabel(img.applySplit(images.get("originalImage"), splitPerc), images.get("newImage").applySplit(images.get("newImage"), splitPerc).createHistogram());
          if (view.saveOption() == 0) {
            images.put("originalImage", images.get("newImage"));
          } else {
//            images.put("newImage", images.get("originalImage"));
            images.put("newImage",images.get("newImage").applySplit(images.get("originalImage"), splitPerc) );
          }
        }
        else {
          view.showDialog("Please enter a number between 1 and 100.");
          break;
        }
        view.updateImageLabel(images.get("newImage"), images.get("newImage").createHistogram());

      case "Save":
        System.out.println("Save button clicked!");
        // save logic
        break;
      case "Compress":
        // save logic
        String userInput = view.getCompressInput().getText();

        try {
          // Parse the input as an integer
          int enteredNumber = Integer.parseInt(userInput);

          // Check if the entered number is within the valid range
          if (enteredNumber >= 1 && enteredNumber <= 100) {
            // Perform an action based on the entered number
            img = img.compress(enteredNumber);
            view.updateImageLabel(img, img.createHistogram());
            System.out.println(enteredNumber);
          } else {
            // Display an error message for an invalid range
            view.showDialog("Please enter a number between 1 and 100.");
          }
        } catch (NumberFormatException ex) {
          // Display an error message for non-integer input
          view.showDialog("Please enter a valid integer.");
        }
        break;
      // Add more cases as needed for other buttons
    }
  }
}
