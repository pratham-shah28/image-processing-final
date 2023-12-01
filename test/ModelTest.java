import org.junit.Test;

import model.Image;
import model.ImageCreator;
import model.ImageCreatorImpl;

import static org.junit.Assert.assertEquals;

/**
 * This is a JUnit test class to test the ImageImpl class.
 */
public class ModelTest {

  @Test
  public void brightenTest() {
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

    int[][] redExpected = new int[][]{
            {255, 255, 255},
            {27, 255, 255},
            {21, 255, 29},
            {27, 255, 39}
    };

    int[][] greenExpected = new int[][]{
            {21, 255, 11},
            {255, 255, 255},
            {21, 11, 255},
            {27, 255, 39}
    };

    int[][] blueExpected = new int[][]{
            {21, 255, 255},
            {27, 255, 21},
            {255, 11, 255},
            {27, 255, 255}
    };
    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.brighten(10);

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImage.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void redComponentTest() {
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

    int[][] redExpected = new int[][]{
            {255, 255, 255},
            {17, 255, 255},
            {11, 255, 19},
            {17, 255, 29}
    };

    int[][] greenExpected = new int[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };

    int[][] blueExpected = new int[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };
    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.redComponent();

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImage.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void greenComponentTest() {
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

    int[][] redExpected = new int[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };

    int[][] greenExpected = new int[][]{
            {11, 255, 1},
            {255, 255, 255},
            {11, 1, 255},
            {17, 255, 29}
    };

    int[][] blueExpected = new int[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };
    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.greenComponent();

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImage.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void blueComponentTest() {
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

    int[][] redExpected = new int[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };

    int[][] greenExpected = new int[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };

    int[][] blueExpected = new int[][]{
            {11, 255, 255},
            {17, 255, 11},
            {255, 1, 255},
            {17, 255, 255}
    };
    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.blueComponent();

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImage.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void intensityTest() {
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

    int[][] redExpected = new int[][]{
            {92, 255, 170},
            {96, 255, 173},
            {92, 85, 176},
            {17, 255, 104}
    };

    int[][] greenExpected = new int[][]{
            {92, 255, 170},
            {96, 255, 173},
            {92, 85, 176},
            {17, 255, 104}
    };

    int[][] blueExpected = new int[][]{
            {92, 255, 170},
            {96, 255, 173},
            {92, 85, 176},
            {17, 255, 104}
    };
    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.intensity();

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImage.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void valueTest() {
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

    int[][] redExpected = new int[][]{
            {255, 255, 255},
            {255, 255, 255},
            {255, 255, 255},
            {17, 255, 255}
    };

    int[][] greenExpected = new int[][]{
            {255, 255, 255},
            {255, 255, 255},
            {255, 255, 255},
            {17, 255, 255}
    };

    int[][] blueExpected = new int[][]{
            {255, 255, 255},
            {255, 255, 255},
            {255, 255, 255},
            {17, 255, 255}
    };
    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.value();

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImage.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void lumaTest() {
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

    int[][] redExpected = new int[][]{
            {62, 254, 73},
            {187, 254, 237},
            {28, 55, 204},
            {17, 254, 45}
    };

    int[][] greenExpected = new int[][]{
            {62, 254, 73},
            {187, 254, 237},
            {28, 55, 204},
            {17, 254, 45}
    };

    int[][] blueExpected = new int[][]{
            {62, 254, 73},
            {187, 254, 237},
            {28, 55, 204},
            {17, 254, 45}
    };
    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.luma();

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImage.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void sepiaTest() {
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

    int[][] redExpected = new int[][]{
            {110, 255, 149},
            {205, 255, 255},
            {60, 101, 251},
            {22, 255, 81}
    };

    int[][] greenExpected = new int[][]{
            {98, 255, 132},
            {183, 255, 255},
            {54, 89, 224},
            {20, 255, 72}
    };

    int[][] blueExpected = new int[][]{
            {76, 238, 103},
            {143, 238, 206},
            {42, 70, 174},
            {15, 238, 56}
    };
    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.sepia();

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImage.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void combine() throws Exception {
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

    int[][] redExpected = new int[][]{
            {255, 255, 255},
            {17, 255, 255},
            {11, 255, 19},
            {17, 255, 29}
    };

    int[][] greenExpected = new int[][]{
            {11, 255, 1},
            {255, 255, 255},
            {11, 1, 255},
            {17, 255, 29}
    };

    int[][] blueExpected = new int[][]{
            {11, 255, 255},
            {17, 255, 11},
            {255, 1, 255},
            {17, 255, 255}
    };
    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImageRed = imgCreator.createModelImpl(red, red, red);
    Image inputImageGreen = imgCreator.createModelImpl(green, green, green);
    Image inputImageBlue = imgCreator.createModelImpl(blue, blue, blue);
    Image actualImage = inputImageRed.combine(inputImageRed, inputImageGreen, inputImageBlue);

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImage.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void combineException() throws Exception {
    int[][] red = new int[][]{
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

    int[][] redExpected = new int[][]{
            {255, 255, 255},
            {17, 255, 255},
            {11, 255, 19},
            {17, 255, 29}
    };

    int[][] greenExpected = new int[][]{
            {11, 255, 1},
            {255, 255, 255},
            {11, 1, 255},
            {17, 255, 29}
    };

    int[][] blueExpected = new int[][]{
            {11, 255, 255},
            {17, 255, 11},
            {255, 1, 255},
            {17, 255, 255}
    };
    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImageRed = imgCreator.createModelImpl(red, red, red);
    Image inputImageGreen = imgCreator.createModelImpl(green, green, green);
    Image inputImageBlue = imgCreator.createModelImpl(blue, blue, blue);
    Image actualImage = inputImageRed.combine(inputImageRed, inputImageGreen, inputImageBlue);

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImage.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void flipHorizontalTest() {
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

    int[][] redExpected = new int[][]{
            {255, 0, 255},
            {255, 0, 17},
            {19, 0, 11},
            {29, 0, 17}
    };

    int[][] greenExpected = new int[][]{
            {1, 0, 11},
            {255, 0, 255},
            {255, 0, 11},
            {29, 0, 17}
    };

    int[][] blueExpected = new int[][]{
            {255, 0, 11},
            {11, 0, 17},
            {255, 0, 255},
            {255, 0, 17}
    };
    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.flipHorizontal();

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImage.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void flipVerticalTest() {
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

    int[][] redExpected = new int[][]{
            {17, 255, 29},
            {11, 255, 19},
            {17, 255, 255},
            {255, 255, 255}
    };

    int[][] greenExpected = new int[][]{
            {17, 255, 29},
            {11, 1, 255},
            {255, 255, 255},
            {11, 255, 1}
    };

    int[][] blueExpected = new int[][]{
            {17, 255, 255},
            {255, 1, 255},
            {17, 255, 11},
            {11, 255, 255}
    };
    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.flipVertical();

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImage.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void blurTest() {
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

    int[][] redExpected = new int[][]{
            {113, 176, 143},
            {101, 195, 161},
            {70, 151, 104},
            {53, 103, 57}
    };

    int[][] greenExpected = new int[][]{
            {82, 129, 79},
            {114, 176, 143},
            {68, 132, 131},
            {37, 86, 71}
    };

    int[][] blueExpected = new int[][]{
            {52, 130, 112},
            {85, 147, 114},
            {100, 146, 129},
            {68, 129, 127}
    };
    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.blur();

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImage.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void sharpenTest() {
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

    int[][] redExpected = new int[][]{
            {255, 255, 255},
            {171, 255, 255},
            {77, 255, 180},
            {75, 255, 91}
    };

    int[][] greenExpected = new int[][]{
            {136, 255, 125},
            {255, 255, 255},
            {106, 255, 255},
            {0, 237, 57}
    };

    int[][] blueExpected = new int[][]{
            {45, 255, 255},
            {80, 255, 165},
            {255, 255, 255},
            {45, 255, 255}
    };
    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.sharpen();

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImage.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void applySplitTest() {
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

    int[][] redExpected = new int[][]{
            {255, 255, 255},
            {171, 255, 255},
            {77, 255, 255},
            {75, 255, 91}
    };

    int[][] greenExpected = new int[][]{
            {136, 255, 0},
            {255, 255, 255},
            {106, 255, 0},
            {0, 237, 57}
    };

    int[][] blueExpected = new int[][]{
            {45, 255, 0},
            {80, 255, 165},
            {255, 255, 0},
            {45, 255, 255}
    };
    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.sharpen();
    Image actualImageNew = actualImage.applySplit(inputImage, 50);

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImageNew.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImageNew.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImageNew.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void applySplitTestFull() {
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

    int[][] redExpected = new int[][]{
            {255, 255, 255},
            {171, 255, 255},
            {77, 255, 180},
            {75, 255, 91}
    };

    int[][] greenExpected = new int[][]{
            {136, 255, 125},
            {255, 255, 255},
            {106, 255, 255},
            {0, 237, 57}
    };

    int[][] blueExpected = new int[][]{
            {45, 255, 255},
            {80, 255, 165},
            {255, 255, 255},
            {45, 255, 255}
    };
    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.sharpen();
    Image actualImageNew = actualImage.applySplit(inputImage, 100);

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImage.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void applySplitTestZero() {
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

    int[][] redExpected = new int[][]{
            {255, 255, 255},
            {171, 255, 255},
            {255, 255, 19},
            {75, 255, 29}
    };

    int[][] greenExpected = new int[][]{
            {0, 255, 1},
            {255, 255, 255},
            {0, 1, 255},
            {0, 255, 29}
    };

    int[][] blueExpected = new int[][]{
            {0, 255, 255},
            {80, 255, 11},
            {0, 1, 255},
            {45, 255, 255}
    };
    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.sharpen();
    Image actualImageNew = actualImage.applySplit(inputImage, 0);

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImageNew.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImageNew.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImageNew.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void sharpenTestSmallerThanKernel() {
    int[][] red = new int[][]{
            {255, 0},
            {0, 0}
    };

    int[][] green = new int[][]{
            {0, 255},
            {0, 0}
    };

    int[][] blue = new int[][]{
            {0, 0},
            {255, 0}
    };
    int[][] redExpected = new int[][]{
            {255, 63},
            {63, 63}
    };

    int[][] greenExpected = new int[][]{
            {63, 255},
            {63, 63}
    };

    int[][] blueExpected = new int[][]{
            {63, 63},
            {255, 63}
    };


    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.sharpen();

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImage.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void blurTestSmallerThanKernel() {
    int[][] red = new int[][]{
            {255, 0},
            {0, 0}
    };

    int[][] green = new int[][]{
            {0, 255},
            {0, 0}
    };

    int[][] blue = new int[][]{
            {0, 0},
            {255, 0}
    };
    int[][] redExpected = new int[][]{
            {63, 31},
            {31, 15}
    };

    int[][] greenExpected = new int[][]{
            {31, 63},
            {15, 31}
    };

    int[][] blueExpected = new int[][]{
            {31, 15},
            {63, 31}
    };

    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.blur();

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImage.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void sharpenTestBiggerThanKernel() {
    int[][] red = new int[][]{
            {255, 255, 255, 255, 253, 234, 195, 137, 62, 0},
            {248, 249, 251, 254, 251, 235, 200, 150, 96, 62},
            {223, 225, 233, 244, 249, 240, 215, 181, 150, 137},
            {178, 185, 204, 228, 244, 246, 234, 215, 200, 195},
            {114, 131, 167, 207, 235, 247, 246, 240, 235, 234},
            {32, 77, 132, 181, 216, 235, 244, 249, 251, 253},
            {0, 34, 99, 145, 181, 207, 228, 244, 254, 255},
            {0, 21, 66, 99, 132, 167, 204, 233, 251, 255},
            {2, 16, 21, 34, 77, 131, 185, 225, 249, 255},
            {6, 2, 0, 0, 32, 114, 178, 223, 248, 255}
    };

    int[][] green = new int[][]{
            {0, 71, 140, 196, 234, 253, 255, 255, 255, 255},
            {0, 71, 140, 196, 234, 251, 254, 251, 249, 248},
            {0, 71, 141, 197, 233, 247, 243, 232, 224, 221},
            {0, 71, 141, 198, 232, 240, 226, 202, 182, 174},
            {0, 70, 142, 198, 231, 235, 212, 174, 137, 121},
            {0, 70, 142, 198, 231, 235, 212, 174, 137, 121},
            {0, 70, 141, 198, 232, 240, 226, 202, 182, 174},
            {0, 70, 141, 197, 233, 247, 243, 232, 224, 221},
            {0, 70, 140, 196, 234, 251, 254, 251, 249, 248},
            {0, 70, 140, 196, 234, 253, 255, 255, 255, 255}
    };

    int[][] blue = new int[][]{
            {0, 63, 138, 195, 234, 252, 255, 255, 255, 255},
            {63, 97, 151, 200, 235, 252, 255, 255, 255, 255},
            {140, 153, 182, 215, 241, 254, 255, 255, 255, 255},
            {198, 203, 216, 233, 248, 255, 255, 255, 255, 255},
            {231, 232, 237, 243, 249, 252, 253, 253, 252, 252},
            {231, 231, 232, 232, 233, 234, 234, 234, 234, 234},
            {199, 199, 198, 197, 196, 196, 196, 197, 197, 197},
            {142, 142, 142, 141, 140, 141, 141, 143, 143, 144},
            {70, 70, 70, 70, 70, 72, 75, 78, 80, 81},
            {0, 0, 0, 0, 0, 0, 0, 10, 20, 23}
    };
    int[][] redExpected = new int[][]{
            {255, 255, 255, 255, 255, 255, 221, 131, 38, 0},
            {255, 255, 255, 255, 255, 255, 255, 209, 134, 38},
            {255, 255, 253, 255, 255, 255, 231, 177, 209, 131},
            {198, 255, 217, 254, 255, 255, 255, 231, 255, 221},
            {100, 175, 171, 236, 255, 255, 255, 255, 255, 255},
            {0, 65, 125, 211, 255, 255, 255, 255, 255, 255},
            {0, 1, 92, 170, 211, 236, 254, 255, 255, 255},
            {0, 0, 49, 92, 125, 171, 217, 253, 255, 255},
            {0, 0, 0, 1, 65, 175, 255, 255, 255, 255},
            {0, 0, 0, 0, 0, 100, 198, 255, 255, 255}
    };

    int[][] greenExpected = new int[][]{
            {0, 58, 169, 240, 255, 255, 255, 255, 255, 255},
            {0, 86, 242, 255, 255, 255, 255, 255, 255, 255},
            {0, 36, 164, 235, 255, 255, 255, 248, 255, 255},
            {0, 36, 165, 239, 255, 255, 238, 188, 255, 171},
            {0, 35, 166, 240, 255, 255, 210, 129, 164, 85},
            {0, 34, 166, 240, 255, 255, 210, 129, 164, 85},
            {0, 34, 165, 239, 255, 255, 238, 188, 255, 171},
            {0, 34, 164, 236, 255, 255, 255, 248, 255, 255},
            {0, 85, 242, 255, 255, 255, 255, 255, 255, 255},
            {0, 57, 169, 240, 255, 255, 255, 255, 255, 255}
    };

    int[][] blueExpected = new int[][]{
            {0, 39, 131, 220, 255, 255, 255, 255, 255, 255},
            {40, 137, 210, 255, 255, 255, 255, 255, 255, 255},
            {137, 217, 180, 227, 255, 255, 255, 255, 255, 255},
            {232, 255, 239, 255, 255, 255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255, 255, 255, 255, 255, 255},
            {255, 255, 255, 255, 255, 255, 255, 255, 255, 255},
            {250, 255, 241, 237, 232, 229, 228, 228, 255, 238},
            {174, 247, 166, 164, 162, 162, 162, 163, 247, 171},
            {57, 86, 34, 34, 34, 35, 41, 50, 115, 80},
            {0, 0, 0, 0, 0, 0, 0, 0, 7, 3}
    };

    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.sharpen();

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImage.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void blurTestBiggerThanKernel() {
    int[][] red = new int[][]{
            {255, 255, 255, 255, 253, 234, 195, 137, 62, 0},
            {248, 249, 251, 254, 251, 235, 200, 150, 96, 62},
            {223, 225, 233, 244, 249, 240, 215, 181, 150, 137},
            {178, 185, 204, 228, 244, 246, 234, 215, 200, 195},
            {114, 131, 167, 207, 235, 247, 246, 240, 235, 234},
            {32, 77, 132, 181, 216, 235, 244, 249, 251, 253},
            {0, 34, 99, 145, 181, 207, 228, 244, 254, 255},
            {0, 21, 66, 99, 132, 167, 204, 233, 251, 255},
            {2, 16, 21, 34, 77, 131, 185, 225, 249, 255},
            {6, 2, 0, 0, 32, 114, 178, 223, 248, 255}
    };

    int[][] green = new int[][]{
            {0, 71, 140, 196, 234, 253, 255, 255, 255, 255},
            {0, 71, 140, 196, 234, 251, 254, 251, 249, 248},
            {0, 71, 141, 197, 233, 247, 243, 232, 224, 221},
            {0, 71, 141, 198, 232, 240, 226, 202, 182, 174},
            {0, 70, 142, 198, 231, 235, 212, 174, 137, 121},
            {0, 70, 142, 198, 231, 235, 212, 174, 137, 121},
            {0, 70, 141, 198, 232, 240, 226, 202, 182, 174},
            {0, 70, 141, 197, 233, 247, 243, 232, 224, 221},
            {0, 70, 140, 196, 234, 251, 254, 251, 249, 248},
            {0, 70, 140, 196, 234, 253, 255, 255, 255, 255}
    };

    int[][] blue = new int[][]{
            {0, 63, 138, 195, 234, 252, 255, 255, 255, 255},
            {63, 97, 151, 200, 235, 252, 255, 255, 255, 255},
            {140, 153, 182, 215, 241, 254, 255, 255, 255, 255},
            {198, 203, 216, 233, 248, 255, 255, 255, 255, 255},
            {231, 232, 237, 243, 249, 252, 253, 253, 252, 252},
            {231, 231, 232, 232, 233, 234, 234, 234, 234, 234},
            {199, 199, 198, 197, 196, 196, 196, 197, 197, 197},
            {142, 142, 142, 141, 140, 141, 141, 143, 143, 144},
            {70, 70, 70, 70, 70, 72, 75, 78, 80, 81},
            {0, 0, 0, 0, 0, 0, 0, 10, 20, 23}
    };
    int[][] redExpected = new int[][]{
            {142, 189, 190, 190, 186, 172, 144, 103, 57, 21},
            {182, 245, 247, 250, 247, 231, 198, 153, 105, 57},
            {164, 222, 231, 240, 244, 236, 213, 182, 153, 103},
            {132, 184, 203, 224, 239, 241, 230, 213, 198, 144},
            {87, 134, 167, 202, 228, 240, 241, 236, 231, 172},
            {42, 84, 130, 175, 208, 228, 239, 244, 247, 186},
            {14, 47, 95, 140, 175, 202, 224, 240, 250, 190},
            {6, 27, 60, 95, 130, 167, 203, 231, 247, 190},
            {4, 14, 27, 47, 84, 134, 184, 222, 245, 189},
            {3, 4, 6, 14, 42, 87, 132, 164, 182, 142}
    };

    int[][] greenExpected = new int[][]{
            {13, 52, 102, 143, 171, 186, 190, 190, 189, 142},
            {17, 70, 136, 191, 228, 246, 250, 247, 244, 182},
            {17, 70, 137, 191, 227, 241, 239, 229, 221, 162},
            {17, 70, 137, 192, 225, 234, 224, 203, 184, 131},
            {17, 70, 137, 192, 224, 229, 212, 181, 152, 104},
            {17, 70, 137, 192, 224, 229, 212, 181, 152, 104},
            {17, 70, 137, 192, 225, 234, 224, 203, 184, 131},
            {17, 70, 137, 191, 227, 241, 239, 229, 221, 162},
            {17, 70, 136, 191, 228, 246, 250, 247, 244, 182},
            {13, 52, 102, 143, 171, 186, 190, 190, 189, 142}
    };

    int[][] blueExpected = new int[][]{
            {21, 58, 104, 144, 172, 186, 190, 191, 191, 143},
            {58, 106, 154, 199, 231, 249, 254, 255, 255, 191},
            {105, 155, 183, 213, 238, 250, 254, 255, 255, 191},
            {145, 200, 213, 230, 244, 252, 254, 254, 254, 190},
            {167, 225, 230, 237, 243, 247, 248, 248, 248, 186},
            {167, 223, 224, 226, 227, 228, 229, 229, 229, 171},
            {144, 192, 192, 191, 191, 191, 192, 192, 192, 144},
            {103, 138, 137, 137, 136, 137, 138, 139, 140, 105},
            {52, 70, 70, 70, 70, 71, 73, 77, 80, 61},
            {13, 17, 17, 17, 17, 18, 20, 24, 29, 23}
    };


    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.blur();

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImage.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void testGetHeight() {
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

    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    assertEquals(red[0].length, inputImage.getHeight());
  }

  @Test
  public void testGetWidth() {
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

    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    assertEquals(red.length, inputImage.getWidth());
  }

  @Test
  public void testGetRedPixelMatrix() {
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

    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    for (int x = 0; x < inputImage.getWidth(); x++) {
      for (int y = 0; y < inputImage.getHeight(); y++) {
        assertEquals(red[x][y], inputImage.getRedPixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void testGetBluePixelMatrix() {
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

    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    for (int x = 0; x < inputImage.getWidth(); x++) {
      for (int y = 0; y < inputImage.getHeight(); y++) {
        assertEquals(blue[x][y], inputImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void testGetGreenPixelMatrix() {
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

    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    for (int x = 0; x < inputImage.getWidth(); x++) {
      for (int y = 0; y < inputImage.getHeight(); y++) {
        assertEquals(green[x][y], inputImage.getGreenPixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void testCompressSixty() {
    int[][] red = new int[][]{
            {255, 255, 0},
            {17, 255, 255},
            {11, 255, 19},
            {17, 255, 29},
            {17, 255, 29}
    };

    int[][] green = new int[][]{
            {0, 255, 1},
            {255, 255, 255},
            {11, 1, 255},
            {17, 255, 29},
            {11, 1, 255}
    };

    int[][] blue = new int[][]{
            {11, 255, 255},
            {17, 255, 11},
            {255, 1, 255},
            {17, 255, 255},
            {255, 1, 255}
    };

    int[][] redExpected = new int[][]{
            {165, 165, 0},
            {165, 165, 229},
            {44, 255, 38},
            {44, 255, 38},
            {87, 87, 19}
    };

    int[][] greenExpected = new int[][]{
            {0, 195, 131},
            {195, 195, 131},
            {66, 66, 138},
            {196, 196, 138},
            {17, 17, 208}
    };

    int[][] blueExpected = new int[][]{
            {0, 236, 182},
            {0, 236, 182},
            {238, 0, 243},
            {0, 238, 243},
            {128, 128, 255}
    };


    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);

    Image output = inputImage.compress(60);

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], output.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], output.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], output.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void testCompress100() {
    int[][] red = new int[][]{
            {255, 255, 0},
            {17, 255, 255},
            {11, 255, 19},
            {17, 255, 29},
            {17, 255, 29}
    };

    int[][] green = new int[][]{
            {0, 255, 1},
            {255, 255, 255},
            {11, 1, 255},
            {17, 255, 29},
            {11, 1, 255}
    };

    int[][] blue = new int[][]{
            {11, 255, 255},
            {17, 255, 11},
            {255, 1, 255},
            {17, 255, 255},
            {255, 1, 255}
    };

    int[][] redExpected = new int[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };

    int[][] greenExpected = new int[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };

    int[][] blueExpected = new int[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };


    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);

    Image output = inputImage.compress(100);

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], output.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], output.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], output.getBluePixelMatrixElement(x, y));
      }
    }
  }


  @Test
  public void testCompressZeroRi() {

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

    int[][] redExpected = new int[][]{
            {255, 255, 255},
            {17, 255, 255},
            {11, 255, 19},
            {17, 255, 29}
    };

    int[][] greenExpected = new int[][]{
            {11, 255, 1},
            {255, 255, 255},
            {11, 1, 255},
            {17, 255, 29}
    };

    int[][] blueExpected = new int[][]{
            {11, 255, 255},
            {17, 255, 11},
            {255, 1, 255},
            {17, 255, 255}
    };


    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);

    Image output = inputImage.compress(0);

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], output.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], output.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], output.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void colorCorrectTest() {
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

    int[][] redExpected = new int[][]{
            {251, 251, 251},
            {13, 251, 251},
            {7, 251, 15},
            {13, 251, 25}
    };

    int[][] greenExpected = new int[][]{
            {13, 255, 3},
            {255, 255, 255},
            {13, 3, 255},
            {19, 255, 31},
    };

    int[][] blueExpected = new int[][]{
            {13, 255, 255},
            {19, 255, 13},
            {255, 3, 255},
            {19, 255, 255},
    };
    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.colorCorrect();

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImage.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void levelAdjustmentTest() {
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

    int[][] redExpected = new int[][]{
            {255, 255, 255},
            {4, 255, 255},
            {0, 255, 5},
            {4, 255, 12},
    };

    int[][] greenExpected = new int[][]{
            {0, 255, 0},
            {255, 255, 255},
            {0, 0, 255},
            {4, 255, 12},
    };

    int[][] blueExpected = new int[][]{
            {0, 255, 255},
            {4, 255, 0},
            {255, 0, 255},
            {4, 255, 255},
    };
    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.adjustLevels(10, 150, 240);

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        assertEquals(redExpected[x][y], actualImage.getRedPixelMatrixElement(x, y));
        assertEquals(greenExpected[x][y], actualImage.getGreenPixelMatrixElement(x, y));
        assertEquals(blueExpected[x][y], actualImage.getBluePixelMatrixElement(x, y));
      }
    }
  }

  @Test
  public void testHistogramFrequencies() {
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

    int[] expectedRed = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
          0, 0, 0, 0, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7};

    int[] expectedGreen = {0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2,
          0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6};

    int[] expectedBlue = {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2,
          0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7};


    ImageCreator imgCreator = new ImageCreatorImpl();
    Image inputImage = imgCreator.createModelImpl(red, green, blue);
    Image actualImage = inputImage.createHistogram();

    int[] redFrequency = new int[256];
    int[] greenFrequency = new int[256];
    int[] blueFrequency = new int[256];
    for (int i = 0; i < 256; i++) {
      redFrequency[i] = 0;
      greenFrequency[i] = 0;
      blueFrequency[i] = 0;
    }

    for (int x = 0; x < red.length; x++) {
      for (int y = 0; y < red[0].length; y++) {
        int r = red[x][y];
        int g = green[x][y];
        int b = blue[x][y];
        redFrequency[r]++;
        greenFrequency[g]++;
        blueFrequency[b]++;
      }
    }
    for (int x = 0; x < 256; x++) {
      assertEquals(redFrequency[x], expectedRed[x]);
      assertEquals(greenFrequency[x], expectedGreen[x]);
      assertEquals(blueFrequency[x], expectedBlue[x]);
    }
  }
}
