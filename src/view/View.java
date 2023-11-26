package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This is the view class which handles what is displayed to the user via command line interface.
 * This class implements the viewInterface.
 */
public class View extends JFrame implements ViewInterface {
  private final String[] commandList;
  private JPanel buttonPanel;

  private JButton commandButton, quitButton;

  private JTextField input;

  private JLabel imageLabel;

  /**
   * Constructs the view class and initialized the set of valid commands.
   */
  public View() {

    super();
    this.setTitle("Image manipulation");
    this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());


    buttonPanel = new JPanel();
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
    buttonPanel.add(quitButton);
    JButton openButton = new JButton("Open Image");
    openButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String imagePath = openImage();
        if (imagePath != null) {
          System.out.println("Selected Image Path: " + imagePath);
        }
      }
    });
    add(openButton, BorderLayout.SOUTH);


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
    setVisible(true);
    this.pack();
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
    commandButton.addActionListener(actionEvent);
  }

  @Override
  public String getTurtleCommand() {
    String command = this.input.getText();
    this.input.setText("");
    return command;
  }

  private String openImage() {
    JFileChooser fileChooser = new JFileChooser();

    // Set file filter to allow only png, jpg, and ppm files
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "png", "jpg", "ppm");
    fileChooser.setFileFilter(filter);

    int result = fileChooser.showOpenDialog(this);

    if (result == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      return selectedFile.getAbsolutePath();
    }

    // Return null if no file was selected
    return null;
  }

}