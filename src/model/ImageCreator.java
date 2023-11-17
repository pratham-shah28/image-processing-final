package model;

/**
 * This is an interface for Image creator class that is responsible for generating images (factory).
 */
public interface ImageCreator {

  /**
   * This method creates an Image object by taking the three channels as input.
   *
   * @param redPixelMatrix   Red channel of image.
   * @param greenPixelMatrix Green channel of image.
   * @param bluePixelMatrix  Blue channel of image.
   * @return ImageImpl object.
   */
  Image createModelImpl(int[][] redPixelMatrix, int[][] greenPixelMatrix, int[][] bluePixelMatrix);

}
