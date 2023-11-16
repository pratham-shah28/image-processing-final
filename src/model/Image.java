package model;

/**
 * This is an interface that represents an Image. An image is represented using its 3 channels i.e.
 * red, green and blue matrices. An image also has operations associated with it.
 */
public interface Image {

  /**
   * This method visualizes red component of an image and discards rest of the channels.
   *
   * @return Red component of an image as an Image object.
   */
  Image redComponent();


  /**
   * This method visualizes green component of an image and discards rest of the channels.
   *
   * @return Green component of an image as an Image object.
   */
  Image greenComponent();


  /**
   * This method visualizes blue component of an image and discards rest of the channels.
   *
   * @return Blue component of an image as an Image object.
   */
  Image blueComponent();


  /**
   * This method visualizes the intensity component of an image.
   * Intensity is the average of the three components for each pixel.
   *
   * @return Intensity component of an image as an Image object.
   */
  Image intensity();


  /**
   * This method visualizes the value component of an image.
   * Value is the maximum value of the three components for each pixel.
   *
   * @return Value component of an image as an Image object.
   */
  Image value();


  /**
   * This method visualizes the luma component of an image.
   * Luma is the weighted sum of each pixel.
   *
   * @return Luma component of an image as an Image object.
   */
  Image luma();


  /**
   * This method brightens the image by the given factor.
   *
   * @param factor int value used to brighten each pixel.
   * @return Brightened version of original image as an Image object.
   */
  Image brighten(int factor);


  /**
   * This method applies sepia tone to an image.
   *
   * @return Sepia-toned version of the given image as an Image object.
   */
  Image sepia();

  /**
   * This method combines three images into one single image. This is generally used to combine
   * images that represent individual channel components.
   *
   * @param redImage   Red component Image for the new image.
   * @param greenImage Green component Image for the new image.
   * @param blueImage  Blue component Image for the new image.
   * @return Combined image as Image object.
   * @throws Exception If sizes are not the same.
   */
  Image combine(Image redImage, Image greenImage, Image blueImage) throws Exception;

  /**
   * This method flips the image horizontally and returns the new image.
   *
   * @return Flipped image as Image object.
   */
  Image flipHorizontal();

  /**
   * This method flips the image vertically and returns the new image.
   *
   * @return Flipped image as Image object.
   */
  Image flipVertical();

  /**
   * This method blurs the input image by applying kernel operations
   * and returns the new Image object.
   *
   * @return Blurred image as Image object.
   */
  Image blur();

  /**
   * This method sharpens the input image by applying kernel operations
   * and returns the new Image object.
   *
   * @return Sharpened image as Image object.
   */
  Image sharpen();

  /**
   * This method compresses the original image by the given factor.
   * Compression reduces the size of an image. under the hood we use haar transformation.
   *
   * @param factor factor by which image should be compressed. Valid values are between 0 and 100.
   * @return compressed version of the original image as an Image object.
   */
  Image compress(double factor);

  /**
   * This method visualizes the histogram for each component as a line graph.
   * Histogram is calculated by finding the number of pixels with value 0, 1, ...255.
   *
   * @return An image that represents the histogram of a given image.
   */
  Image createHistogram();

  /**
   * This method color corrects the given image by calculating the histogram for each component
   * and aligning the histogram peaks of individual channels.
   * We find the "meaningful" peaks by only considering values greater than 10 and lesser than 245
   * in each channel (essentially ignoring peaks in the extremities of the histogram).
   *
   * @return color corrected version of original image as an Image object.
   */
  Image colorCorrect();

  /**
   * This method adjusts the bright, mid and dark regions of an image
   * according to the given b_p, m_p and w_p values.
   *
   * @param b_p the black value.
   * @param m_p the mid value.
   * @param w_p the white value.
   * @return level adjusted version of original image as an Image object.
   */
  Image adjustLevels(int b_p, int m_p, int w_p);


  /**
   * This method gets the pixel value at x,y position from the red channel.
   *
   * @param x x coordinate of the pixel.
   * @param y y coordinate of the pixel.
   * @return Pixel value at that position as an integer.
   */
  int getRedPixelMatrixElement(int x, int y);

  /**
   * This method gets the pixel value at x,y position from the green channel.
   *
   * @param x x coordinate of the pixel.
   * @param y y coordinate of the pixel.
   * @return Pixel value at that position as an integer.
   */
  int getGreenPixelMatrixElement(int x, int y);

  /**
   * This method gets the pixel value at x,y position from the blue channel.
   *
   * @param x x coordinate of the pixel.
   * @param y y coordinate of the pixel.
   * @return Pixel value at that position as an integer.
   */
  int getBluePixelMatrixElement(int x, int y);

  /**
   * This method gets the height of the current image.
   *
   * @return height of the image as an integer.
   */
  int getHeight();

  /**
   * This method gets the width of the current image.
   *
   * @return Width of the image as an integer.
   */
  int getWidth();

  /**
   * This method creates a split image with one side representing original image
   * and other side representing sepia version of the image.
   *
   * @param perc percentage of the width of the image.
   * @return split view image.
   */
  Image sepiaSplit(double perc);

  /**
   * This method creates a split image with one side representing original image
   * and other side representing sharpened version of the image.
   *
   * @param perc percentage of the width of the image.
   * @return split view image.
   */
  Image sharpenSplit(double perc);

  /**
   * This method creates a split image with one side representing original image
   * and other side representing blurred version of the image.
   *
   * @param perc percentage of the width of the image.
   * @return split view image.
   */
  Image blurSplit(double perc);

  /**
   * This method creates a split image with one side representing original image
   * and other side representing value component of the image.
   *
   * @param perc percentage of the width of the image.
   * @return split view image.
   */
  Image valueSplit(double perc);

  /**
   * This method creates a split image with one side representing original image
   * and other side representing luma component of the image.
   *
   * @param perc percentage of the width of the image.
   * @return split view image.
   */
  Image lumaSplit(double perc);

  /**
   * This method creates a split image with one side representing original image
   * and other side representing intensity component of the image.
   *
   * @param perc percentage of the width of the image.
   * @return split view image.
   */
  Image intensitySplit(double perc);

}
