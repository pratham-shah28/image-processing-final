package model;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import static java.lang.Math.sqrt;

/**
 * This class represents an Image with its properties and operations.
 * An image has a width, height and is represented as 3 channels i.e. red, green and blue.
 * This class implements the Image interface.
 */
public class ImageImpl implements Image {

  private final int[][] redChannel;
  private final int[][] greenChannel;
  private final int[][] blueChannel;
  private final int width;
  private final int height;

  /**
   * Constructs an Image by initializing its width, height
   * and storing the respective channels data as matrices.
   *
   * @param redChannel   Matrix for the red channel of the image.
   * @param greenChannel Matrix for the green channel of the image.
   * @param blueChannel  Matrix for the blue channel of the image.
   */
  public ImageImpl(int[][] redChannel, int[][] greenChannel, int[][] blueChannel) {
    this.redChannel = redChannel;
    this.greenChannel = greenChannel;
    this.blueChannel = blueChannel;
    this.width = redChannel.length;
    this.height = redChannel[0].length;
  }

  @Override
  public Image value() {
    TriFunction<Integer, Integer, Integer, int[]> valueOperation = (red, green, blue) -> {
      int max = Math.max(red, Math.max(green, blue));
      return new int[]{max, max, max};
    };
    return applyOperation(valueOperation);
  }


  @Override
  public Image luma() {
    TriFunction<Integer, Integer, Integer, int[]> lumaOperation = (red, green, blue) -> {
      int luma = (int) ((0.2126 * red) + (0.7152 * green) + (0.0722 * blue));
      luma = clip(luma);
      return new int[]{luma, luma, luma};
    };
    return applyOperation(lumaOperation);
  }


  @Override
  public Image intensity() {
    TriFunction<Integer, Integer, Integer, int[]> intensityOperation = (red, green, blue) -> {
      int avg = Math.round((red + green + blue) / 3);
      return new int[]{avg, avg, avg};
    };
    return applyOperation(intensityOperation);
  }


  @Override
  public Image redComponent() {
    TriFunction<Integer, Integer, Integer, int[]> redOperation = (red, green, blue) -> {
      int[] result = {red, 0, 0};
      return result;
    };
    return applyOperation(redOperation);
  }

  @Override
  public Image greenComponent() {
    TriFunction<Integer, Integer, Integer, int[]> greenOperation = (red, green, blue) -> {
      int[] result = {0, green, 0};
      return result;
    };
    return applyOperation(greenOperation);
  }


  @Override
  public Image blueComponent() {
    TriFunction<Integer, Integer, Integer, int[]> blueOperation = (red, green, blue) -> {
      int[] result = {0, 0, blue};
      return result;
    };
    return applyOperation(blueOperation);
  }

  @Override
  public Image sepia() {
    TriFunction<Integer, Integer, Integer, int[]> sepiaOperation = (red, green, blue) -> {
      int sepia_red = (int) ((0.393 * red) + (0.769 * green) + (0.189 * blue));
      int sepia_green = (int) ((0.349 * red) + (0.686 * green) + (0.168 * blue));
      int sepia_blue = (int) ((0.272 * red) + (0.534 * green) + (0.131 * blue));

      sepia_red = clip(sepia_red);
      sepia_green = clip(sepia_green);
      sepia_blue = clip(sepia_blue);
      int[] result = {sepia_red, sepia_green, sepia_blue};
      return result;
    };
    return applyOperation(sepiaOperation);
  }


  @Override
  public Image brighten(int factor) {
    TriFunction<Integer, Integer, Integer, int[]> brightenOperation = (red, green, blue) -> {
      red += factor;
      green += factor;
      blue += factor;

      red = clip(red);
      green = clip(green);
      blue = clip(blue);
      return new int[]{red, green, blue};
    };
    return applyOperation(brightenOperation);
  }

  @Override
  public Image combine(Image redImage, Image greenImage, Image blueImage) {
    if ((redImage.getWidth() == greenImage.getWidth()
            && redImage.getWidth() == blueImage.getWidth())
            && (redImage.getHeight() == greenImage.getHeight()
            && redImage.getHeight() == blueImage.getHeight())) {
      int[][] newRedPixelMatrix = new int[redImage.getWidth()][redImage.getHeight()];
      int[][] newGreenPixelMatrix = new int[greenImage.getWidth()][greenImage.getHeight()];
      int[][] newBluePixelMatrix = new int[blueImage.getWidth()][blueImage.getHeight()];
      for (int x = 0; x < redImage.getWidth(); x++) {
        for (int y = 0; y < redImage.getHeight(); y++) {
          int red = redImage.getRedPixelMatrixElement(x, y);
          int green = greenImage.getGreenPixelMatrixElement(x, y);
          int blue = blueImage.getBluePixelMatrixElement(x, y);
          newRedPixelMatrix[x][y] = red;
          newGreenPixelMatrix[x][y] = green;
          newBluePixelMatrix[x][y] = blue;
        }
      }
      return new ImageImpl(newRedPixelMatrix, newGreenPixelMatrix, newBluePixelMatrix);
    } else {
      throw new IllegalArgumentException("Images should be of the same size");
    }
  }


  @Override
  public Image flipHorizontal() {
    int[][] newRedPixelMatrix = new int[this.width][this.height];
    int[][] newGreenPixelMatrix = new int[this.width][this.height];
    int[][] newBluePixelMatrix = new int[this.width][this.height];
    int width = this.width;
    int height = this.height;
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height / 2; y++) {
        // Swap the elements to flip the matrix horizontally
        newRedPixelMatrix[x][y] = this.getRedPixelMatrixElement(x, (height - 1 - y));
        newRedPixelMatrix[x][height - 1 - y] = this.getRedPixelMatrixElement(x, y);
        newGreenPixelMatrix[x][y] = this.getGreenPixelMatrixElement(x, (height - 1 - y));
        newGreenPixelMatrix[x][height - 1 - y] = this.getGreenPixelMatrixElement(x, y);
        newBluePixelMatrix[x][y] = this.getBluePixelMatrixElement(x, (height - 1 - y));
        newBluePixelMatrix[x][height - 1 - y] = this.getBluePixelMatrixElement(x, y);
      }
    }
    return new ImageImpl(newRedPixelMatrix, newGreenPixelMatrix, newBluePixelMatrix);
  }

  @Override
  public Image flipVertical() {
    int[][] newRedPixelMatrix = new int[this.width][this.height];
    int[][] newGreenPixelMatrix = new int[this.width][this.height];
    int[][] newBluePixelMatrix = new int[this.width][this.height];
    int width = this.width;
    int height = this.height;
    for (int x = 0; x < width / 2; x++) {
      for (int y = 0; y < height; y++) {
        newRedPixelMatrix[x][y] = this.getRedPixelMatrixElement((width - 1 - x), y);
        newRedPixelMatrix[width - 1 - x][y] = this.getRedPixelMatrixElement(x, y);
        newGreenPixelMatrix[x][y] = this.getGreenPixelMatrixElement((width - 1 - x), y);
        newGreenPixelMatrix[width - 1 - x][y] = this.getGreenPixelMatrixElement(x, y);
        newBluePixelMatrix[x][y] = this.getBluePixelMatrixElement((width - 1 - x), y);
        newBluePixelMatrix[width - 1 - x][y] = this.getBluePixelMatrixElement(x, y);
      }
    }
    return new ImageImpl(newRedPixelMatrix, newGreenPixelMatrix, newBluePixelMatrix);
  }


  @Override
  public Image blur() {
    double[][] kernel = {{0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125}, {0.0625, 0.125, 0.0625}};
    return kernelOperation(this, kernel);
  }


  @Override
  public Image sharpen() {
    double[][] kernel =
        {{-0.125, -0.125, -0.125, -0.125, -0.125}, {-0.125, 0.25, 0.25, 0.25, -0.125},
         {-0.125, 0.25, 1, 0.25, -0.125}, {-0.125, 0.25, 0.25, 0.25, -0.125},
         {-0.125, -0.125, -0.125, -0.125, -0.125}};
    return kernelOperation(this, kernel);
  }


  private Image kernelOperation(Image img, double[][] kernel) {
    int[][] newRedPixelMatrix = new int[img.getWidth()][img.getHeight()];
    int[][] newGreenPixelMatrix = new int[img.getWidth()][img.getHeight()];
    int[][] newBluePixelMatrix = new int[img.getWidth()][img.getHeight()];
    int width = img.getWidth();
    int height = img.getHeight();
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        double valueRed = 0;
        double valueGreen = 0;
        double valueBlue = 0;
        int kernelCenter = kernel.length / 2;

        for (int i = 0; i < kernel.length; i++) {
          for (int j = 0; j < kernel.length; j++) {
            int image_x = x - kernelCenter + i;
            int image_y = y - kernelCenter + j;

            if (image_x >= 0 && image_x < width && image_y >= 0 && image_y < height) {
              valueRed += img.getRedPixelMatrixElement(image_x, image_y) * kernel[i][j];
              valueGreen += img.getGreenPixelMatrixElement(image_x, image_y) * kernel[i][j];
              valueBlue += img.getBluePixelMatrixElement(image_x, image_y) * kernel[i][j];
            }
          }
        }

        valueRed = clip(valueRed);
        valueGreen = clip(valueGreen);
        valueBlue = clip(valueBlue);

        newRedPixelMatrix[x][y] = (int) valueRed;
        newGreenPixelMatrix[x][y] = (int) valueGreen;
        newBluePixelMatrix[x][y] = (int) valueBlue;
      }
    }
    return new ImageImpl(newRedPixelMatrix, newGreenPixelMatrix, newBluePixelMatrix);
  }

  private int clip(double value) {
    if (value > 255) {
      value = 255;
    }
    if (value < 0) {
      value = 0;
    }
    return (int) (value);
  }

  private double[][] padMatrix(int[][] pixelMatrix) {

    int rows = pixelMatrix.length;
    int cols = pixelMatrix[0].length;

    int target = 1;
    while (target < rows || target < cols) {
      target *= 2;
    }

    double[][] squareMatrix = new double[target][target];

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        squareMatrix[i][j] = pixelMatrix[i][j];
      }
    }
    return squareMatrix;
  }


  private double[] transform(double[] sequence) {
    double[] avg = new double[sequence.length / 2];
    double[] diff = new double[sequence.length / 2];
    double[] result = new double[avg.length + diff.length];

    for (int i = 0; i < sequence.length; i += 2) {
      double av = (sequence[i] + sequence[i + 1]) / sqrt(2);
      double di = (sequence[i] - sequence[i + 1]) / sqrt(2);
      avg[i / 2] = av;
      diff[i / 2] = di;
    }
    System.arraycopy(avg, 0, result, 0, avg.length);
    System.arraycopy(diff, 0, result, avg.length, diff.length);
    return result;
  }

  private double[] inverse(double[] sequence) {
    double[] avg = new double[sequence.length / 2];
    double[] diff = new double[sequence.length / 2];
    double[] result = new double[sequence.length];

    for (int i = 0, j = sequence.length / 2; i < sequence.length / 2
            && j < sequence.length; i += 1, j += 1) {
      double av = (sequence[i] + sequence[j]) / sqrt(2);
      double di = (sequence[i] - sequence[j]) / sqrt(2);
      avg[i] = av;
      diff[i] = di;
    }
    int i = 0;
    int j = 0;
    int k = 0;
    while (i < avg.length && j < diff.length) {
      result[k++] = avg[i++];
      result[k++] = diff[j++];
    }
    return result;
  }


  /**
   * Interface for the Trifunction method.
   *
   * @param <T> Generic type argument.
   * @param <U> Generic type argument.
   * @param <V> Generic type argument.
   * @param <R> Generic type return.
   */
  @FunctionalInterface
  public interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
  }

  private Image applyOperation(TriFunction<Integer, Integer, Integer, int[]> operation) {
    int[][] newRedPixelMatrix = new int[this.width][this.height];
    int[][] newGreenPixelMatrix = new int[this.width][this.height];
    int[][] newBluePixelMatrix = new int[this.width][this.height];

    for (int x = 0; x < this.width; x++) {
      for (int y = 0; y < this.height; y++) {
        int red = this.getRedPixelMatrixElement(x, y);
        int green = this.getGreenPixelMatrixElement(x, y);
        int blue = this.getBluePixelMatrixElement(x, y);

        int[] result = operation.apply(red, green, blue);

        newRedPixelMatrix[x][y] = result[0];
        newGreenPixelMatrix[x][y] = result[1];
        newBluePixelMatrix[x][y] = result[2];
      }
    }
    return new ImageImpl(newRedPixelMatrix, newGreenPixelMatrix, newBluePixelMatrix);
  }


  @Override
  public int getRedPixelMatrixElement(int x, int y) {
    return this.redChannel[x][y];
  }

  @Override
  public int getGreenPixelMatrixElement(int x, int y) {
    return this.greenChannel[x][y];
  }

  @Override
  public int getBluePixelMatrixElement(int x, int y) {
    return this.blueChannel[x][y];
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public Image applySplit(Image originalImage, double perc) {
    int heightSplit = (int) Math.round(this.getHeight() * perc / 100);
    for (int x = 0; x < this.width; x++) {
      for (int y = heightSplit; y < this.height; y++) {
        if (y == heightSplit) {
          if (x % 2 == 0) {
            this.redChannel[x][y] = 255;
            this.greenChannel[x][y] = 0;
            this.blueChannel[x][y] = 0;
          } else {
            this.redChannel[x][y] = this.getRedPixelMatrixElement(x, y);
            this.greenChannel[x][y] = this.getGreenPixelMatrixElement(x, y);
            this.blueChannel[x][y] = this.getBluePixelMatrixElement(x, y);
          }
        } else {
          this.redChannel[x][y] = originalImage.getRedPixelMatrixElement(x, y);
          this.greenChannel[x][y] = originalImage.getGreenPixelMatrixElement(x, y);
          this.blueChannel[x][y] = originalImage.getBluePixelMatrixElement(x, y);
        }
      }
    }
    return new ImageImpl(this.redChannel, this.greenChannel, this.blueChannel);
  }

  private double[][] haar(int[][] sequence) {
    double[][] paddedSequence = padMatrix(sequence);
    int len = paddedSequence.length;
    while (len > 1) {
      for (int i = 0; i < len; i++) {
        double[] tempArray = Arrays.copyOf(paddedSequence[i], len);
        tempArray = transform(tempArray);
        System.arraycopy(tempArray, 0, paddedSequence[i], 0, len);

      }
      for (int j = 0; j < len; j++) {
        double[] tempColArray = new double[len];
        for (int k = 0; k < len; k++) {
          tempColArray[k] = paddedSequence[k][j];
        }

        tempColArray = transform(tempColArray);

        for (int l = 0; l < len; l++) {
          paddedSequence[l][j] = tempColArray[l];
        }
      }
      len = len / 2;
    }
    return paddedSequence;
  }

  private double[][] invharr(double[][] paddedSequence) {
    int len = 2;
    while (len <= paddedSequence.length) {
      for (int j = 0; j < len; j++) {
        double[] tempColArray = new double[len];
        for (int k = 0; k < len; k++) {
          tempColArray[k] = paddedSequence[k][j];
        }
        tempColArray = inverse(tempColArray);
        for (int l = 0; l < len; l++) {
          paddedSequence[l][j] = tempColArray[l];
        }
      }
      for (int i = 0; i < len; i++) {
        double[] tempArray = Arrays.copyOf(paddedSequence[i], len);
        tempArray = inverse(tempArray);
        System.arraycopy(tempArray, 0, paddedSequence[i], 0, len);
      }
      len = len * 2;
    }
    return paddedSequence;
  }


  @Override
  public Image compress(double factor) {

    double[][] transformedRedPixelMatrix = new double[this.getWidth()][this.getHeight()];
    double[][] transformedGreenPixelMatrix = new double[this.getWidth()][this.getHeight()];
    double[][] transformedBluePixelMatrix = new double[this.getWidth()][this.getHeight()];

    transformedRedPixelMatrix = haar(this.redChannel);
    transformedGreenPixelMatrix = haar(this.greenChannel);
    transformedBluePixelMatrix = haar(this.blueChannel);
    Map<String, double[][]> matrices =
            applyThreshold3(transformedRedPixelMatrix,
                    transformedGreenPixelMatrix,
                    transformedBluePixelMatrix, factor);
    transformedRedPixelMatrix = invharr(matrices.get("red"));
    transformedGreenPixelMatrix = invharr(matrices.get("green"));
    transformedBluePixelMatrix = invharr(matrices.get("blue"));

    int[][] redPixelMatrix = new int[this.getWidth()][this.getHeight()];
    int[][] greenPixelMatrix = new int[this.getWidth()][this.getHeight()];
    int[][] bluePixelMatrix = new int[this.getWidth()][this.getHeight()];


    for (int i = 0; i < redPixelMatrix.length; i++) {
      for (int j = 0; j < redPixelMatrix[0].length; j++) {
        redPixelMatrix[i][j] = clip(Math.round(transformedRedPixelMatrix[i][j]));
        greenPixelMatrix[i][j] = clip(Math.round(transformedGreenPixelMatrix[i][j]));
        bluePixelMatrix[i][j] = clip(Math.round(transformedBluePixelMatrix[i][j]));
      }
    }
    return new ImageImpl(redPixelMatrix, greenPixelMatrix, bluePixelMatrix);
  }


  private Map<String, double[][]> applyThreshold3(double[][] transformedRed,
                                                  double[][] transformedGreen,
                                                  double[][] transformedBlue,
                                                  double factor) {

    Map<Double, Double> uniqueValues = new HashMap<>();
    Map<String, double[][]> matrices = new HashMap<>();
    for (int i = 0; i < transformedRed.length; i++) {
      for (int j = 0; j < transformedRed[0].length; j++) {
        if (!(uniqueValues.containsKey(Math.abs(transformedRed[i][j])))) {
          uniqueValues.put(Math.abs(transformedRed[i][j]), Math.abs(transformedRed[i][j]));
        }
        if (!(uniqueValues.containsKey(Math.abs(transformedGreen[i][j])))) {
          uniqueValues.put(Math.abs(transformedGreen[i][j]), Math.abs(transformedGreen[i][j]));
        }
        if (!(uniqueValues.containsKey(Math.abs(transformedBlue[i][j])))) {
          uniqueValues.put(Math.abs(transformedBlue[i][j]), Math.abs(transformedBlue[i][j]));
        }
      }
    }
    int n = (int) Math.round((factor * uniqueValues.size()) / 100);
    int count = 0;

    List<Double> values = new ArrayList<>(new TreeSet<>(uniqueValues.keySet()));

    if (n != 0) {
      double threshold = values.get(n - 1);
      for (int i = 0; i < transformedRed.length; i++) {
        for (int j = 0; j < transformedRed[0].length; j++) {
          if (Math.abs(transformedRed[i][j]) <= threshold) {
            transformedRed[i][j] = 0;
            count += 1;
          }
          if (Math.abs(transformedGreen[i][j]) <= threshold) {
            transformedGreen[i][j] = 0;
            count += 1;
          }
          if (Math.abs(transformedBlue[i][j]) <= threshold) {
            transformedBlue[i][j] = 0;
            count += 1;
          }
        }
      }
      matrices.put("red", transformedRed);
      matrices.put("green", transformedGreen);
      matrices.put("blue", transformedBlue);
      return matrices;
    } else {
      matrices.put("red", transformedRed);
      matrices.put("green", transformedGreen);
      matrices.put("blue", transformedBlue);
      return matrices;
    }
  }

  private HashMap<String, int[]> getFrequency() {
    int[] redFrequency = new int[256];
    int[] greenFrequency = new int[256];
    int[] blueFrequency = new int[256];
    HashMap<String, int[]> rgbFrequency = new HashMap<>();

    for (int i = 0; i < 256; i++) {
      redFrequency[i] = 0;
      greenFrequency[i] = 0;
      blueFrequency[i] = 0;
    }

    for (int x = 0; x < this.width; x++) {
      for (int y = 0; y < this.height; y++) {
        int red = this.getRedPixelMatrixElement(x, y);
        int green = this.getGreenPixelMatrixElement(x, y);
        int blue = this.getBluePixelMatrixElement(x, y);
        redFrequency[red]++;
        greenFrequency[green]++;
        blueFrequency[blue]++;
      }
    }

    rgbFrequency.put("red", redFrequency);
    rgbFrequency.put("green", greenFrequency);
    rgbFrequency.put("blue", blueFrequency);

    return rgbFrequency;
  }

  private void plotLine(Graphics2D g2d, int[] frequencies, int width, int height,
                        Color lineColor, int maxFrequency) {
    g2d.setColor(lineColor);
    g2d.setStroke(new BasicStroke(1));

    for (int i = 0; i < frequencies.length - 1; i++) {
      int x1 = i * (width - 1) / (frequencies.length - 1);
      int y1 = height - frequencies[i] * height / maxFrequency;
      int x2 = (i + 1) * (width - 1) / (frequencies.length - 1);
      int y2 = height - frequencies[i + 1] * height / maxFrequency;
      g2d.drawLine(x1, y1, x2, y2);
    }
  }

  private void drawGrid(Graphics2D g2d, int gridSize, int width, int height) {
    g2d.setColor(Color.lightGray);
    g2d.setStroke(new BasicStroke());

    for (int x = 0; x < width; x += gridSize) {
      g2d.drawLine(x, 0, x, height);
    }

    // Draw horizontal lines
    for (int y = 0; y < height; y += gridSize) {
      g2d.drawLine(0, y, width, y);
    }
  }

  @Override
  public Image createHistogram() {
    HashMap<String, int[]> rgbFrequency = getFrequency();
    int width = 256;
    int height = 256;

    BufferedImage graphImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = graphImage.createGraphics();

    g2d.setBackground(Color.WHITE);
    g2d.clearRect(0, 0, width, height);

    // Find max frequency for normalization
    int maxFrequency = Integer.MIN_VALUE;
    for (int frequency : rgbFrequency.get("red")) {
      if (frequency > maxFrequency) {
        maxFrequency = frequency;
      }
    }
    for (int frequency : rgbFrequency.get("green")) {
      if (frequency > maxFrequency) {
        maxFrequency = frequency;
      }
    }
    for (int frequency : rgbFrequency.get("blue")) {
      if (frequency > maxFrequency) {
        maxFrequency = frequency;
      }
    }

    //draw grid

    int gridSize = 13;
    drawGrid(g2d, gridSize, width, height);

    // Plot the line graph for Red Channel (in red)
    plotLine(g2d, rgbFrequency.get("red"), width, height, Color.RED, maxFrequency);

    // Plot the line graph for Green Channel (in green)
    plotLine(g2d, rgbFrequency.get("green"), width, height, Color.GREEN, maxFrequency);

    // Plot the line graph for Blue Channel (in blue)
    plotLine(g2d, rgbFrequency.get("blue"), width, height, Color.BLUE, maxFrequency);

    // Dispose Graphics2D
    g2d.dispose();

    int[][] newRedPixelMatrix = new int[height][width];
    int[][] newGreenPixelMatrix = new int[height][width];
    int[][] newBluePixelMatrix = new int[height][width];

    for (int x = 0; x < height; x++) {
      for (int y = 0; y < width; y++) {
        int pixel = graphImage.getRGB(y, x);
        newRedPixelMatrix[x][y] = (pixel & 0xff0000) >> 16;
        newGreenPixelMatrix[x][y] = (pixel & 0xff00) >> 8;
        newBluePixelMatrix[x][y] = pixel & 0xff;
      }
    }
    return new ImageImpl(newRedPixelMatrix, newGreenPixelMatrix, newBluePixelMatrix);
  }

  private int findPeak(int[] channel) {
    int maxFrequency = Integer.MIN_VALUE;
    int maxIndex = 0;
    for (int i = 10; i < channel.length - 10; i++) {
      if (channel[i] > maxFrequency) {
        maxIndex = i;
        maxFrequency = channel[i];
      }
    }
    return maxIndex;
  }

  @Override
  public Image colorCorrect() {
    HashMap<String, int[]> rgbFrequency = getFrequency();
    int redPeakIndex = findPeak(rgbFrequency.get("red"));
    int greenPeakIndex = findPeak(rgbFrequency.get("green"));
    int bluePeakIndex = findPeak(rgbFrequency.get("blue"));

    int avgPeakPosition = (redPeakIndex + greenPeakIndex + bluePeakIndex) / 3;

    int[][] newRedPixelMatrix = new int[this.width][this.height];
    int[][] newGreenPixelMatrix = new int[this.width][this.height];
    int[][] newBluePixelMatrix = new int[this.width][this.height];

    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        newRedPixelMatrix[i][j] = this.getRedPixelMatrixElement(i, j);
        newGreenPixelMatrix[i][j] = this.getGreenPixelMatrixElement(i, j);
        newBluePixelMatrix[i][j] = this.getBluePixelMatrixElement(i, j);
      }
    }

    shiftChannel(newRedPixelMatrix, redPeakIndex, avgPeakPosition);
    shiftChannel(newGreenPixelMatrix, greenPeakIndex, avgPeakPosition);
    shiftChannel(newBluePixelMatrix, bluePeakIndex, avgPeakPosition);

    return new ImageImpl(newRedPixelMatrix, newGreenPixelMatrix, newBluePixelMatrix);
  }

  private void shiftChannel(int[][] pixelMatrix, int peakIndex, int avgPeakIndex) {
    int difference = avgPeakIndex - peakIndex;
    for (int i = 0; i < pixelMatrix.length; i++) {
      for (int j = 0; j < pixelMatrix[0].length; j++) {
        pixelMatrix[i][j] = pixelMatrix[i][j] + difference;
        pixelMatrix[i][j] = clip(pixelMatrix[i][j]);
      }
    }
  }

  @Override
  public Image adjustLevels(int bP, int mP, int wP) {
    double a1 = (Math.pow(bP, 2)
            * (mP - wP))
            - (bP * (Math.pow(mP, 2)
            - Math.pow(wP, 2)))
            + (wP * Math.pow(mP, 2))
            - (mP * Math.pow(wP, 2));
    double aA = ((-1 * bP) * (128 - 255)) + (128 * wP) - (255 * mP);
    double aB = (Math.pow(bP, 2) * (128 - 255)) + (255 * Math.pow(mP, 2))
            - (128 * Math.pow(wP, 2));
    double aC = (Math.pow(bP, 2) * ((255 * mP) - (128 * wP)))
            - (bP * ((255 * Math.pow(mP, 2)) - (128 * Math.pow(wP, 2))));
    double a = aA / a1;
    double b = aB / a1;
    double c = aC / a1;

    int[][] newRedPixelMatrix = new int[this.width][this.height];
    int[][] newGreenPixelMatrix = new int[this.width][this.height];
    int[][] newBluePixelMatrix = new int[this.width][this.height];

    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        newRedPixelMatrix[i][j] =
                computeAdjustedValues(getRedPixelMatrixElement(i, j), a, b, c);
        newGreenPixelMatrix[i][j] =
                computeAdjustedValues(getGreenPixelMatrixElement(i, j), a, b, c);
        newBluePixelMatrix[i][j] =
                computeAdjustedValues(getBluePixelMatrixElement(i, j), a, b, c);
      }
    }
    return new ImageImpl(newRedPixelMatrix, newGreenPixelMatrix, newBluePixelMatrix);
  }

  private int computeAdjustedValues(int currPixelValue, double a, double b, double c) {
    int newValue = (int) ((a * (Math.pow(currPixelValue, 2))) + (b * currPixelValue) + c);
    newValue = clip(newValue);
    return newValue;
  }

}
