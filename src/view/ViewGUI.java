package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;

/**
 * This is the view class which handles what is displayed to the user via command line interface.
 * This class implements the viewInterface.
 */
public class ViewGUI extends JFrame implements ViewGUIInterface {
  private JButton applyButton, loadButton, saveButton, compressButton,
          splitPercButton, levelsAdjustButton, applyTransformation;
  protected JLabel histogramLabel, imageLabel;
  private JTextField compressInput, splitInput, bInput, mInput, wInput;
  private JComboBox<String> imageOperationList;
  private JToggleButton toggleButton;
  private JScrollPane scrollPane;

  /**
   * Constructs the view GUI class and build the GUI to be displayed to the user..
   */
  public ViewGUI() {
    super();
    buildGUI();
  }

  @Override
  public String getComboBoxSelectedItem() {
    return (String) imageOperationList.getSelectedItem();
  }

  @Override
  public int getCompressInput() {
    int result;
    try {
      result = Integer.parseInt(compressInput.getText());
      return result;
    } catch (NumberFormatException ex) {
      // Display an error message for non-integer input
      showDialog("Please enter a valid integer.");
      return -1;
    }
  }

  @Override
  public String bInput() {
    return bInput.getText();
  }

  @Override
  public String mInput() {
    return mInput.getText();
  }

  @Override
  public String wInput() {
    return wInput.getText();
  }

  @Override
  public String getSplit() {
    return splitInput.getText();
  }

  @Override
  public void showDialog(String s) {
    JOptionPane.showMessageDialog(null, s);
  }

  @Override
  public void updateImageLabel(model.Image image, model.Image histogram) {
    // Read the image using BufferedImage
    int width = image.getWidth();
    int height = image.getHeight();
    int[][] pixelMatrix = new int[width][height];
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        pixelMatrix[x][y] = (image.getRedPixelMatrixElement(x, y) << 16)
                | (image.getGreenPixelMatrixElement(x, y) << 8)
                | image.getBluePixelMatrixElement(x, y);
      }
    }
    BufferedImage imageSave = new BufferedImage(pixelMatrix[0].length, pixelMatrix.length, 5);
    for (int x = 0; x < pixelMatrix.length; x++) {
      for (int y = 0; y < pixelMatrix[0].length; y++) {
        imageSave.setRGB(y, x, pixelMatrix[x][y]);
      }
    }


    int widthHistogram = histogram.getWidth();
    int heightHistogram = histogram.getHeight();
    int[][] pixelMatrixHistogram = new int[widthHistogram][heightHistogram];
    for (int x = 0; x < widthHistogram; x++) {
      for (int y = 0; y < heightHistogram; y++) {
        pixelMatrixHistogram[x][y] = (histogram.getRedPixelMatrixElement(x, y) << 16)
                | (histogram.getGreenPixelMatrixElement(x, y) << 8)
                | histogram.getBluePixelMatrixElement(x, y);
      }
    }
    BufferedImage imageSaveHistogram = new BufferedImage(pixelMatrixHistogram[0].length,
            pixelMatrixHistogram.length, 5);
    for (int x = 0; x < pixelMatrixHistogram.length; x++) {
      for (int y = 0; y < pixelMatrixHistogram[0].length; y++) {
        imageSaveHistogram.setRGB(y, x, pixelMatrixHistogram[x][y]);
      }
    }
    ImageIcon imageIcon = new ImageIcon(imageSave);

    ImageIcon imageIconHistogram = new ImageIcon(imageSaveHistogram);

    histogramLabel.setIcon(imageIconHistogram);
    imageLabel.setIcon(imageIcon);
  }

  private void buildGUI() {
    JPanel leftPanel = new JPanel(new BorderLayout());

    JPanel upperLeftPanel = new JPanel(new GridLayout(0, 2, 5, 10));
    JPanel levelsAdjustPanel = new JPanel(new GridLayout(0, 3));
    JPanel splitPanel = new JPanel();
    Border leftUpperPanelBorder = BorderFactory.createTitledBorder("Operations");
    Border leftSplitPanelBorder = BorderFactory.createTitledBorder("Split Mode");
    Border leftBottomPanelBorder = BorderFactory.createTitledBorder("Histogram");

    // An array of items for the dropdown
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
    applyTransformation = new JButton("Confirm transformation");
    applyTransformation.setEnabled(false);
    splitPanel.add(toggleButton, BorderLayout.NORTH);
    splitPanel.add(splitInput);
    splitPanel.add(splitPercButton);
    splitPanel.add(applyTransformation, BorderLayout.NORTH);
    splitPanel.setBorder(leftSplitPanelBorder);

    JPanel bottomLeftPanel = new JPanel();
    histogramLabel = new JLabel();
    histogramLabel.setPreferredSize(new Dimension(256, 256));
    bottomLeftPanel.add(histogramLabel);
    bottomLeftPanel.setBorder(leftBottomPanelBorder);

    leftPanel.add(upperLeftPanel, BorderLayout.NORTH);
    leftPanel.add(splitPanel, BorderLayout.CENTER);
    leftPanel.add(bottomLeftPanel, BorderLayout.SOUTH);

    JPanel rightPanel = new JPanel(new BorderLayout());
    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    scrollPane = new JScrollPane(imageLabel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    rightPanel.add(scrollPane, BorderLayout.CENTER);

    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
    splitPane.setDividerLocation(300); // Set the initial divider location

    this.setTitle("Graphical Image Manipulation Application");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.getContentPane().add(splitPane);
    this.setSize(800, 600);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }

  @Override
  public void toggleSet(boolean selected) {
    if (selected) {
      splitInput.setEditable(true);
      splitPercButton.setEnabled(true);
      applyTransformation.setEnabled(true);
      toggleButton.setText("Disable Split Mode");
    } else {
      splitInput.setEditable(false);
      splitPercButton.setEnabled(false);
      toggleButton.setText("Enable Split Mode");
      applyTransformation.setEnabled(false);
    }
  }

  @Override
  public int saveOption() {
    return JOptionPane.showConfirmDialog(this, "Do you want to save this",
            "Save", JOptionPane.YES_NO_OPTION);
  }

  @Override
  public void addFeatures(Features features) {
    applyButton.addActionListener(evt -> features.apply());
    loadButton.addActionListener(evt -> {
      try {
        features.loadImage();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    saveButton.addActionListener(evt -> {
      try {
        features.saveImage();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    compressButton.addActionListener(evt -> features.applyCompress());
    levelsAdjustButton.addActionListener(evt -> features.adjustLevel());
    splitPercButton.addActionListener(evt -> features.setSplitPercentage());
    applyTransformation.addActionListener(evt -> features.confirmTransformation());
    toggleButton.addActionListener(evt -> features.splitMode(toggleButton.isSelected()));
  }

  @Override
  public File loadSelectedImage() {
    File selectedFile = null;
    JFileChooser fileChooser = new JFileChooser();

    FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "png", "jpg", "ppm");
    fileChooser.setFileFilter(filter);

    int result = fileChooser.showOpenDialog(this);

    if (result == JFileChooser.APPROVE_OPTION) {
      selectedFile = fileChooser.getSelectedFile();
    }
    return selectedFile;
  }

  @Override
  public File selectedDirectory() {
    File selectedDir = null;
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Pick a Directory to Save Image");
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int userSelection = fileChooser.showSaveDialog(this);
    if (userSelection == JFileChooser.APPROVE_OPTION) {
      selectedDir = fileChooser.getSelectedFile();
    }
    return selectedDir;
  }

  @Override
  public String getImageName() {
    return JOptionPane.showInputDialog("Enter image name with extension");
  }
}