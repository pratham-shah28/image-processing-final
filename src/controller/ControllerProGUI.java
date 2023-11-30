package controller;

import java.awt.*;
import java.io.FileWriter;
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


/**
 * This class is an extended version of original Controller for additional functionalities such as
 * compression, histogram, color correction, level adjustment.
 * It retains support for all operations performed by original Controller.
 */
public class ControllerProGUI implements Features {

  protected ViewGUIInterface view;

  private Map<String, Image> images;

  private Image img;

  private HashSet<String> supportedFormats;

  private ImageCreator imageCreator;

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
    this.view = view;
    this.images = images;
    split = false;
    this.imageCreator = imageCreator;
    supportedFormats = new HashSet<>();
    supportedFormats.add("jpg");
    supportedFormats.add("png");
    supportedFormats.add("ppm");
  }

  public void setView(){
    //provide view with all the callbacks
    view.addFeatures(this);
  }

  @Override
  public void loadImage() {
    if (images.containsKey("originalImage") && !isSaved) {
      if (view.saveOption() == 0) {
        saveImage();
        isSaved = true;
      }
    }
    File selectedFile = view.loadSelectedImage();
    if (selectedFile != null) {
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
  }

  @Override
  public void saveImage() {
    if (!checkImageExists()) {
      return;
    }
    File selectedDirectory = view.selectedDirectory();
    if (selectedDirectory != null) {
      String imageName = view.getImageName();
      convertToBufferedImage(imageName, selectedDirectory);
    }
  }

  @Override
  public void apply() {
    if (!checkImageExists()) {
      return;
    }
    String selectedOption = view.getComboBoxSelectedItem();
    if ("red-component".equals(selectedOption)) {
      images.put("newImage", images.get("originalImage").redComponent());
    } else if ("green-component".equals(selectedOption)) {
      images.put("newImage", images.get("originalImage").greenComponent());
    } else if ("blue-component".equals(selectedOption)) {
      images.put("newImage", images.get("originalImage").blueComponent());
    } else if ("flip-vertical".equals(selectedOption)) {
      images.put("newImage", images.get("originalImage").flipVertical());
    } else if ("flip-horizontal".equals(selectedOption)) {
      images.put("newImage", images.get("originalImage").flipHorizontal());
    } else if ("blur".equals(selectedOption)) {
      images.put("newImage", images.get("originalImage").blur());
    } else if ("sharpen".equals(selectedOption)) {
      images.put("newImage", images.get("originalImage").sharpen());
    } else if ("sepia".equals(selectedOption)) {
      images.put("newImage", images.get("originalImage").sepia());
    } else if ("greyscale".equals(selectedOption)) {
      images.put("newImage", images.get("originalImage").luma());
    } else if ("color-correct".equals(selectedOption)) {
      images.put("newImage", images.get("originalImage").colorCorrect());
    }
    if (handleSplit()) return;
    view.updateImageLabel(images.get("newImage"), images.get("newImage").createHistogram());
  }

  @Override
  public void adjustLevel() {
    if (!checkImageExists()) {
      return;
    }
    try {
      int b = Integer.parseInt(view.bInput());
      int m = Integer.parseInt(view.mInput());
      int w = Integer.parseInt(view.wInput());
      if (b < 0 || m < 0 || w < 0 || b > 255 || m > 255 || w > 255) {
        view.showDialog("b, m, w values should be in the range 0 - 255");
        return;
      }
      if (!(b < m && b < w && m < w)) {
        view.showDialog("b, m, w values should be in ascending order");
        return;
      }
      images.put("newImage", images.get("originalImage").adjustLevels(b, m, w));
      if (handleSplit()) return;
      view.updateImageLabel(images.get("newImage"), images.get("newImage").createHistogram());
    } catch (Exception adlevel) {
      view.showDialog("Please enter valid b, m, w values");
    }
  }

  @Override
  public void setSplitPercentage() {
    if (!checkImageExists()) {
      return;
    }
    if (!(view.getSplit().equals(""))) {
      try {
        splitPerc = Integer.parseInt(view.getSplit());
      } catch (NumberFormatException ex) {
        view.showDialog("Please enter a valid integer.");
      }
    } else {
      splitPerc = 50;
    }

    if (splitPerc >= 1 && splitPerc <= 100) {
      images.put("splitImage", images.get("newImage").applySplit(images.get("originalImage"), splitPerc));
      view.updateImageLabel(images.get("splitImage"), images.get("splitImage").createHistogram());
    } else {
      view.showDialog("Please enter a number between 1 and 100.");
    }
  }

  @Override
  public void saveTransformation() {
    if (!checkImageExists()) {
      return;
    }
    images.put("originalImage", images.get("newImage"));
    view.updateImageLabel(images.get("originalImage"),
            images.get("originalImage").createHistogram());
    split = !split;
    view.toggleSet(split);
  }

  @Override
  public void applyCompress() {
    if (!checkImageExists()) {
      return;
    }
    int userInput = view.getCompressInput();
    if (userInput >= 1 && userInput <= 100) {
        // Perform an action based on the entered number
        img = img.compress(userInput);
        images.put("newImage", images.get("originalImage").compress(userInput));
        if (handleSplit()) return;
        view.updateImageLabel(images.get("newImage"), images.get("newImage").createHistogram());
      } else {
        // Display an error message for an invalid range
        view.showDialog("Please enter a number between 1 and 100.");
      }
  }

  @Override
  public void splitMode(boolean isEnabled) {
    if (!checkImageExists()) {
      return;
    }
    split = !split;
    view.toggleSet(split);
    if (isEnabled) {
      if (images.containsKey("newImage")) {
        images.put("splitImage", images.get("newImage").applySplit(images.get("originalImage"), 50));
        view.updateImageLabel(images.get("splitImage"), images.get("splitImage").createHistogram());
      } else {
        if (images.containsKey("originalImage")) {
          view.updateImageLabel(images.get("originalImage"), images.get("originalImage").createHistogram());
        }
      }
    }
  }

  private boolean checkImageExists() {
    if (!(images.containsKey("originalImage"))) {
      view.showDialog("Please load an image first");
      return false;
    } else {
      return true;
    }
  }
  private boolean handleSplit() {
    if (split) {
      if (!(view.getSplit().equals(""))) {
        try {
          splitPerc = Integer.parseInt(view.getSplit());
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
