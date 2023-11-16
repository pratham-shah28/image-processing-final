package model;

/**
 * An interface for Image which has all operations for image represented using 3 matrices
 * of each chanel.
 */
public interface Image {

  /**
   * Method to get the red component of an image.
   *
   * @return     Red component of an image as Image object.
   */
  Image redComponent();


  /**
   * Method to get the green component of an image.
   *
   * @return     Green component of an image as Image object.
   */
  Image greenComponent();


  /**
   * Method to get the blue component of an image.
   *
   * @return     Blue component of an image as Image object.
   */
  Image blueComponent();


  /**
   * Method to get the intensity component of an image.
   *
   * @return     Intensity component of an image as Image object.
   */
  Image intensity();


  /**
   * Method to get the value component of an image.
   *
   * @return     Value component of an image as Image object.
   */
  Image value();


  /**
   * Method to get the luma component of an image.
   *
   * @return     Luma component of an image as Image object.
   */
  Image luma();


  /**
   * Method to brighten the image by the given factor.
   * @param     factor Int value used factor to brighten.
   * @return           Brighten image as Image object.
   */
  Image brighten(int factor);


  /**
   * Method to set sepia tone to an image.
   * @return    Image with sepia tone as an Image object.
   */
  Image sepia();

  /**
   * Method to combine 3 images into one.
   * @param redImage      Image for the red component of new image.
   * @param greenImage    Image for the green component of new image.
   * @param blueImage     Image for the blue component of new image.
   * @return              Combined image as Image object.
   * @throws Exception    If sizes are not the same.
   */
  Image combine(Image redImage, Image greenImage, Image blueImage) throws Exception;

  /**
   * Method to flip the image horizontally.
   * @return    Flipped image as Image object.
   */
  Image flipHorizontal();

  /**
   * Method to flip the image verticallu.
   * @return    Flipped image as Image object.
   */
  Image flipVertical();

  /**
   * Method to blur the image.
   * @return    Blurred image as Image object.
   */
  Image blur();

  /**
   * Method to sharpen the image.
   * @return    Sharpened image as Image object.
   */
  Image sharpen();

  Image compress(double factor);

  /**
   *
   * @return
   */
  Image createHistogram();

  /**
   *
   * @return
   */
  Image colorCorrect();

  /**
   *
   * @param b_p
   * @param m_p
   * @param w_p
   * @return
   */
  Image adjustLevels(int b_p, int m_p, int w_p);


  /**
   * Method to get the pixel at x,y position from the red channel.
   *
   * @param x i-th position
   * @param y j-th position.
   * @return Pixel at that position as an integer.
   */
  int getRedPixelMatrixElement(int x, int y);

  /**
   * Method to get the pixel at x,y position from the green channel.
   *
   * @param x i-th position
   * @param y j-th positiin
   * @return Pixel at that position as an integer.
   */
  int getGreenPixelMatrixElement(int x, int y);

  /**
   * Method to get the pixel at x,y position from the blue channel.
   *
   * @param x i-th position
   * @param y j-th positiin
   * @return Pixel at that position as an integer.
   */
  int getBluePixelMatrixElement(int x, int y);

  /**
   * Method to get the height of the image being represented.
   *
   * @return Height as int.
   */
  int getHeight();

  /**
   * Method to get the width of the image being represented.
   *
   * @return Width as int.
   */
  int getWidth();

  Image applySplit(Image originalImage, double perc);



}
