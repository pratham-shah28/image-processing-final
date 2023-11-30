package controller;

import java.awt.*;
import java.io.FileWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Image;
import model.ImageCreator;
import view.ViewGUIInterface;
import view.ViewInterface;


/**
 * This class is an extended version of original Controller for additional functionalities such as
 * compression, histogram, color correction, level adjustment.
 * It retains support for all operations performed by original Controller.
 */
public class ControllerProGUI implements ActionListener {

  protected ViewGUIInterface view;

  private Map<String, Image> images;

  private Image img;

  private HashSet<String> supportedFormats;

  private ImageCreator imageCreator;

  private Image imgFinal;

  private Integer splitPerc;

  boolean split;

  boolean isSaved;


  /**
   * A constructor for ControllerPro class.
   *
   * @param view         View object.
   * @param in           InputStream object.
   * @param images       Hashmap of images.
   * @param imageCreator Factory for creating Image object.
   */
  public ControllerProGUI(ViewGUIInterface view, InputStream in, HashMap<String, Image> images,
                          ImageCreator imageCreator) {
//    super(view, in, images, imageCreator);
    this.view = view;
    this.images = images;
    split = false;
    view.setCommandButtonListener(this);
    this.imageCreator = imageCreator;
    supportedFormats = new HashSet<>();
    supportedFormats.add("jpg");
    supportedFormats.add("png");
    supportedFormats.add("ppm");

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    if (command.equals("Enable Split Mode")) {
      split = !split;
      view.toggleSet(split);
      if (images.containsKey("newImage")) {
        images.put("splitImage", images.get("newImage").applySplit(images.get("originalImage"), 50));
        view.updateImageLabel(images.get("splitImage"), images.get("splitImage").createHistogram());
      }
      return;
    }

    if (command.equals("Disable Split Mode")) {
      split = !split;
      view.toggleSet(split);
      if (images.containsKey("originalImage")) {
        view.updateImageLabel(images.get("originalImage"), images.get("originalImage").createHistogram());
      }
      return;
    }
    if (!(command.equals("Load")) && !(images.containsKey("originalImage"))) {
      view.showDialog("Please load an image first");
      return;
    }
    switch (command) {
      case "Apply":
        String selectedOption = (String) view.getComboBox().getSelectedItem();
        if ("red-component".equals(selectedOption)) {
          images.put("newImage", images.get("originalImage").redComponent());
          img = img.redComponent();
//          view.updateImageLabel(img, img.createHistogram());

        } else if ("green-component".equals(selectedOption)) {
          images.put("newImage", images.get("originalImage").greenComponent());
          img = img.greenComponent();
//          view.updateImageLabel(img, img.createHistogram());


        } else if ("blue-component".equals(selectedOption)) {
          images.put("newImage", images.get("originalImage").blueComponent());
          img = img.blueComponent();


        } else if ("flip-vertical".equals(selectedOption)) {
          images.put("newImage", images.get("originalImage").flipVertical());
          img = img.flipVertical();


        } else if ("flip-horizontal".equals(selectedOption)) {
          images.put("newImage", images.get("originalImage").flipHorizontal());
          img = img.flipHorizontal();


        } else if ("blur".equals(selectedOption)) {
          images.put("newImage", images.get("originalImage").blur());
          img = img.blur();


        } else if ("sharpen".equals(selectedOption)) {
          images.put("newImage", images.get("originalImage").sharpen());

        } else if ("sepia".equals(selectedOption)) {
          images.put("newImage", images.get("originalImage").sepia());
          img = img.sepia();

          // Add your code here for Option 8
        } else if ("greyscale".equals(selectedOption)) {
          images.put("newImage", images.get("originalImage").luma());
          img = img.luma();

          System.out.println("greyscale");

        } else if ("color-correct".equals(selectedOption)) {
          images.put("newImage", images.get("originalImage").colorCorrect());
          img = img.colorCorrect();

          System.out.println("color-correct");

        }
        if (handleSplit()) break;
        view.updateImageLabel(images.get("newImage"), images.get("newImage").createHistogram());
        break;
      case "Load":
        if (images.containsKey("originalImage") && !isSaved) {
          if (view.saveOption() == 0) {
            saveImage();
            isSaved = true;
            break;
          }
        }
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
          isSaved = false;
        }

        // Load logic
        break;

//      case "Enable Split Mode":
//        splitPerc = Integer.parseInt(view.getSplit().getText());
//        split = !split;
      case "Adjust level":
        try {
          int b = Integer.parseInt(view.bInput().getText());
          int m = Integer.parseInt(view.mInput().getText());
          int w = Integer.parseInt(view.wInput().getText());

          if (b < 0 || m < 0 || w < 0 || b > 255 || m > 255 || w > 255) {
            view.showDialog("b, m, w values should be in the range 0 - 255");
            break;
          }
          if (!(b < m && b < w && m < w)) {
            view.showDialog("b, m, w values should be in ascending order");
            break;
          }
          images.put("newImage", images.get("originalImage").adjustLevels(b, m, w));
          if (handleSplit()) break;
          view.updateImageLabel(images.get("newImage"), images.get("newImage").createHistogram());
          break;
        } catch (Exception adlevel) {
          view.showDialog("Please enter valid b, m, w values");
          break;
        }

      case "Set split percentage":
        if (!(view.getSplit().getText().equals(""))) {
          try {
            splitPerc = Integer.parseInt(view.getSplit().getText());
          } catch (NumberFormatException ex) {
            view.showDialog("Please enter a valid integer.");
          }
        } else {
          splitPerc = 50;
        }

        if (splitPerc >= 1 && splitPerc <= 100) {
          images.put("splitImage", images.get("newImage").applySplit(images.get("originalImage"), splitPerc));
          view.updateImageLabel(images.get("splitImage"), images.get("splitImage").createHistogram());
          break;
        } else {
          view.showDialog("Please enter a number between 1 and 100.");
          break;
        }

      case "Save":
        saveImage();
        isSaved = true;
        break;

      case "Save transformation":
//        if (!(images.containsKey("originalImage"))) {
//          view.showDialog("Please load an Image first");
//        }
        images.put("originalImage", images.get("newImage"));
        view.updateImageLabel(images.get("originalImage"),
                images.get("originalImage").createHistogram());
        split = !split;
        view.toggleSet(split);
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
            images.put("newImage", images.get("originalImage").compress(enteredNumber));
            if (handleSplit()) break;
            view.updateImageLabel(images.get("newImage"), images.get("newImage").createHistogram());
            break;
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

  private boolean handleSplit() {
    if (split) {
      if (!(view.getSplit().getText().equals(""))) {
        try {
          splitPerc = Integer.parseInt(view.getSplit().getText());
        } catch (NumberFormatException ex) {
          view.showDialog("Please enter a valid integer.");
        }
      } else {
        splitPerc = 50;
      }
      if (splitPerc >= 1 && splitPerc <= 100) {
        images.put("splitImage", images.get("newImage").applySplit(images.get("originalImage"), splitPerc));
        view.updateImageLabel(images.get("splitImage"), images.get("splitImage").createHistogram());
        return true;
      } else {
        view.showDialog("Please enter a number between 1 and 100.");
        return true;
      }

    } else {
      images.put("originalImage", images.get("newImage"));
    }
    return false;
  }

  protected Image processLoadImage(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    int[][] pixelMatrix = new int[height][width];
    int[][] redPixelMatrix = new int[height][width];
    int[][] greenPixelMatrix = new int[height][width];
    int[][] bluePixelMatrix = new int[height][width];

    for (int x = 0; x < height; x++) {
      for (int y = 0; y < width; y++) {
        pixelMatrix[x][y] = image.getRGB(y, x);
        redPixelMatrix[x][y] = (pixelMatrix[x][y] & 0xff0000) >> 16;
        greenPixelMatrix[x][y] = (pixelMatrix[x][y] & 0xff00) >> 8;
        bluePixelMatrix[x][y] = pixelMatrix[x][y] & 0xff;
      }
    }
    return imageCreator.createModelImpl(redPixelMatrix, greenPixelMatrix, bluePixelMatrix);
  }

  private void saveImage() {
    // Create a file chooser
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Pick a Directory to Save Image");
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    // Show the file chooser dialog
    int userSelection = fileChooser.showSaveDialog((Component) view);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
      // Get the selected directory
      File selectedDirectory = fileChooser.getSelectedFile();

      // Ask the user for the image name
      String imageName = JOptionPane.showInputDialog("Enter image name with extension");
      convertToBufferedImage(imageName, selectedDirectory);

    }
  }

  private void convertToBufferedImage(String imageName, File selectedDirectory) {
    BufferedImage imageSave = null;
    try {
      int lastDotIndex = imageName.lastIndexOf('.');
      String format = imageName.substring(lastDotIndex + 1);
      if (supportedFormats.contains(format)) {
        Image i = images.get("originalImage");
        if (i == null) {
          return;
        }

        if (format.equals("ppm")) {
          try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedDirectory.getCanonicalPath() + imageName))) {
            int width = i.getWidth();
            int height = i.getHeight();

            writer.write("P3\n");
            writer.write(height + " " + width + "\n");
            writer.write(255 + "\n");

            for (int x = 0; x < width; x++) {
              for (int y = 0; y < height; y++) {
                int red = i.getRedPixelMatrixElement(x, y);
                int green = i.getGreenPixelMatrixElement(x, y);
                int blue = i.getBluePixelMatrixElement(x, y);
                writer.write(red + " " + green + " " + blue + " ");
              }
              writer.write("\n");
            }
          } catch (IOException e) {
            view.showDialog("Invalid path.");
          }
        } else {
          int width = i.getWidth();
          int height = i.getHeight();
          int[][] pixelMatrix = new int[width][height];
          for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
              pixelMatrix[x][y] = (i.getRedPixelMatrixElement(x, y) << 16)
                      | (i.getGreenPixelMatrixElement(x, y) << 8)
                      | i.getBluePixelMatrixElement(x, y);
            }
          }
          imageSave = new BufferedImage(pixelMatrix[0].length, pixelMatrix.length, 5);
          for (int x = 0; x < pixelMatrix.length; x++) {
            for (int y = 0; y < pixelMatrix[0].length; y++) {
              imageSave.setRGB(y, x, pixelMatrix[x][y]);
            }
          }
        }
        try {
          File outputFile = new File(selectedDirectory, imageName);
          ImageIO.write(imageSave, format, outputFile);
          view.showDialog("Image Saved Successfully!");
        } catch (Exception e) {
          view.showDialog("Invalid destination folder.");
        }
      } else {
        view.showDialog("Invalid Image format.");
      }
    } catch (Exception e) {
      view.showDialog("Cannot Save the image.");
    }
  }
}
