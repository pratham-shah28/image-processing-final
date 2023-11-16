//package model;
//
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeSet;
//
//import static java.lang.Math.sqrt;
//
//public class ImageProImpl implements ImagePro {
//
//  private final int[][] redChannel;
//  private final int[][] greenChannel;
//  private final int[][] blueChannel;
//  private final int width;
//  private final int height;
//
//  private ImageImpl image;
//  /**
//   * A constructor for the ImageImpl class.
//   *
//   * @param redChannel   Matrix for the red channel of the image.
//   * @param greenChannel Matrix for the green channel of the image.
//   * @param blueChannel  Matrix for the blue channel of the image.
//   */
//  public ImageProImpl(int[][] redChannel, int[][] greenChannel, int[][] blueChannel) {
//    this.redChannel = redChannel;
//    this.greenChannel = greenChannel;
//    this.blueChannel = blueChannel;
//    this.width = redChannel.length;
//    this.height = redChannel[0].length;
//    image = new ImageImpl(redChannel, greenChannel, blueChannel);
//  }
//
//  private double[][] haar(int[][] sequence, double factor) {
//    double[][] paddedSequence = padMatrix(sequence);
//    int len = paddedSequence.length;
//    while (len > 1) {
//      for (int i = 0; i < len; i++) {
//        double[] tempArray = Arrays.copyOf(paddedSequence[i], len);
//        tempArray = transform(tempArray);
//        System.arraycopy(tempArray, 0, paddedSequence[i], 0, len);
//
//      }
//      for (int j = 0; j < len; j++) {
//        double[] tempColArray = new double[len];
//        for (int k = 0; k < len; k++) {
//          tempColArray[k] = paddedSequence[k][j];
//        }
//
//        tempColArray = transform(tempColArray);
//
//        for (int l = 0; l < len; l++) {
//          paddedSequence[l][j] = tempColArray[l];
//        }
//      }
//      len = len / 2;
//    }
//    return paddedSequence;
//  }
//
//  private double[][] invharr(double[][] paddedSequence) {
//    int len = 2;
//    while (len <= paddedSequence.length) {
//      for (int j = 0; j < len; j++) {
//        double[] tempColArray = new double[len];
//        for (int k = 0; k < len; k++) {
//          tempColArray[k] = paddedSequence[k][j];
//        }
//        tempColArray = inverse(tempColArray);
//        for (int l = 0; l < len; l++) {
//          paddedSequence[l][j] = tempColArray[l];
//        }
//      }
//      for (int i = 0; i < len; i++) {
//        double[] tempArray = Arrays.copyOf(paddedSequence[i], len);
//        tempArray = inverse(tempArray);
//        System.arraycopy(tempArray, 0, paddedSequence[i], 0, len);
//      }
//      len = len * 2;
//    }
//    return paddedSequence;
//  }
//
//  private int[][] compressMatrix(int[][] pixelMatrix, double factor) {
//    double[][] transformed = haar(pixelMatrix, factor);
//    System.out.println("transformed matrix: \n");
//    System.out.println(Arrays.deepToString(transformed));
//    double[][] inversedTransformed = invharr(transformed);
//
//    System.out.println("inverse matrix: \n");
//    System.out.println(Arrays.deepToString(inversedTransformed));
//
//    for (int i = 0; i < pixelMatrix.length; i++) {
//      for (int j = 0; j < pixelMatrix[0].length; j++) {
//        pixelMatrix[i][j] = image.clip(inversedTransformed[i][j]);
//      }
//    }
//    return pixelMatrix;
//  }
//
//  @Override
//  public Image compress(double factor) {
//
//    double[][] transformedRedPixelMatrix = new double[this.getWidth()][this.getHeight()];
//    double[][] transformedGreenPixelMatrix = new double[this.getWidth()][this.getHeight()];
//    double[][] transformedBluePixelMatrix = new double[this.getWidth()][this.getHeight()];
//
//    int[][] newRedPixelMatrix = new int[this.getWidth()][this.getHeight()];
//    int[][] newGreenPixelMatrix = new int[this.getWidth()][this.getHeight()];
//    int[][] newBluePixelMatrix = new int[this.getWidth()][this.getHeight()];
//
//    for (int x = 0; x < this.getWidth(); x++) {
//      for (int y = 0; y < this.getHeight(); y++) {
//        newRedPixelMatrix[x][y] = this.getRedPixelMatrixElement(x, y);
//        newGreenPixelMatrix[x][y] = this.getGreenPixelMatrixElement(x, y);
//        newBluePixelMatrix[x][y] = this.getBluePixelMatrixElement(x, y);
//      }
//    }
//
//    transformedRedPixelMatrix = haar(newRedPixelMatrix, factor);
//    transformedGreenPixelMatrix = haar(newGreenPixelMatrix, factor);
//    transformedBluePixelMatrix = haar(newBluePixelMatrix, factor);
//    Map<String, double[][]> matrices = applyThreshold3(transformedRedPixelMatrix, transformedGreenPixelMatrix, transformedBluePixelMatrix, factor);
//    transformedRedPixelMatrix = invharr(matrices.get("red"));
//    transformedGreenPixelMatrix = invharr(matrices.get("green"));
//    transformedBluePixelMatrix = invharr(matrices.get("blue"));
//
//    int[][] redPixelMatrix = new int[this.getWidth()][this.getHeight()];
//    int[][] greenPixelMatrix = new int[this.getWidth()][this.getHeight()];
//    int[][] bluePixelMatrix = new int[this.getWidth()][this.getHeight()];
//
//
//    for (int i = 0; i < redPixelMatrix.length; i++) {
//      for (int j = 0; j < redPixelMatrix[0].length; j++) {
//        redPixelMatrix[i][j] = image.clip(Math.round(transformedRedPixelMatrix[i][j]));
//        greenPixelMatrix[i][j] = image.clip(Math.round(transformedGreenPixelMatrix[i][j]));
//        bluePixelMatrix[i][j] = image.clip(Math.round(transformedBluePixelMatrix[i][j]));
//      }
//    }
//    return new ImageProImpl(redPixelMatrix, greenPixelMatrix, bluePixelMatrix);
//  }
//
//
//  private Map<String, double[][]> applyThreshold3(double[][] transformedRed, double[][] transformedGreen, double[][] transformedBlue, double factor) {
//
//    Map<Double, Double> uniqueValues = new HashMap<>();
//    Map<String, double[][]> matrices = new HashMap<>();
//    //System.out.println("og sequence: " + Arrays.deepToString(sequence));
//    for (int i = 0; i < transformedRed.length; i++) {
//      for (int j = 0; j < transformedRed[0].length; j++) {
//        if (!(uniqueValues.containsKey(Math.abs(transformedRed[i][j])))) {
//          uniqueValues.put(Math.abs(transformedRed[i][j]), Math.abs(transformedRed[i][j]));
//        }
//        if (!(uniqueValues.containsKey(Math.abs(transformedGreen[i][j])))) {
//          uniqueValues.put(Math.abs(transformedGreen[i][j]), Math.abs(transformedGreen[i][j]));
//        }
//        if (!(uniqueValues.containsKey(Math.abs(transformedBlue[i][j])))) {
//          uniqueValues.put(Math.abs(transformedBlue[i][j]), Math.abs(transformedBlue[i][j]));
//        }
//      }
//    }
//    int n = (int) Math.round((factor * uniqueValues.size()) / 100);
//    System.out.println("n: " + n);
//    System.out.println("unique values size: " + uniqueValues.size());
//    int count = 0;
//
////    Set<Double> values = new TreeSet<>(uniqueValues.keySet());
//
//    List<Double> values = new ArrayList<>(new TreeSet<>(uniqueValues.keySet()));
//
//    if (n != 0) {
//      System.out.println(values.size());
//      double threshold = values.get(n - 1);
//      System.out.println("threshold: " + threshold);
//      for (int i = 0; i < transformedRed.length; i++) {
//        for (int j = 0; j < transformedRed[0].length; j++) {
//          if (Math.abs(transformedRed[i][j]) <= threshold) {
//            transformedRed[i][j] = 0;
//            count += 1;
//          }
//          if (Math.abs(transformedGreen[i][j]) <= threshold) {
//            transformedGreen[i][j] = 0;
//            count += 1;
//          }
//          if (Math.abs(transformedBlue[i][j]) <= threshold) {
//            transformedBlue[i][j] = 0;
//            count += 1;
//          }
//        }
//      }
//      System.out.println("count: " + count);
//      matrices.put("red", transformedRed);
//      matrices.put("green", transformedGreen);
//      matrices.put("blue", transformedBlue);
//      return matrices;
//    } else {
//      matrices.put("red", transformedRed);
//      matrices.put("green", transformedGreen);
//      matrices.put("blue", transformedBlue);
//      return matrices;
//    }
//  }
//
//  private HashMap<String, int[]> getFrequency() {
//    int[] redFrequency = new int[256];
//    int[] greenFrequency = new int[256];
//    int[] blueFrequency = new int[256];
//    HashMap<String, int[]> rgbFrequency = new HashMap<>();
//
//    for (int i = 0; i < 256; i++) {
//      redFrequency[i] = 0;
//      greenFrequency[i] = 0;
//      blueFrequency[i] = 0;
//    }
//
//    for (int x = 0; x < this.getWidth(); x++) {
//      for (int y = 0; y < this.getHeight(); y++) {
//        int red = this.getRedPixelMatrixElement(x, y);
//        int green = this.getGreenPixelMatrixElement(x, y);
//        int blue = this.getBluePixelMatrixElement(x, y);
//        redFrequency[red]++;
//        greenFrequency[green]++;
//        blueFrequency[blue]++;
//      }
//    }
//
//    rgbFrequency.put("red", redFrequency);
//    rgbFrequency.put("green", greenFrequency);
//    rgbFrequency.put("blue", blueFrequency);
//
//    return rgbFrequency;
//  }
//
//  private void plotLine(Graphics2D g2d, int[] frequencies, int width, int height, Color lineColor, int maxFrequency) {
//    g2d.setColor(lineColor);
//    g2d.setStroke(new BasicStroke(1));
//
//    for (int i = 0; i < frequencies.length - 1; i++) {
//      int x1 = i * (width - 1) / (frequencies.length - 1);
//      int y1 = height - frequencies[i] * height / maxFrequency;
//      int x2 = (i + 1) * (width - 1) / (frequencies.length - 1);
//      int y2 = height - frequencies[i + 1] * height / maxFrequency;
//      g2d.drawLine(x1, y1, x2, y2);
//    }
//  }
//
//  private void drawGrid(Graphics2D g2d, int gridSize, int width, int height) {
//    g2d.setColor(Color.lightGray);
//    g2d.setStroke(new BasicStroke());
//
//    for (int x = 0; x < width; x += gridSize) {
//      g2d.drawLine(x, 0, x, height);
//    }
//
//    // Draw horizontal lines
//    for (int y = 0; y < height; y += gridSize) {
//      g2d.drawLine(0, y, width, y);
//    }
//  }
//
//  @Override
//  public Image createHistogram() {
//    HashMap<String, int[]> rgbFrequency = getFrequency();
//    int width = 256;
//    int height = 256;
//
//    BufferedImage graphImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//    Graphics2D g2d = graphImage.createGraphics();
//
//    g2d.setBackground(Color.WHITE);
//    g2d.clearRect(0, 0, width, height);
//
//    // Find max frequency for normalization
//    int maxFrequency = Integer.MIN_VALUE;
//    for (int frequency : rgbFrequency.get("red")) {
//      if (frequency > maxFrequency) {
//        maxFrequency = frequency;
//      }
//    }
//    for (int frequency : rgbFrequency.get("green")) {
//      if (frequency > maxFrequency) {
//        maxFrequency = frequency;
//      }
//    }
//    for (int frequency : rgbFrequency.get("blue")) {
//      if (frequency > maxFrequency) {
//        maxFrequency = frequency;
//      }
//    }
//
//    //draw grid
//
//    int gridSize = 13;
//    drawGrid(g2d, gridSize, width, height);
//
//    // Plot the line graph for Red Channel (in red)
//    plotLine(g2d, rgbFrequency.get("red"), width, height, Color.RED, maxFrequency);
//
//    // Plot the line graph for Green Channel (in green)
//    plotLine(g2d, rgbFrequency.get("green"), width, height, Color.GREEN, maxFrequency);
//
//    // Plot the line graph for Blue Channel (in blue)
//    plotLine(g2d, rgbFrequency.get("blue"), width, height, Color.BLUE, maxFrequency);
//
//    // Dispose Graphics2D
//    g2d.dispose();
//
//    int[][] newRedPixelMatrix = new int[height][width];
//    int[][] newGreenPixelMatrix = new int[height][width];
//    int[][] newBluePixelMatrix = new int[height][width];
//
//    for (int x = 0; x < height; x++) {
//      for (int y = 0; y < width; y++) {
//        int pixel = graphImage.getRGB(y, x);
//        newRedPixelMatrix[x][y] = (pixel & 0xff0000) >> 16;
//        newGreenPixelMatrix[x][y] = (pixel & 0xff00) >> 8;
//        newBluePixelMatrix[x][y] = pixel & 0xff;
//      }
//    }
//    return new ImageProImpl(newRedPixelMatrix, newGreenPixelMatrix, newBluePixelMatrix);
//  }
//
//  private int findPeak(int[] channel) {
//    int maxFrequency = Integer.MIN_VALUE;
//    int maxIndex = 0;
//    for (int i = 10; i < channel.length - 10; i++) {
//      if (channel[i] > maxFrequency) {
//        maxIndex = i;
//        maxFrequency = channel[i];
//      }
//    }
//    return maxIndex;
//  }
//
//  @Override
//  public Image colorCorrect() {
//    HashMap<String, int[]> rgbFrequency = getFrequency();
//    int redPeakIndex = findPeak(rgbFrequency.get("red"));
//    int greenPeakIndex = findPeak(rgbFrequency.get("green"));
//    int bluePeakIndex = findPeak(rgbFrequency.get("blue"));
//
//    int avgPeakPosition = (redPeakIndex + greenPeakIndex + bluePeakIndex) / 3;
//
//    int[][] newRedPixelMatrix = new int[this.getWidth()][this.getHeight()];
//    int[][] newGreenPixelMatrix = new int[this.getWidth()][this.getHeight()];
//    int[][] newBluePixelMatrix = new int[this.getWidth()][this.getHeight()];
//
//    for (int i = 0; i < this.getWidth(); i++) {
//      for (int j = 0; j < this.getHeight(); j++) {
//        newRedPixelMatrix[i][j] = this.getRedPixelMatrixElement(i, j);
//        newGreenPixelMatrix[i][j] = this.getGreenPixelMatrixElement(i, j);
//        newBluePixelMatrix[i][j] = this.getBluePixelMatrixElement(i, j);
//      }
//    }
//
//    shiftChannel(newRedPixelMatrix, redPeakIndex, avgPeakPosition);
//    shiftChannel(newGreenPixelMatrix, greenPeakIndex, avgPeakPosition);
//    shiftChannel(newBluePixelMatrix, bluePeakIndex, avgPeakPosition);
//
//    return new ImageProImpl(newRedPixelMatrix, newGreenPixelMatrix, newBluePixelMatrix);
//  }
//
//  private void shiftChannel(int[][] pixelMatrix, int peakIndex, int avgPeakIndex) {
//    int difference = avgPeakIndex - peakIndex;
//    for (int i = 0; i < pixelMatrix.length; i++) {
//      for (int j = 0; j < pixelMatrix[0].length; j++) {
//        pixelMatrix[i][j] = pixelMatrix[i][j] + difference;
//        pixelMatrix[i][j] = image.clip(pixelMatrix[i][j]);
//      }
//    }
//  }
//
//  /**
//   * @param b_p
//   * @param m_p
//   * @param w_p
//   * @return
//   */
//  @Override
//  public Image adjustLevels(int b_p, int m_p, int w_p) {
//    double A = (Math.pow(b_p, 2) * (m_p - w_p)) - (b_p * (Math.pow(m_p, 2) - Math.pow(w_p, 2))) + (w_p * Math.pow(m_p, 2)) - (m_p * Math.pow(w_p, 2));
//    double Aa = ((-1 * b_p) * (128 - 255)) + (128 * w_p) - (255 * m_p);
//    double Ab = (Math.pow(b_p, 2) * (128 - 255)) + (255 * Math.pow(m_p, 2)) - (128 * Math.pow(w_p, 2));
//    double Ac = (Math.pow(b_p, 2) * ((255 * m_p) - (128 * w_p))) - (b_p * ((255 * Math.pow(m_p, 2)) - (128 * Math.pow(w_p, 2))));
//    double a = Aa / A;
//    double b = Ab / A;
//    double c = Ac / A;
//
//    int[][] newRedPixelMatrix = new int[this.getWidth()][this.getHeight()];
//    int[][] newGreenPixelMatrix = new int[this.getWidth()][this.getHeight()];
//    int[][] newBluePixelMatrix = new int[this.getWidth()][this.getHeight()];
//
//    for (int i = 0; i < this.getWidth(); i++) {
//      for (int j = 0; j < this.getHeight(); j++) {
//        newRedPixelMatrix[i][j] = computeAdjustedValues(getRedPixelMatrixElement(i, j), a, b, c);
//        newGreenPixelMatrix[i][j] = computeAdjustedValues(getGreenPixelMatrixElement(i, j), a, b, c);
//        newBluePixelMatrix[i][j] = computeAdjustedValues(getBluePixelMatrixElement(i, j), a, b, c);
//      }
//    }
//    return new ImageProImpl(newRedPixelMatrix, newGreenPixelMatrix, newBluePixelMatrix);
//  }
//
//  private int computeAdjustedValues(int currPixelValue, double a, double b, double c) {
//    int newValue = (int) ((a * (Math.pow(currPixelValue, 2))) + (b * currPixelValue) + c);
//    newValue = image.clip(newValue);
//    return newValue;
//  }
//
//  @Override
//  public Image valueSplit(double perc) {
//    TriFunction<Integer, Integer, Integer, int[]> valueOperation = (red, green, blue) -> {
//      int max = Math.max(red, Math.max(green, blue));
//      int[] result = {max, max, max};
//      return result;
//    };
//    return image.applyOperationSplit(valueOperation, perc);
//  }
//
//  @Override
//  public Image lumaSplit(double perc) {
//    TriFunction<Integer, Integer, Integer, int[]> lumaOperation = (red, green, blue) -> {
//      int luma = (int) ((0.2126 * red) + (0.7152 * green) + (0.0722 * blue));
//      luma = image.clip(luma);
//      int[] result = {luma, luma, luma};
//      return result;
//    };
//    return image. applyOperationSplit(lumaOperation, perc);
//  }
//
//  @Override
//  public Image intensitySplit(double perc) {
//    TriFunction<Integer, Integer, Integer, int[]> intensityOperation = (red, green, blue) -> {
//      int avg = Math.round((red + green + blue) / 3);
//      int[] result = {avg, avg, avg};
//      return result;
//    };
//    return applyOperationSplit(intensityOperation, perc);
//  }
//
//  @Override
//  public Image sepiaSplit(double perc) {
//    TriFunction<Integer, Integer, Integer, int[]> sepiaOperation = (red, green, blue) -> {
//      int sepia_red = (int) ((0.393 * red) + (0.769 * green) + (0.189 * blue));
//      int sepia_green = (int) ((0.349 * red) + (0.686 * green) + (0.168 * blue));
//      int sepia_blue = (int) ((0.272 * red) + (0.534 * green) + (0.131 * blue));
//
//      sepia_red = image.image.clip(sepia_red);
//      image.
//      sepia_green = image.clip(sepia_green);
//      sepia_blue = image.clip(sepia_blue);
//      int[] result = {sepia_red, sepia_green, sepia_blue};
//      return result;
//    };
//    return applyOperationSplit(sepiaOperation, perc);
//  }
//
//  @Override
//  public Image blurSplit(double perc) {
//    double[][] kernel = {{0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125}, {0.0625, 0.125, 0.0625}};
//    return kernelOperationSplit(this, kernel, perc);
//  }
//
//  @Override
//  public Image sharpenSplit(double perc) {
//    double[][] kernel =
//            {{-0.125, -0.125, -0.125, -0.125, -0.125}, {-0.125, 0.25, 0.25, 0.25, -0.125},
//                    {-0.125, 0.25, 1, 0.25, -0.125}, {-0.125, 0.25, 0.25, 0.25, -0.125},
//                    {-0.125, -0.125, -0.125, -0.125, -0.125}};
//    return kernelOperationSplit(this, kernel, perc);
//  }
//
//  private double[] transform(double[] sequence) {
//    double[] avg = new double[sequence.length / 2];
//    double[] diff = new double[sequence.length / 2];
//    double[] result = new double[avg.length + diff.length];
//
//    for (int i = 0; i < sequence.length; i += 2) {
//      double av = (sequence[i] + sequence[i + 1]) / sqrt(2);
//      double di = (sequence[i] - sequence[i + 1]) / sqrt(2);
//      avg[i / 2] = av;
//      diff[i / 2] = di;
//    }
//    System.arraycopy(avg, 0, result, 0, avg.length);
//    System.arraycopy(diff, 0, result, avg.length, diff.length);
//    return result;
//  }
//
//  private double[] inverse(double[] sequence) {
//    double[] avg = new double[sequence.length / 2];
//    double[] diff = new double[sequence.length / 2];
//    double[] result = new double[sequence.length];
//
//    for (int i = 0, j = sequence.length / 2; i < sequence.length / 2
//            && j < sequence.length; i += 1, j += 1) {
//      double av = (sequence[i] + sequence[j]) / sqrt(2);
//      double di = (sequence[i] - sequence[j]) / sqrt(2);
//      avg[i] = av;
//      diff[i] = di;
//    }
//    int i = 0, j = 0, k = 0;
//    while (i < avg.length && j < diff.length) {
//      result[k++] = avg[i++];
//      result[k++] = diff[j++];
//    }
//    return result;
//  }
//  private double[][] padMatrix(int[][] pixelMatrix) {
//
//    int rows = pixelMatrix.length;
//    int cols = pixelMatrix[0].length;
//
//    int target = 1;
//    while (target < rows || target < cols) {
//      target *= 2;
//    }
//
//    double[][] squareMatrix = new double[target][target];
//
//    for (int i = 0; i < rows; i++) {
//      for (int j = 0; j < cols; j++) {
//        squareMatrix[i][j] = pixelMatrix[i][j];
//      }
//    }
//    return squareMatrix;
//  }
//
//  @Override
//  public Image redComponent() {
//    return null;
//  }
//
//  @Override
//  public Image greenComponent() {
//    return null;
//  }
//
//  @Override
//  public Image blueComponent() {
//    return null;
//  }
//
//  @Override
//  public Image intensity() {
//    return null;
//  }
//
//  @Override
//  public Image value() {
//    return null;
//  }
//
//  @Override
//  public Image luma() {
//    return null;
//  }
//
//  @Override
//  public Image brighten(int factor) {
//    return null;
//  }
//
//  @Override
//  public Image sepia() {
//    return null;
//  }
//
//  @Override
//  public Image combine(Image redImage, Image greenImage, Image blueImage) throws Exception {
//    return null;
//  }
//
//  @Override
//  public Image flipHorizontal() {
//    return null;
//  }
//
//  @Override
//  public Image flipVertical() {
//    return null;
//  }
//
//  @Override
//  public Image blur() {
//    return null;
//  }
//
//  @Override
//  public Image sharpen() {
//    return null;
//  }
//
//  @Override
//  public int getRedPixelMatrixElement(int x, int y) {
//    return 0;
//  }
//
//  @Override
//  public int getGreenPixelMatrixElement(int x, int y) {
//    return 0;
//  }
//
//  @Override
//  public int getBluePixelMatrixElement(int x, int y) {
//    return 0;
//  }
//
//  @Override
//  public int getHeight() {
//    return 0;
//  }
//
//  @Override
//  public int getWidth() {
//    return 0;
//  }
//}
