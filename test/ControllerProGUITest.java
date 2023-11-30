import org.junit.Test;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.Border;

import controller.ControllerProGUI;
import controller.Features;
import model.Image;
import model.ImageCreator;
import view.ViewGUIInterface;

import static org.junit.Assert.assertEquals;

/**
 * This is a JUnit test class for the GUI version of controller and View.
 */
public class ControllerProGUITest {
  private class MockViewGUI extends JFrame implements ViewGUIInterface {
    private JButton applyButton, loadButton, saveButton, compressButton,
            splitPercButton, popupButton, levelsAdjustButton, applyTransformation;
    protected JLabel histogramLabel, imageLabel;
    private JTextField compressInput, splitInput, bInput, mInput, wInput;
    private JComboBox<String> imageOperationList;
    private JToggleButton toggleButton;
    private JScrollPane scrollPane;
    private StringBuilder log;
    private int compressionValue;
    private String splitPerc;
    private String b;
    private String m;
    private String w;
    private String command;

    /**
     * Constructs Mock View GUI.
     * @param log log used to test functionality.
     */
    public MockViewGUI(StringBuilder log) {
      this.splitPerc = "50";
      this.log = log;
    }

    private void buildGUI() {
      JPanel leftPanel = new JPanel(new BorderLayout());

      // Upper part of the left pane
      JPanel upperLeftPanel = new JPanel(new GridLayout(0, 2, 5, 10));
      JPanel levelsAdjustPanel = new JPanel(new GridLayout(0, 3));
      JPanel splitPanel = new JPanel();
      Border leftUpperPanelBorder = BorderFactory.createTitledBorder("Operations");
      Border leftSplitPanelBorder = BorderFactory.createTitledBorder("Split Mode");
      Border leftBottomPanelBorder = BorderFactory.createTitledBorder("Histogram");

      // Create an array of items for the dropdown
      String[] items = {"red-component", "green-component", "blue-component", "flip-vertical",
              "flip-horizontal", "blur", "sharpen", "sepia", "greyscale", "color-correct"};
      imageOperationList = new JComboBox<>(items);
      applyButton = new JButton("Apply");
      loadButton = new JButton("Load");
      saveButton = new JButton("Save");
      compressInput = new JTextField(10);
      compressInput.setToolTipText("Enter Compression percentage");
      compressButton = new JButton("Compress");
      bInput = new JTextField();
      bInput.setToolTipText("Enter 'b' value");
      mInput = new JTextField();
      mInput.setToolTipText("Enter 'm' value");
      wInput = new JTextField();
      wInput.setToolTipText("Enter 'w' value");
      levelsAdjustButton = new JButton("Adjust level");
      levelsAdjustPanel.add(bInput);
      levelsAdjustPanel.add(mInput);
      levelsAdjustPanel.add(wInput);

      upperLeftPanel.add(loadButton);
      upperLeftPanel.add(saveButton);
      upperLeftPanel.add(imageOperationList);
      upperLeftPanel.add(applyButton);
      upperLeftPanel.add(compressInput);
      upperLeftPanel.add(compressButton);
      upperLeftPanel.add(levelsAdjustPanel);
      upperLeftPanel.add(levelsAdjustButton);
      upperLeftPanel.setBorder(leftUpperPanelBorder);


      splitInput = new JTextField(10);
      splitInput.setEditable(false);
      splitInput.setToolTipText("Enter Split Percentage");
      splitPercButton = new JButton("Set split percentage");
      splitPercButton.setEnabled(false);
      toggleButton = new JToggleButton("Enable Split Mode");
      toggleButton.setToolTipText("Click to toggle between preview and normal mode.");
      applyTransformation = new JButton("Save transformation");
      applyTransformation.setEnabled(false);
      splitPanel.add(toggleButton, BorderLayout.NORTH);
      splitPanel.add(splitInput);
      splitPanel.add(splitPercButton);
      splitPanel.add(applyTransformation, BorderLayout.NORTH);
      splitPanel.setBorder(leftSplitPanelBorder);


      // Bottom part of the left pane to display the image
      JPanel bottomLeftPanel = new JPanel();
      histogramLabel = new JLabel();
      histogramLabel.setPreferredSize(new Dimension(256, 256));
      bottomLeftPanel.add(histogramLabel);
      bottomLeftPanel.setBorder(leftBottomPanelBorder);

      // Add upper and bottom panels to the left pane
      leftPanel.add(upperLeftPanel, BorderLayout.NORTH);
      leftPanel.add(splitPanel, BorderLayout.CENTER);
      leftPanel.add(bottomLeftPanel, BorderLayout.SOUTH);

      // Right Pane
      JPanel rightPanel = new JPanel(new BorderLayout());
      imageLabel = new JLabel();
      imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
      scrollPane = new JScrollPane(imageLabel);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
      rightPanel.add(scrollPane, BorderLayout.CENTER);

      // Set up the JSplitPane
      JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
      splitPane.setDividerLocation(300); // Set the initial divider location

      // Set up the main frame
      this.setTitle("Graphical Image Manipulation Application");
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.getContentPane().add(splitPane);
      this.setSize(800, 600);
      this.setLocationRelativeTo(null);
      this.setVisible(true);
    }

    @Override
    public String getComboBoxSelectedItem() {
      log.append("\ngetComboBoxSelectedItem");
      return command;
    }

    @Override
    public int getCompressInput() {
      return compressionValue;
    }

    @Override
    public String getSplit() {
      log.append("\ngetSplit");
      return splitPerc;
    }

    @Override
    public void showDialog(String s) {
      log.append("\n" + s);
    }

    @Override
    public void updateImageLabel(Image image, Image histogram) {
      log.append("\nupdateImageLabel");
    }

    @Override
    public String bInput() {
      log.append("\n" + b);
      return b;
    }

    @Override
    public String mInput() {
      log.append("\n" + m);
      return m;
    }

    @Override
    public String wInput() {
      log.append("\n" + w);
      return w;
    }

    @Override
    public void toggleSet(boolean split) {
      log.append("\n" + split);
    }

    @Override
    public int saveOption() {
      log.append("\nsaveOption");
      return 0;
    }

    @Override
    public void addFeatures(Features features) {

    }

    @Override
    public File loadSelectedImage() {
      log.append("\nloadSelectedImage");
      return null;
    }

    @Override
    public File selectedDirectory() {
      log.append("\nselectedDirectory");
      return null;
    }

    @Override
    public String getImageName() {
      return null;
    }

    protected void setCompressValue(int compressionValue) {
      this.compressionValue = compressionValue;
    }

    protected void setSplitValue(String splitPerc) {
      this.splitPerc = splitPerc;
    }

    protected void setBMW(String b, String m, String w) {
      this.b = b;
      this.m = m;
      this.w = w;
    }

    protected void setComboBox(String command) {
      this.command = command;
    }
  }

  private class MockModel implements Image {
    private StringBuilder log;
    private int[][] red;
    private int[][] green;
    private int[][] blue;

    /**
     * Constructs Mock Model.
     * @param log log to be used to test the functionality.
     * @param red the red pixel array matrix.
     * @param green the green pixel array matrix.
     * @param blue the blue pixel array matrix.
     */
    public MockModel(StringBuilder log, int[][] red, int[][] green, int[][] blue) {
      this.log = log;
      this.red = red;
      this.green = green;
      this.blue = blue;
    }

    @Override
    public Image sepia() {
      log.append("\nsepia");
      return this;
    }

    @Override
    public Image redComponent() {
      log.append("\nredComponent");
      return this;
    }

    @Override
    public Image greenComponent() {
      log.append("\ngreenComponent");
      return this;
    }

    @Override
    public Image blueComponent() {
      log.append("\nblueComponent");
      return this;
    }

    @Override
    public Image intensity() {
      return this;
    }

    @Override
    public Image value() {
      return this;
    }

    @Override
    public Image luma() {
      log.append("\nluma");
      return this;
    }

    @Override
    public Image brighten(int factor) {
      log.append("\n" + factor);
      return this;
    }

    @Override
    public Image combine(Image redImage, Image greenImage, Image blueImage) {
      log.append(redImage);
      log.append(greenImage);
      log.append(blueImage);
      return null;
    }

    @Override
    public Image flipHorizontal() {
      log.append("\nflipHorizontal");
      return this;
    }

    @Override
    public Image flipVertical() {
      log.append("\nflipVertical");
      return this;
    }

    @Override
    public Image blur() {
      log.append("\nblur");
      return this;
    }

    @Override
    public Image sharpen() {
      log.append("\nsharpen");
      return this;
    }

    @Override
    public Image compress(double factor) {
      log.append("\n" + factor);
      return this;
    }

    @Override
    public Image createHistogram() {
      log.append("\ncreateHistogram");
      return this;
    }

    @Override
    public Image colorCorrect() {
      log.append("\ncolorCorrect");
      return this;
    }

    @Override
    public Image adjustLevels(int bP, int mP, int wP) {
      return this;
    }

    @Override
    public int getRedPixelMatrixElement(int x, int y) {
      return 0;
    }

    @Override
    public int getGreenPixelMatrixElement(int x, int y) {
      return 0;
    }

    @Override
    public int getBluePixelMatrixElement(int x, int y) {
      return 0;
    }

    @Override
    public int getHeight() {
      return 0;
    }

    @Override
    public int getWidth() {
      return 0;
    }

    @Override
    public Image applySplit(Image originalImage, double perc) {
      log.append("\n" + perc);
      return originalImage;
    }
  }

  private class MockImageCreator implements ImageCreator {
    private StringBuilder mockLog;

    /**
     * Constructs Mock Image creator.
     * @param mockLog the mock log used to test the functionality.
     */
    public MockImageCreator(StringBuilder mockLog) {
      this.mockLog = mockLog;
    }
    @Override
    public Image createModelImpl(int[][] redPixelMatrix,
                                 int[][] greenPixelMatrix,
                                 int[][] bluePixelMatrix) {
      return new MockModel(mockLog, redPixelMatrix, greenPixelMatrix, bluePixelMatrix);
    }
  }

  @Test
  public void loadFirst() throws IOException {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    controller.loadImage();
    String expected = "\nloadSelectedImage\n";
    assertEquals(expected, mockLog.toString());
  }
  @Test
  public void loadSecond() throws IOException {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    images.put("originalImage", null);
    controller.loadImage();
    String expected = "\nsaveOption\nselectedDirectory\nSave failed.\nloadSelectedImage\n";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void saveImageNotExist() throws IOException {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    controller.saveImage();
    String expected = "\nPlease load an image first";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void saveImageExists() throws IOException {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    images.put("originalImage", null);
    controller.saveImage();
    String expected = "\nselectedDirectory" +
            "\nSave failed.";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void applyTestImageDoesNotExist() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    controller.apply();
    String expected = "\nPlease load an image first";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void applyTestImageDoesExists() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    images.put("originalImage", image);
    images.put("newImage", image);
    controller.apply();
    String expected = "\ngetComboBoxSelectedItem" +
            "\ncreateHistogram" +
            "\nupdateImageLabel";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void applyTestRedComponent() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    view.setComboBox("red-component");
    images.put("originalImage", image);
    images.put("newImage", image);
    controller.apply();
    String expected = "\ngetComboBoxSelectedItem" +
            "\nredComponent" +
            "\ncreateHistogram" +
            "\nupdateImageLabel";

    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void applyTestGreenComponent() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    view.setComboBox("green-component");
    images.put("originalImage", image);
    images.put("newImage", image);
    controller.apply();
    String expected = "\ngetComboBoxSelectedItem" +
            "\ngreenComponent" +
            "\ncreateHistogram" +
            "\nupdateImageLabel";

    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void applyTestBlueComponent() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    view.setComboBox("blue-component");
    images.put("originalImage", image);
    images.put("newImage", image);
    controller.apply();
    String expected = "\ngetComboBoxSelectedItem" +
            "\nblueComponent" +
            "\ncreateHistogram" +
            "\nupdateImageLabel";

    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void applyTestFlipvertical() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    view.setComboBox("flip-vertical");
    images.put("originalImage", image);
    images.put("newImage", image);
    controller.apply();
    String expected = "\ngetComboBoxSelectedItem" +
            "\nflipVertical" +
            "\ncreateHistogram" +
            "\nupdateImageLabel";

    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void applyTestHortizontal() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    view.setComboBox("flip-horizontal");
    images.put("originalImage", image);
    images.put("newImage", image);
    controller.apply();
    String expected = "\ngetComboBoxSelectedItem" +
            "\nflipHorizontal" +
            "\ncreateHistogram" +
            "\nupdateImageLabel";

    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void applyTestBlur() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    view.setComboBox("blur");
    images.put("originalImage", image);
    images.put("newImage", image);
    controller.apply();
    String expected = "\ngetComboBoxSelectedItem" +
            "\nblur" +
            "\ncreateHistogram" +
            "\nupdateImageLabel";

    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void applyTestSharpen() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    view.setComboBox("sharpen");
    images.put("originalImage", image);
    images.put("newImage", image);
    controller.apply();
    String expected = "\ngetComboBoxSelectedItem" +
            "\nsharpen" +
            "\ncreateHistogram" +
            "\nupdateImageLabel";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void applyTestSepia() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    view.setComboBox("sepia");
    images.put("originalImage", image);
    images.put("newImage", image);
    controller.apply();
    String expected = "\ngetComboBoxSelectedItem" +
            "\nsepia" +
            "\ncreateHistogram" +
            "\nupdateImageLabel";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void applyTestGreyscale() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    view.setComboBox("greyscale");
    images.put("originalImage", image);
    images.put("newImage", image);
    controller.apply();
    String expected = "\ngetComboBoxSelectedItem" +
            "\nluma" +
            "\ncreateHistogram" +
            "\nupdateImageLabel";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void applyTestColroCorrect() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    view.setComboBox("color-correct");
    images.put("originalImage", image);
    images.put("newImage", image);
    controller.apply();
    String expected = "\ngetComboBoxSelectedItem" +
            "\ncolorCorrect" +
            "\ncreateHistogram" +
            "\nupdateImageLabel";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void splitModeImageDoesNotExists() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    controller.splitMode(true);
    String expected = "\nPlease load an image first";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void splitModeImageDoesExists() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    images.put("originalImage", image);
    images.put("newImage", image);
    images.put("splitImage", image);
    controller.splitMode(true);
    String expected = "\ntrue" +
            "\n50.0" +
            "\ncreateHistogram" +
            "\nupdateImageLabel";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void splitModeImageDoesExistsNoNewImage() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    images.put("originalImage", image);
    images.put("splitImage", image);
    controller.splitMode(true);
    String expected = "\ntrue" +
            "\ncreateHistogram" +
            "\nupdateImageLabel";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void compressImageDoesNotExist() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    controller.applyCompress();
    String expected = "\nPlease load an image first";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void compressImageDoesExistsNoCompressValue() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    images.put("originalImage", image);
    controller.applyCompress();
    String expected = "\nPlease enter a number between 1 and 100.";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void compressImageDoesExists() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    view.setCompressValue(60);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    images.put("originalImage", image);
    images.put("newImage", image);
    controller.applyCompress();
    String expected = "\n60.0" +
            "\ncreateHistogram" +
            "\nupdateImageLabel";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void confirmTransformationImageDoesNotExists() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    controller.saveTransformation();
    String expected = "\nPlease load an image first";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void confirmTransformationImageDoesExists() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    images.put("originalImage", image);
    images.put("newImage",image);
    controller.saveTransformation();
    String expected = "\ncreateHistogram" +
            "\nupdateImageLabel" +
            "\ntrue";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void setSplitImageDoesNotExists() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    controller.saveTransformation();
    String expected = "\nPlease load an image first";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void setSplitImageDoesExistsDefaultSplit() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    images.put("originalImage", image);
    images.put("newImage",image);
    controller.setSplitPercentage();
    String expected = "\ngetSplit" +
            "\ngetSplit" +
            "\n50.0" +
            "\ncreateHistogram" +
            "\nupdateImageLabel";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void setSplitImageDoesExistsSetSplit() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    view.setSplitValue("60");
    images.put("originalImage", image);
    images.put("newImage",image);
    images.put("splitImage",image);
    controller.setSplitPercentage();
    String expected = "\ngetSplit" +
            "\ngetSplit" +
            "\n60.0" +
            "\ncreateHistogram" +
            "\nupdateImageLabel";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void adjustLevelNoBMW() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    images.put("originalImage", image);
    images.put("newImage",image);
    images.put("splitImage",image);
    controller.adjustLevel();
    String expected = "\nnull" +
            "\nPlease enter valid b, m, w values";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void adjustLevelOutofRange() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    view.setBMW("-1", "256", "333");
    images.put("originalImage", image);
    images.put("newImage",image);
    images.put("splitImage",image);
    controller.adjustLevel();
    String expected = "\n-1" +
            "\n256" +
            "\n333" +
            "\nb, m, w values should be in the range 0 - 255";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void adjustLevelNotAscending() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    view.setBMW("100", "50", "25");
    images.put("originalImage", image);
    images.put("newImage",image);
    images.put("splitImage",image);
    controller.adjustLevel();
    String expected = "\n100" +
            "\n50" +
            "\n25" +
            "\nb, m, w values should be in ascending order";
    assertEquals(expected, mockLog.toString());
  }

  @Test
  public void adjustLevelValidTest() {
    StringBuilder mockLog = new StringBuilder();
    MockViewGUI view = new MockViewGUI(mockLog);
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    int red[][] = null;
    int green[][] = null;
    int blue[][] = null;
    MockModel image = new MockModel(mockLog, red, green, blue);
    Features controller = new ControllerProGUI(view, images, imageCreator);
    view.setBMW("25", "50", "100");
    images.put("originalImage", image);
    images.put("newImage",image);
    images.put("splitImage",image);
    controller.adjustLevel();
    String expected = "\n25" +
            "\n50" +
            "\n100" +
            "\ncreateHistogram" +
            "\nupdateImageLabel";
    assertEquals(expected, mockLog.toString());
  }
}
