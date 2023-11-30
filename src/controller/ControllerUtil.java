package controller;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;

import javax.imageio.ImageIO;

import model.Image;

public class ControllerUtil implements ControllerUtilInterface {

  private HashSet<String> supportedFormats;

  private Map<String, Image> images;

  public ControllerUtil() {
    supportedFormats = new HashSet<>();
    supportedFormats.add("jpg");
    supportedFormats.add("png");
    supportedFormats.add("ppm");
  }
  @Override
  public Image processLoadImage(BufferedImage image) {


  }

  @Override
  public String saveImage(String path, String imageName) {
    String status;
    try {
      int lastDotIndex = path.lastIndexOf('.');
      String format = path.substring(lastDotIndex + 1);
      if (supportedFormats.contains(format)) {
        Image i = images.get(imageName);
        if (i == null) {
          status = "Image does not exist";
          //view.imageDoesNotExists();
          // yaha pe yeh view vala part hi nahi ayega aisa bol raha hai?
          return status;
        }

        if (format.equals("ppm")) {
          try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
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
            view.showOutput("Invalid path.");
          }
        }
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
        BufferedImage imageSave = new BufferedImage(pixelMatrix[0].length, pixelMatrix.length, 5);
        for (int x = 0; x < pixelMatrix.length; x++) {
          for (int y = 0; y < pixelMatrix[0].length; y++) {
            imageSave.setRGB(y, x, pixelMatrix[x][y]);
          }
        }

        try {
          File outputImageFileRed = new File(path);
          ImageIO.write(imageSave, format, outputImageFileRed);
          view.showOutput("Save: Success");
        } catch (Exception e) {
          view.showOutput("Invalid destination.");
        }

      } else {
        view.showOutput("Invalid format.");
      }
    } catch (Exception e) {
      view.showCommandList();
    }
  }

  }
}
