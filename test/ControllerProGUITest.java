import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

import model.Image;
import view.ViewGUIInterface;

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

    public MockViewGUI(StringBuilder log) {
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
    public void setCommandButtonListener(ActionListener actionEvent) {
      imageOperationList.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          log.append("imageOperationList");
        }
      });
      applyButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          log.append("applyButton");
        }
      });
      loadButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          log.append("loadButton");
        }
      });
      saveButton.addActionListener(actionEvent);
      compressInput.addActionListener(actionEvent);
      compressButton.addActionListener(actionEvent);
      levelsAdjustButton.addActionListener(actionEvent);
      splitPercButton.addActionListener(actionEvent);
      applyTransformation.addActionListener(actionEvent);
      toggleButton.addActionListener(actionEvent);
    }

    @Override
    public JComboBox<String> getComboBox() {
      return null;
    }

    @Override
    public JTextField getCompressInput() {
      return null;
    }

    @Override
    public JTextField getSplit() {
      return null;
    }

    @Override
    public void showDialog(String s) {

    }

    @Override
    public void updateImageLabel(Image image, Image histogram) {

    }

    @Override
    public JTextField bInput() {
      return null;
    }

    @Override
    public JTextField mInput() {
      return null;
    }

    @Override
    public JTextField wInput() {
      return null;
    }

    @Override
    public void toggleSet(boolean split) {

    }
  }
}
