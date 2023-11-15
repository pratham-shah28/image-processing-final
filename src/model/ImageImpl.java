package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeSet;

import static java.lang.Math.sqrt;

/**
 * This class represents an Image object and its representations implementing Image interface.
 */
public class ImageImpl implements Image {

  private final int[][] redChannel;
  private final int[][] greenChannel;
  private final int[][] blueChannel;
  private final int width;
  private final int height;

  /**
   * A constructor for the ImageImpl class.
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
      int[] result = {max, max, max};
      return result;
    };
    return applyOperation(valueOperation);
  }

  @Override
  public Image valueSplit(double perc) {
    TriFunction<Integer, Integer, Integer, int[]> valueOperation = (red, green, blue) -> {
      int max = Math.max(red, Math.max(green, blue));
      int[] result = {max, max, max};
      return result;
    };
    return applyOperationSplit(valueOperation, perc);
  }

  @Override
  public Image luma() {
    TriFunction<Integer, Integer, Integer, int[]> lumaOperation = (red, green, blue) -> {
      int luma = (int) ((0.2126 * red) + (0.7152 * green) + (0.0722 * blue));
      luma = clip(luma);
      int[] result = {luma, luma, luma};
      return result;
    };
    return applyOperation(lumaOperation);
  }

  @Override
  public Image lumaSplit(double perc) {
    TriFunction<Integer, Integer, Integer, int[]> lumaOperation = (red, green, blue) -> {
      int luma = (int) ((0.2126 * red) + (0.7152 * green) + (0.0722 * blue));
      luma = clip(luma);
      int[] result = {luma, luma, luma};
      return result;
    };
    return applyOperationSplit(lumaOperation, perc);
  }

  @Override
  public Image intensity() {
    TriFunction<Integer, Integer, Integer, int[]> intensityOperation = (red, green, blue) -> {
      int avg = Math.round((red + green + blue) / 3);
      int[] result = {avg, avg, avg};
      return result;
    };
    return applyOperation(intensityOperation);
  }

  @Override
  public Image intensitySplit(double perc) {
    TriFunction<Integer, Integer, Integer, int[]> intensityOperation = (red, green, blue) -> {
      int avg = Math.round((red + green + blue) / 3);
      int[] result = {avg, avg, avg};
      return result;
    };
    return applyOperationSplit(intensityOperation, perc);
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
  public Image sepiaSplit(double perc) {
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
    return applyOperationSplit(sepiaOperation, perc);
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
      int[] result = {red, green, blue};
      return result;
    };
    return applyOperation(brightenOperation);
  }

  @Override
  public Image combine(Image redImage, Image greenImage, Image blueImage) throws Exception {
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
  public Image blurSplit(double perc) {
    double[][] kernel = {{0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125}, {0.0625, 0.125, 0.0625}};
    return kernelOperationSplit(this, kernel, perc);
  }

  @Override
  public Image sharpen() {
    double[][] kernel =
            {{-0.125, -0.125, -0.125, -0.125, -0.125}, {-0.125, 0.25, 0.25, 0.25, -0.125},
                    {-0.125, 0.25, 1, 0.25, -0.125}, {-0.125, 0.25, 0.25, 0.25, -0.125},
                    {-0.125, -0.125, -0.125, -0.125, -0.125}};
    return kernelOperation(this, kernel);
  }

  @Override
  public Image sharpenSplit(double perc) {
    double[][] kernel =
            {{-0.125, -0.125, -0.125, -0.125, -0.125}, {-0.125, 0.25, 0.25, 0.25, -0.125},
                    {-0.125, 0.25, 1, 0.25, -0.125}, {-0.125, 0.25, 0.25, 0.25, -0.125},
                    {-0.125, -0.125, -0.125, -0.125, -0.125}};
    return kernelOperationSplit(this, kernel, perc);
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

  private Image kernelOperationSplit(Image img, double[][] kernel, double perc) {
    int[][] newRedPixelMatrix = new int[img.getWidth()][img.getHeight()];
    int[][] newGreenPixelMatrix = new int[img.getWidth()][img.getHeight()];
    int[][] newBluePixelMatrix = new int[img.getWidth()][img.getHeight()];
    int width = img.getWidth();
    int height = img.getHeight();
    int heightSplit = (int) Math.round(img.getHeight()*perc / 100);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if (y > heightSplit) {
          newRedPixelMatrix[x][y] = this.getRedPixelMatrixElement(x,y);
          newGreenPixelMatrix[x][y] = this.getGreenPixelMatrixElement(x,y);
          newBluePixelMatrix[x][y] = this.getBluePixelMatrixElement(x,y);
        }
        else if (y == heightSplit) {
          if (x % 2 == 0) {
            newRedPixelMatrix[x][y] = 255;
            newGreenPixelMatrix[x][y] = 0;
            newBluePixelMatrix[x][y] = 0;
          } else {
            newRedPixelMatrix[x][y] = this.getRedPixelMatrixElement(x, y);
            newGreenPixelMatrix[x][y] = this.getGreenPixelMatrixElement(x, y);
            newBluePixelMatrix[x][y] = this.getBluePixelMatrixElement(x, y);
          }
        }
        else {
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
    }


    for (int x = 0; x < width; x++) {
      for (int y = heightSplit; y < height; y++) {

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
    int i = 0, j = 0, k = 0;
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

  private Image applyOperationSplit(TriFunction<Integer, Integer, Integer, int[]> operation, double perc) {
    int[][] newRedPixelMatrix = new int[this.width][this.height];
    int[][] newGreenPixelMatrix = new int[this.width][this.height];
    int[][] newBluePixelMatrix = new int[this.width][this.height];

    int height = (int) Math.round((this.height * perc)/ 100);

    for (int x = 0; x < this.width; x++) {
      for (int y = 0; y < this.height; y++) {
        int red = this.getRedPixelMatrixElement(x, y);
        int green = this.getGreenPixelMatrixElement(x, y);
        int blue = this.getBluePixelMatrixElement(x, y);
        if (y < height) {
          int[] result = operation.apply(red, green, blue);
          newRedPixelMatrix[x][y] = result[0];
          newGreenPixelMatrix[x][y] = result[1];
          newBluePixelMatrix[x][y] = result[2];
        }
        else if (y == height) {
          if (x % 2 == 0) {
            newRedPixelMatrix[x][y] = 255;
            newGreenPixelMatrix[x][y] = 0;
            newBluePixelMatrix[x][y] = 0;
          }
          else {
            newRedPixelMatrix[x][y] = red;
            newGreenPixelMatrix[x][y] = green;
            newBluePixelMatrix[x][y] = blue;
          }
        }
        else {
          newRedPixelMatrix[x][y] = red;
          newGreenPixelMatrix[x][y] = green;
          newBluePixelMatrix[x][y] = blue;
        }
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
    return width;
  }

  private double[][] haar(int[][] sequence, double factor) {
    double[][] paddedSequence = padMatrix(sequence);
    int len = paddedSequence.length;
    while (len > 1) {
      for (int i = 0; i < len; i++) {
        double[] tempArray = Arrays.copyOf(paddedSequence[i], len);
        tempArray = transform(tempArray);
        System.arraycopy(tempArray,0, paddedSequence[i],0, len);

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
        System.arraycopy(tempArray,0, paddedSequence[i],0, len);
      }
      len = len * 2;
    }
    return paddedSequence;
  }

  private int[][] compressMatrix(int[][] pixelMatrix, double factor) {
    double[][] transformed = haar(pixelMatrix, factor);
    System.out.println("transformed matrix: \n");
    System.out.println(Arrays.deepToString(transformed));
    double[][] inversedTransformed = invharr(transformed);

    System.out.println("inverse matrix: \n");
    System.out.println(Arrays.deepToString(inversedTransformed));

    for (int i = 0; i < pixelMatrix.length; i++) {
      for (int j = 0; j < pixelMatrix[0].length; j++) {
        pixelMatrix[i][j] = clip(inversedTransformed[i][j]);
      }
    }
    return pixelMatrix;
  }

  @Override
  public Image compress(double factor) {
    System.out.println("Compressing----");
    double[][] transformedRedPixelMatrix = new double[this.getWidth()][this.getHeight()];
    double[][] transformedGreenPixelMatrix = new double[this.getWidth()][this.getHeight()];
    double[][] transformedBluePixelMatrix = new double[this.getWidth()][this.getHeight()];

    transformedRedPixelMatrix = haar(this.redChannel, factor);
    transformedGreenPixelMatrix = haar(this.greenChannel, factor);
    transformedBluePixelMatrix = haar(this.blueChannel, factor);
    Map<String, double[][]> matrices = applyThreshold3(transformedRedPixelMatrix,transformedGreenPixelMatrix,transformedBluePixelMatrix, factor);
    transformedRedPixelMatrix = invharr(matrices.get("red"));
    transformedGreenPixelMatrix = invharr(matrices.get("green"));
    transformedBluePixelMatrix = invharr(matrices.get("blue"));

    int[][] redPixelMatrix = new int[this.getWidth()][this.getHeight()];
    int[][] greenPixelMatrix = new int[this.getWidth()][this.getHeight()];
    int[][] bluePixelMatrix = new int[this.getWidth()][this.getHeight()];


    for (int i = 0; i < redPixelMatrix.length; i++) {
      for (int j = 0; j < redPixelMatrix[0].length; j++) {
        redPixelMatrix[i][j] = clip(transformedRedPixelMatrix[i][j]);
        greenPixelMatrix[i][j] = clip(transformedGreenPixelMatrix[i][j]);
        bluePixelMatrix[i][j] = clip(transformedBluePixelMatrix[i][j]);
      }
    }

    return new ImageImpl(redPixelMatrix, greenPixelMatrix, bluePixelMatrix);
  }

  

  private Map<String, double[][]> applyThreshold3(double[][] transformedRed, double[][] transformedGreen, double[][] transformedBlue, double factor) {
    int count = 0;
    Map<Double, Double> uniqueValues = new HashMap<>();
    Map<String, double[][]> matrices = new HashMap<>();
    //System.out.println("og sequence: " + Arrays.deepToString(sequence));
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

//    Set<Double> values = new TreeSet<>(uniqueValues.keySet());

    List<Double> values = new ArrayList<>(new TreeSet<>(uniqueValues.keySet()));

    if (n != 0) {
      double threshold = values.get(n);
      System.out.println("threshold: " + threshold);
      for (int i = 0; i < transformedRed.length; i++) {
        for (int j = 0; j < transformedRed[0].length; j++) {
          if (Math.abs(transformedRed[i][j]) < threshold) {
            transformedRed[i][j] = 0;
          }
          if (Math.abs(transformedGreen[i][j]) < threshold) {
            transformedGreen[i][j] = 0;
          }
          if (Math.abs(transformedBlue[i][j]) < threshold) {
            transformedBlue[i][j] = 0;
          }
        }
      }
      matrices.put("red", transformedRed);
      matrices.put("green", transformedGreen);
      matrices.put("blue", transformedBlue);
      return matrices;
    }


    else {
      matrices.put("red", transformedRed);
      matrices.put("green", transformedGreen);
      matrices.put("blue", transformedBlue);
      return matrices;
    }

//    int[][] indices = new int[sequence.length * sequence[0].length][2];
//
//    // Initialize the indices array with row and column indices
//    int index = 0;
//    for (int i = 0; i < sequence.length; i++) {
//      for (int j = 0; j < sequence[0].length; j++) {
//        indices[index][0] = i;
//        indices[index][1] = j;
//        index++;
//      }
//    }
//    // Sort the indices array based on the corresponding values in the original matrix
//    Arrays.sort(indices, (a, b) -> Double.compare(Math.abs(sequence[a[0]][a[1]]), Math.abs(sequence[b[0]][b[1]])));
//
//    // Set the n smallest values in the original matrix to zero using the sorted indices
//    int i = 0;
//    while (n > 0 && i < indices.length) {
//      int row = indices[i][0];
//      int col = indices[i][1];
//      if (sequence[row][col] != 0) {
//        sequence[row][col] = 0;
//        n = n - 1;
//      }
//      i = i + 1;
//    }
  }

}

