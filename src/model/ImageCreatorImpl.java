package model;

/**
 * This class is responsible for creating new image objects.
 * This class implements the ImageCreator interface.
 */
public class ImageCreatorImpl implements ImageCreator {
  @Override
  public Image createModelImpl(int[][] redPixelMatrix,
                               int[][] greenPixelMatrix,
                               int[][] bluePixelMatrix) {
    return new ImageImpl(redPixelMatrix, greenPixelMatrix, bluePixelMatrix);
  }

}
