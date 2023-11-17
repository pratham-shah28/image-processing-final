package model;

/**
 * Interface for creating a model Image.
 */
public interface ImageCreator {

  /**
   * Method to create an Image object.
   * @param     redPixelMatrix Red channel of image.
   * @param     greenPixelMatrix Green channel of image.
   * @param     bluePixelMatrix Blue channel of image.
   * @return
   */
  Image createModelImpl(int[][] redPixelMatrix, int[][] greenPixelMatrix, int[][] bluePixelMatrix);

}
