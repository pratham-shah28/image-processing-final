package view;

import java.io.File;

import controller.Features;

/**
 * This interface provides a blueprint for the view class. A view is responsible to show output to
 * the user.
 */
public interface ViewGUIInterface {

  /**
   * This method gets the selected item from the comboBox containing list of operations.
   *
   * @return selected operation in string format.
   */
  String getComboBoxSelectedItem();

  /**
   * This method gets the compression factor given by the user.
   *
   * @return Value in the JTextField for compression.
   */
  int getCompressInput();

  /**
   * This method gets the split percentage for the preview.
   *
   * @return Value in the JTextField for split.
   */
  String getSplit();

  /**
   * This method shows a message using a dialog box.
   *
   * @param s String which is to be displayed in the dialog box.
   */
  void showDialog(String s);

  /**
   * This method updates the image that is being displayed in the GUI.
   *
   * @param image     Image object which is to be displayed in the GUI.
   * @param histogram Histogram for the given image.
   */
  void updateImageLabel(model.Image image, model.Image histogram);

  /**
   * This method gets the b value for level-adjust method.
   *
   * @return b value given in the JTextField.
   */
  String bInput();

  /**
   * This method gets the m value for level-adjust method.
   *
   * @return m value given in the JTextField.
   */
  String mInput();

  /**
   * This method gets the w value for level-adjust method.
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
   * This method displays a dialog box asking the user to save the currently visible image.
   *
   * @return Integer to see if user wants to save or not.
   */
  int saveOption();

  /**
   * This method calls the callback functions based on the button clicked.
   *
   * @param features all the features supported by GUI.
   */
  void addFeatures(Features features);

  /**
   * This method gets the selected image from the JFileChooser.
   *
   * @return the selected image file.
   */
  File loadSelectedImage();

  /**
   * This method gets the selected directory from the JFileChooser.
   *
   * @return the selected directory.
   */
  File selectedDirectory();

  /**
   * This method gets the name of the image from the user using JOptionPane input dialog.
   *
   * @return image name with which the image will be saved.
   */
  String getImageName();
}