package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This is the view class which handles what is displayed to the user via command line interface.
 * This class implements the viewInterface.
 */
public class View extends JFrame implements ViewInterface {
  private final String[] commandList;
  private JButton applyButton, loadButton, saveButton, compressButton,
          splitPercButton, popupButton, levelsAdjustButton;
  protected JLabel histogramLabel, imageLabel;
  private JTextField compressInput, splitInput, bInput, mInput, wInput;
  private JComboBox<String> imageOperationList;
  private JToggleButton toggleButton;
  private JScrollPane scrollPane;
  private JPopupMenu popupMenu;

  /**
   * Constructs the view class and initialized the set of valid commands.
   */
  public View() {

    super();
    buildGUI();


    commandList = new String[]{"load image-path image-name",
            "brighten factor image-name dest-image-name",
            "vertical-flip image-name dest-image-name",
            "horizontal-flip image-name dest-image-name",
            "rgb-split image-name dest-image-name-red dest-image-name-green dest-image-name-blue",
            "rgb-combine dest-image-name red-image green-image blue-image",
            "blur image-name dest-image-name",
            "sharpen image-name dest-image-name",
            "value-component image-name dest-image-name",
            "intensity-component image-name dest-image-name",
            "luma-component image-name dest-image-name",
            "red-component image-name dest-image-name",
            "green-component image-name dest-image-name",
            "blue-component image-name dest-image-name",
            "sepia image-name dest-image-name",
            "histogram image-name dest-image-name",
            "color-correct image-name dest-image-name",
            "levels-adjust b m w image-name dest-image-name",
            "compress percentage image-name dest-image-name",
            "blur image-name dest-image split p",
            "sharpen image-name dest-image split p",
            "sepia image-name dest-image split p",
            "luma-component image-name dest-image split p",
            "run script-file",
            "save image-path image-name"
    };
    /*setVisible(true);
    this.pack();*/
  }

  @Override
  public void showOutput(String message) {
    System.out.println(message);
  }

  @Override
  public void welcomeMessage() {
    System.out.println("Welcome");
    System.out.println("-------------------------------------------------------------------------");
    System.out.println("This is an image processing application where users can perform");
    System.out.println("operations on images by using command line interface.");
    System.out.println("Enter 'help' to list all commands and their uses.");
    System.out.println("-------------------------------------------------------------------------");
  }

  @Override
  public void enterCommandPrompt() {
    System.out.println("Enter Command:");
  }

  @Override
  public void imageDoesNotExists() {
    System.out.println("The image you are trying to operate on does not exists.");
  }

  @Override
  public void showCommandList() {
    System.out.println("-------------------------------------------------------------------------");
    System.out.println("Here is the list of operations that are supported " +
            "in our image processing application: ");
    System.out.println();
    for (String s : commandList) {
      System.out.println(s);
    }
    System.out.println("-------------------------------------------------------------------------");
  }

  public JComboBox<String> getComboBox() {
    return imageOperationList;
  }

  @Override
  public JTextField getCompressInput() {
    return compressInput;
  }

  public JTextField getSplit() {
    return splitInput;
  }

  public void showDialog(String s) {
    JOptionPane.showMessageDialog(null, s);
  }

  public int saveOption() {
    int result = JOptionPane.showConfirmDialog(this, "Do you want to save this", "Save", JOptionPane.YES_NO_OPTION);
    return result;
  }

  @Override
  public void setCommandButtonListener(ActionListener actionEvent) {
    imageOperationList.addActionListener(actionEvent);
    applyButton.addActionListener(actionEvent);
    loadButton.addActionListener(actionEvent);
    saveButton.addActionListener(actionEvent);
    compressInput.addActionListener(actionEvent);
    compressButton.addActionListener(actionEvent);
    levelsAdjustButton.addActionListener(actionEvent);
    splitPercButton.addActionListener(actionEvent);
    toggleButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        boolean selected = toggleButton.isSelected();
        if (selected) {
          splitInput.setEditable(true);
          splitPercButton.setEnabled(true);
          toggleButton.setText("Disable Split Mode");
        } else {
          splitInput.setEditable(false);
          splitPercButton.setEnabled(false);
          toggleButton.setText("Enable Split Mode");
        }
      }
    });
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
    // Create an ImageIcon from BufferedImage
    ImageIcon imageIcon = new ImageIcon(imageSave);

    ImageIcon imageIconHistogram = new ImageIcon(imageSaveHistogram);
    // Set the ImageIcon to the JLabel

    histogramLabel.setIcon(imageIconHistogram);
    imageLabel.setIcon(imageIcon);
    // scrollPane.getViewport().setPreferredSize(new Dimension(300, 500));
  }

  private JPopupMenu createPopupMenu() {
    JPopupMenu popupMenu = new JPopupMenu();
    JMenuItem menuItem1 = new JMenuItem("Save");
    JMenuItem menuItem2 = new JMenuItem("Discard");
    popupMenu.add(menuItem1);
    popupMenu.add(menuItem2);
    return popupMenu;
  }

  @Override
  public boolean getSplitMode() {
    return toggleButton.isSelected();
  }

  private static void updateValue(String value) {
    System.out.println("Value changed: " + value);
    // Do something with the updated value, for example, pass it to another method or class.
  }


  private void buildGUI() {
    JPanel leftPanel = new JPanel(new BorderLayout());

    // Upper part of the left pane
    JPanel upperLeftPanel = new JPanel(new GridLayout(0,2,5,10));
    JPanel levelsAdjustPanel = new JPanel(new GridLayout(0,3));
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
    splitPanel.add(splitInput);
    splitPanel.add(splitPercButton);
    splitPanel.add(toggleButton, BorderLayout.NORTH);
    splitPanel.setBorder(leftSplitPanelBorder);
    popupMenu = createPopupMenu();

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

}