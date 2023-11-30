package view;

import java.io.File;

import controller.Features;

/**
 * This interface provides a blueprint for the view class. A view is responsible to show output to
 * the user.
 */
public interface ViewGUIInterface {

  /**
   * Returns the comboBox which is the drop menu for operations.
   *
   * @return Returns the dropdown menu for operations.
   */
  String getComboBoxSelectedItem();

  /**
   * Returns the compression factor given by the user.
   *
   * @return Value in the JTextField for compression.
   */
  int getCompressInput();

  /**
   * Returns the split percentage for the preview.
   *
   * @return Value in the JTextField for split.
   */
  String getSplit();

  /**
   * Method to show a message using a dialog box.
   *
   * @param s String which is to be displayed in the dialog box.
   */
  void showDialog(String s);

  /**
   * Method used to display an image in the GUI.
   *
   * @param image     Image object which is to be displayed in the GUI.
   * @param histogram Histogram for the given image.
   */
  void updateImageLabel(model.Image image, model.Image histogram);

  /**
   * Method to get the b value for level-adjust method.
   *
   * @return b value given in the JTextField.
   */
  String bInput();

  /**
   * Method to get the m value for level-adjust method.
   *
   * @return m value given in the JTextField.
   */
  String mInput();

  /**
   * Method to get the w value for level-adjust method.
   *
   * @return w value given in the JTextField.
   */
  String wInput();

  /**
   * Method to update the Split panel in the GUI. If split is toggled off then user can't edit its
   * text field or press its buttons.
   *
   * @param split Condition if split is turned on or not.
   */
  void toggleSet(boolean split);

  /**
   * Method to show the save dialog box.
   *
   * @return Integer to see if user wants to save or not.
   */
  int saveOption();

  void addFeatures(Features features);

  File loadSelectedImage();

  File selectedDirectory();

  String getImageName();
}