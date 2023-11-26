package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This is the view class which handles what is displayed to the user via command line interface.
 * This class implements the viewInterface.
 */
public class View extends JFrame implements ViewInterface {
  private final String[] commandList;
  private JPanel buttonPanel;

  private JButton commandButton, quitButton, applyButton, loadButton, saveButton;

  private JTextField input;
  private JComboBox<String> comboBox;

  protected JLabel imageLabel;
  /**
   * Constructs the view class and initialized the set of valid commands.
   */
  public View() {

    super();
    /*this.setTitle("Image manipulation");
    this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());*/


    /*buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    this.add(buttonPanel, BorderLayout.SOUTH);

    //input textfield
    input = new JTextField(15);
    buttonPanel.add(input);

    commandButton = new JButton("Execute");
    buttonPanel.add(commandButton);

    quitButton = new JButton("Quit");
    quitButton.addActionListener((ActionEvent e) -> {
      System.exit(0);
    });
    buttonPanel.add(quitButton);*/

    JFrame frame = new JFrame("");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 500);

    // Left panel with buttons
    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new GridLayout(3, 2));
    // Create an array of items for the dropdown
    String[] items = {"red-component", "green-component", "blue-component", "flip-vertical",
            "flip-horizontal", "blur", "sharpen", "sepia", "greyscale", "color-correct"};

    // Create a JComboBox and add the items
    comboBox = new JComboBox<>(items);

    leftPanel.add(comboBox);
    applyButton = new JButton("Apply");
    loadButton = new JButton("Load");
    saveButton = new JButton("Save");
    leftPanel.add(applyButton);
    leftPanel.add(loadButton);
    leftPanel.add(saveButton);

    // Right panel split into two sections (up and down)
    JPanel rightPanel = new JPanel(new BorderLayout());

    // Up section
    JPanel upSection = new JPanel();
    upSection.setBackground(Color.LIGHT_GRAY);
    upSection.add(new JLabel("Up Section"));

    // Down section
    JPanel downSection = new JPanel();
    downSection.setBackground(Color.CYAN);
    downSection.add(new JLabel("Down Section"));

    rightPanel.add(upSection, BorderLayout.NORTH);
    rightPanel.add(downSection, BorderLayout.SOUTH);

    // Main panel containing both left and right panels
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(leftPanel, BorderLayout.WEST);
    mainPanel.add(rightPanel, BorderLayout.CENTER);


    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    imageLabel.setVerticalAlignment(SwingConstants.CENTER);
    mainPanel.add(imageLabel, BorderLayout.CENTER);

    frame.add(mainPanel);
    frame.setVisible(true);

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

  @Override
  public void setCommandButtonListener(ActionListener actionEvent) {
    comboBox.addActionListener(actionEvent);
    applyButton.addActionListener(actionEvent);
    loadButton.addActionListener(actionEvent);
    saveButton.addActionListener(actionEvent);
  }

  @Override
  public String getTurtleCommand() {
    return null;
  }

  @Override
  public JLabel getJlabel() {
    return this.imageLabel;
  }

  @Override
  public void updateImageLabel(String imagePath) {
    try {
      // Read the image using BufferedImage
      BufferedImage bufferedImage = ImageIO.read(new File(imagePath));

      // Create an ImageIcon from BufferedImage
      ImageIcon imageIcon = new ImageIcon(bufferedImage);

      // Set the ImageIcon to the JLabel
      imageLabel.setIcon(imageIcon);

    } catch (IOException ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(null, "Error loading image", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
}