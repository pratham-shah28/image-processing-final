package controller;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.Image;
import model.ImageCreator;

/**
 * This is a controller util class which can be used by all the controllers to load and save image.
 */
public class ControllerUtil implements ControllerUtilInterface {

  private HashSet<String> supportedFormats;

  /**
   * Constructs the ControllerUtil class and initializes all the supported image formats.
   */
  public ControllerUtil() {
    supportedFormats = new HashSet<>();
    supportedFormats.add("jpg");
    supportedFormats.add("png");
    supportedFormats.add("ppm");
  }

  @Override
  public String saveImage(String path, Image img) {
    String status = "Save aborted";
    try {
      int lastDotIndex = path.lastIndexOf('.');
      String format = path.substring(lastDotIndex + 1);
      if (supportedFormats.contains(format)) {
        if (img == null) {
          status = "Image does not exist";
        }
        if (format.equals("ppm")) {
          try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            int width = img.getWidth();
            int height = img.getHeight();

            writer.write("P3\n");
            writer.write(height + " " + width + "\n");
            writer.write(255 + "\n");

            for (int x = 0; x < width; x++) {
              for (int y = 0; y < height; y++) {
                int red = img.getRedPixelMatrixElement(x, y);
                int green = img.getGreenPixelMatrixElement(x, y);
                int blue = img.getBluePixelMatrixElement(x, y);
                writer.write(red + " " + green + " " + blue + " ");
              }
              writer.write("\n");
            }
            status = "Image Saved Successfully!";
          } catch (IOException e) {
            status = "Invalid path.";
          }
        } else {
          int width = img.getWidth();
          int height = img.getHeight();
          int[][] pixelMatrix = new int[width][height];
          for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
              pixelMatrix[x][y] = (img.getRedPixelMatrixElement(x, y) << 16)
                      | (img.getGreenPixelMatrixElement(x, y) << 8)
                      | img.getBluePixelMatrixElement(x, y);
            }
          }
          BufferedImage imageSave = new BufferedImage(pixelMatrix[0].length, pixelMatrix.length, 5);
          for (int x = 0; x < pixelMatrix.length; x++) {
            for (int y = 0; y < pixelMatrix[0].length; y++) {
              imageSave.setRGB(y, x, pixelMatrix[x][y]);
            }
          }
          try {
            File outputImageFile = new File(path);
            ImageIO.write(imageSave, format, outputImageFile);
            status = "Image Saved Successfully!";
          } catch (Exception e) {
            status = "Invalid destination folder.";
          }
        }
      } else {
        status = "Invalid Image format.";
      }
    } catch (Exception e) {
      status = "Error: Save Unsuccessful";
    }
    return status;
  }

  @Override
  public String loadImage(String path, String name, Map<String, Image> images, ImageCreator creator) {
    String status = "Load aborted";
    if (name != null && path != null) {
      try {
        int lastDotIndex = path.lastIndexOf('.');
        String format = path.substring(lastDotIndex + 1);
        if (format.equals("ppm")) {
          status = loadImagePPM(path, name, images, creator);
        } else {
          status = processLoadImage(path, name, images, creator);
        }
      } catch (Exception e) {
        status = "Load: Failed: " + e;
      }
    }
    return status;
  }

  private String processLoadImage(String path, String imageName, Map<String, Image> images, ImageCreator creator) throws IOException {
    String status = "";
    try {
      File imageFile = new File(path);
      BufferedImage image = ImageIO.read(imageFile);
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
      Image img = creator.createModelImpl(redPixelMatrix, greenPixelMatrix, bluePixelMatrix);
      images.put(imageName, img);
      status = "Load Successful.";
    } catch (FileNotFoundException e) {
      status = "File " + path + " not found!";
    }
    return status;
  }

  private String loadImagePPM(String path, String name, Map<String, Image> images, ImageCreator creator) {
    String status = "";
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(path));
    } catch (FileNotFoundException e) {
      status = "File " + path + " not found!";
      return status;
    }
    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }
    sc = new Scanner(builder.toString());
    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      status = "Invalid PPM file: plain RAW file should begin with P3";
      return status;
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    status = "Maximum value of a color in this file (usually 255): " + maxValue;
    int[][] newRedPixelMatrix = new int[height][width];
    int[][] newGreenPixelMatrix = new int[height][width];
    int[][] newBluePixelMatrix = new int[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        newRedPixelMatrix[i][j] = r;
        newGreenPixelMatrix[i][j] = g;
        newBluePixelMatrix[i][j] = b;
      }
    }
    Image imageProcessor =
            creator.createModelImpl(newRedPixelMatrix,
                    newGreenPixelMatrix,
                    newBluePixelMatrix);
    images.put(name, imageProcessor);
    status = "Load Successful.";
    return status;
  }
}
