package model;

/**
 * A class to create an Image object.
 */
public class ImageCreatorImpl implements ImageCreator {
  @Override
  public Image createModelImpl(int[][] redPixelMatrix,
                               int[][] greenPixelMatrix,
                               int[][] bluePixelMatrix) {
    return new ImageImpl(redPixelMatrix, greenPixelMatrix, bluePixelMatrix);
  }

}
