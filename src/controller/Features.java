package controller;

import java.io.IOException;

/**
 * This is an interface for all the features provided by the
 * GUI version of the Image Manipulation and Enhancement application.
 */
public interface Features {
  /**
   * This method loads an image in PPM/JPG/PNG formats.
   * If the currently shown image is not saved, the program will prompt the user accordingly.
   *
   * @throws IOException if image cannot be loaded.
   */
  void loadImage() throws IOException;

  /**
   * This method saves the currently displayed image into the local system.
   *
   * @throws IOException if image cannot be saved.
   */
  void saveImage() throws IOException;

  /**
   * This method is associated with a button which when clicked will apply the appropriate
   * transformation on currently visible image based on the selected item from the dropdown list.
   */
  void apply();

  /**
   * This method is associated with a button which when clicked
   * will levels adjust the currently visible image.
   */
  void adjustLevel();

  /**
   * This method is associated with a button which when clicked will set the split percentage
   * for the preview mode.
   */
  void setSplitPercentage();

  /**
   * This method is associated with a button which when clicked
   * will confirm the transformation showed in split mode.
   */
  void confirmTransformation();

  /**
   * This method is associated with a button which when clicked
   * will compress the currently visible image based on the compress factor provided.
   */
  void applyCompress();

  /**
   * This method is associated with a button which when clicked will enable or disable split mode.
   *
   * @param isEnabled true if split mode is enabled.
   */
  void splitMode(boolean isEnabled);
}
