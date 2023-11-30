package controller;

import java.util.Map;

import model.Image;
import model.ImageCreator;

/**
 * This is an interface for ControllerUtil class.
 */
public interface ControllerUtilInterface {
  /**
   * This method loads an image present at the given path and creates entry in the Hashmap.
   * @param path the path of the image to be loaded.
   * @param name the name of the image.
   * @param images the hashmap to track images.
   * @param creator imageCreator object to create a ImageImpl object.
   * @return the status string stating whether the image was loaded successfully or not.
   */
  String loadImage(String path, String name, Map<String, Image> images, ImageCreator creator);

  /**
   * This method saves the given image to the path specified by the user.
   * @param path the destination path where image has to be stored.
   * @param img the image to be saved.
   * @return the status string stating whether the image was saved successfully or not.
   */
  String saveImage(String path, Image img);
}
