import org.junit.Test;

import model.Image;
import model.ImageImpl;

import static org.junit.Assert.assertEquals;

/**
 * A test class for ImageCreator class.
 */
public class ImageCreatorTest {

  @Test
  public void test() {
    int[][] red = new int[][]{
            {255, 255, 255},
            {17, 255, 255},
            {11, 255, 19},
            {17, 255, 29}
    };

    int[][] green = new int[][]{
            {11, 255, 1},
            {255, 255, 255},
            {11, 1, 255},
            {17, 255, 29}
    };

    int[][] blue = new int[][]{
            {11, 255, 255},
            {17, 255, 11},
            {255, 1, 255},
            {17, 255, 255}
    };

    Image inputImage = new ImageImpl(red, green, blue);

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(red[x][y], inputImage.getRedPixelMatrixElement(x, y));
        assertEquals(green[x][y], inputImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blue[x][y], inputImage.getBluePixelMatrixElement(x, y));
      }
    }
  }
}

