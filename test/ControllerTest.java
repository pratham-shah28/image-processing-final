import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;

import controller.Controller;
import controller.ControllerInterface;
import model.Image;
import model.ImageCreator;
import model.ImageCreatorImpl;
import view.View;
import view.ViewInterface;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class to test the controller.
 */
public class ControllerTest {
  private class MockModel implements Image {
    private StringBuilder log;
    private int[][] red;
    private int[][] green;
    private int[][] blue;

    public MockModel(StringBuilder log, int[][] red, int[][] green, int[][] blue) {
      this.log = log;
      this.red = red;
      this.green = green;
      this.blue = blue;
    }

    @Override
    public Image sepia() {
      return null;
    }

    @Override
    public Image redComponent() {
      return null;
    }

    @Override
    public Image greenComponent() {
      return null;
    }

    @Override
    public Image blueComponent() {
      return null;
    }

    @Override
    public Image intensity() {
      return null;
    }

    @Override
    public Image value() {
      return null;
    }

    @Override
    public Image luma() {
      return null;
    }

    @Override
    public Image brighten(int factor) {
      log.append(factor);
      return null;
    }

    @Override
    public Image combine(Image redImage, Image greenImage, Image blueImage) {
      log.append(redImage);
      log.append(greenImage);
      log.append(blueImage);
      return null;
    }

    @Override
    public Image flipHorizontal() {
      return null;
    }

    @Override
    public Image flipVertical() {
      return null;
    }

    @Override
    public Image blur() {
      return null;
    }

    @Override
    public Image sharpen() {
      return null;
    }

    @Override
    public Image compress(double factor) {
      log.append(factor);
      return null;
    }

    @Override
    public Image createHistogram() {
      return null;
    }

    @Override
    public Image colorCorrect() {
      return null;
    }

    @Override
    public Image adjustLevels(int b_p, int m_p, int w_p) {
      return null;
    }


    @Override
    public int getRedPixelMatrixElement(int x, int y) {
      return 0;
    }

    @Override
    public int getGreenPixelMatrixElement(int x, int y) {
      return 0;
    }

    @Override
    public int getBluePixelMatrixElement(int x, int y) {
      return 0;
    }

    @Override
    public int getHeight() {
      return 0;
    }

    @Override
    public int getWidth() {
      return 0;
    }

    @Override
    public Image applySplit(Image originalImage, double perc) {
      return null;
    }


  }

  private class MockImageCreator implements ImageCreator {

    private StringBuilder mockLog;

    public MockImageCreator(StringBuilder mockLog) {
      this.mockLog = mockLog;
    }

    @Override
    public Image createModelImpl(int[][] redPixelMatrix,
                                 int[][] greenPixelMatrix,
                                 int[][] bluePixelMatrix) {
      return new MockModel(mockLog, redPixelMatrix, greenPixelMatrix, bluePixelMatrix);
    }
  }

  private final ByteArrayOutputStream out = new ByteArrayOutputStream();

  @Before
  public void redirectOut() {
    System.setOut(new PrintStream(out));
  }

  @After
  public void cleanUpOut() {
    System.setOut(null);
  }

  @Test
  public void testImageObjectCreationAfterOperation() throws IOException {

    ViewInterface view = new View();
    String path = new File(".").getCanonicalPath() + "\\test\\Mumbai.jpg";
    String input = "load " + "\"" + path + "\"" + " m\n" +
            "vertical-flip m mVF\n" +
            "horizontal-flip m mHF\n" +
            "sharpen m mSH\n" +
            "blur m mBL\n" +
            "brighten 50 m mBR\n" +
            "brighten -50 m mDR\n" +
            "sepia m mSP\n" +
            "value-component m mVC\n" +
            "intensity-component m mIC\n" +
            "luma-component m mLC\n" +
            "rgb-split m mR mG mB\n" +
            "rgb-combine mC mR mG mB \n" +
            "red-component m mRed\n" +
            "green-component m mGreen\n" +
            "blue-component m mBlue";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    StringBuilder mockLog = new StringBuilder();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
    controller.execute();
    assertTrue(images.containsKey("m"));
    assertTrue(images.containsKey("mVF"));
    assertTrue(images.containsKey("mHF"));
    assertTrue(images.containsKey("mSH"));
    assertTrue(images.containsKey("mBL"));
    assertTrue(images.containsKey("mBR"));
    assertTrue(images.containsKey("mDR"));
    assertTrue(images.containsKey("mSP"));
    assertTrue(images.containsKey("mVC"));
    assertTrue(images.containsKey("mIC"));
    assertTrue(images.containsKey("mR"));
    assertTrue(images.containsKey("mG"));
    assertTrue(images.containsKey("mB"));
    assertTrue(images.containsKey("mRed"));
    assertTrue(images.containsKey("mGreen"));
    assertTrue(images.containsKey("mBlue"));
  }

  @Test
  public void testImageObjectCreationAfterOperation2() throws IOException {
    ViewInterface view = new View();
    String path = new File(".").getCanonicalPath() + "\\test\\Mumbai.jpg";
    String input = "load " + "\"" + path + "\"" + " m\n" +
            "vertical-flip m m\n" +
            "horizontal-flip m m";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    StringBuilder mockLog = new StringBuilder();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
    controller.execute();
    assertTrue(images.containsKey("m"));
  }

  @Test
  public void testSave() throws IOException {
    ViewInterface view = new View();
    String path = new File(".").getCanonicalPath() + "\\test\\Mumbai.jpg";
    String path2 = new File(".").getCanonicalPath() + "\\test\\Mumbai1.jpg";
    String input = "load " + "\"" + path + "\"" + " " + "mumbai" +
            "\nred-component mumbai mRed" +
            "\nsave " + "\"" + path2 + "\"" + " " + "mRed";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    StringBuilder mockLog = new StringBuilder();
    ImageCreator imageCreator = new ImageCreatorImpl();
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
    controller.execute();
    String outputString = out.toString();
    String[] lines = outputString.split(System.lineSeparator());
    String lastLine = "";
    if (lines.length > 0) {
      lastLine = lines[lines.length - 2];
    }
    StringBuilder str = new StringBuilder();
    str.append("Save: Success");
    assertEquals(str.toString(), lastLine);

  }

  @Test
  public void testLoad() throws IOException {

    ViewInterface view = new View();
    String path = new File(".").getCanonicalPath() + "\\test\\Mumbai.jpg";
    String input = "load " + "\"" + path + "\"" + " mumbai";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    StringBuilder mockLog = new StringBuilder();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
    controller.execute();
    assertTrue(images.containsKey("mumbai"));
  }

  @Test
  public void operationBeforeLoad() throws IOException {

    ViewInterface view = new View();
    String input = "red-component mumbai mumbai-red";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    StringBuilder mockLog = new StringBuilder();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
    controller.execute();
    String outputString = out.toString();

    String[] lines = outputString.split(System.lineSeparator());

    String lastLine = "";
    if (lines.length > 0) {
      lastLine = lines[lines.length - 2];
    }
    StringBuilder str = new StringBuilder();
    str.append("The image you are trying to operate on does not exists.");
    assertEquals(str.toString(), lastLine);

  }

  @Test
  public void fileDoesNotExist() throws IOException {

    ViewInterface view = new View();
    String path = new File(".").getCanonicalPath() + "\\test\\Mumbai5.jpg";
    String input = "load " + "\"" + path + "\"" + " mumbai";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    StringBuilder mockLog = new StringBuilder();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
    controller.execute();

    String outputString = out.toString();

    String[] lines = outputString.split(System.lineSeparator());

    String lastLine = "";
    if (lines.length > 0) {
      lastLine = lines[lines.length - 2];
    }
    StringBuilder str = new StringBuilder();
    str.append("Load: Failed: javax.imageio.IIOException: Can't read input file!");
    assertEquals(str.toString(), lastLine);

  }

  @Test
  public void testSaveInvalidFormat() throws IOException {
    ViewInterface view = new View();
    String path = new File(".").getCanonicalPath() + "\\test\\Mumbai.jpg";
    String path2 = new File(".").getCanonicalPath() + "\\test\\Mumbai1.vhvah";
    String input = "load " + "\"" + path + "\"" + " mumbai" +
            "\nred-component mumbai mRed" +
            "\nsave " + "\"" + path2 + "\"" + " mRed";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    StringBuilder mockLog = new StringBuilder();
    ImageCreator imageCreator = new ImageCreatorImpl();
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
    controller.execute();

    String outputString = out.toString();

    String[] lines = outputString.split(System.lineSeparator());

    String lastLine = "";
    if (lines.length > 0) {
      lastLine = lines[lines.length - 2];
    }
    StringBuilder str = new StringBuilder();
    str.append("Invalid format.");
    assertEquals(str.toString(), lastLine);

  }

  @Test
  public void testSaveInvalidPath() throws IOException {
    ViewInterface view = new View();
    String path = new File(".").getCanonicalPath() + "\\test\\Mumbai.jpg";
    String path2 = new File(".").getCanonicalPath() + "\\HVhjvh\\Mumbai1.jpg";
    String input = "load " + "\"" + path + "\"" + " mumbai" +
            "\nred-component mumbai mRed" +
            "\nsave " + "\"" + path2 + "\"" + " " + "mRed";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    StringBuilder mockLog = new StringBuilder();
    ImageCreator imageCreator = new ImageCreatorImpl();
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
    controller.execute();
    String outputString = out.toString();

    String[] lines = outputString.split(System.lineSeparator());

    String lastLine = "";
    if (lines.length > 0) {
      lastLine = lines[lines.length - 2];
    }
    StringBuilder str = new StringBuilder();
    str.append("Invalid destination.");
    assertEquals(str.toString(), lastLine);
  }

  @Test
  public void testRunInvalidFormat() throws IOException {

    ViewInterface view = new View();
    String path = new File(".").getCanonicalPath() + "\\test\\Mumbai.jpg";
    String input = "run " + "\"" + path + "\"";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    StringBuilder mockLog = new StringBuilder();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
    controller.execute();

    String outputString = out.toString();

    String[] lines = outputString.split(System.lineSeparator());

    String lastLine = "";
    if (lines.length > 0) {
      lastLine = lines[lines.length - 2];
    }
    StringBuilder str = new StringBuilder();
    str.append("Run: Failed: java.lang.IllegalArgumentException: Please input text file only.");
    assertEquals(str.toString(), lastLine);

  }

  @Test
  public void testRun() throws IOException {

    ViewInterface view = new View();
    String path = new File(".").getCanonicalPath() + "\\commands.txt";
    String input = "run " + "\"" + path + "\"";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    StringBuilder mockLog = new StringBuilder();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
    controller.execute();

    String outputString = out.toString();

    String[] lines = outputString.split(System.lineSeparator());

    String lastLine = "";
    if (lines.length > 0) {
      lastLine = lines[lines.length - 2];
    }
    StringBuilder str = new StringBuilder();
    str.append("Run: Success");
    assertEquals(str.toString(), lastLine);
  }

  @Test
  public void testCombine() throws IOException {
    StringBuilder mockLog = new StringBuilder();
    ViewInterface view = new View();
    String path = new File(".").getCanonicalPath() + "\\test\\Mumbai.jpg";
    String input = "load " + "\"" + path + "\"" + " " + "a" + "\n"
            + "load " + "\"" + path + "\"" + " " + "b" + "\n"
            + "load " + "\"" + path + "\"" + " " + "c" + "\n"
            + "rgb-combine" + " " + "m" + " " + "a" + " " + "b" + " " + "c";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
    controller.execute();
    StringBuilder expected = new StringBuilder();
    expected.append(images.get("a"));
    expected.append(images.get("b"));
    expected.append(images.get("c"));
    assertEquals(expected.toString(), mockLog.toString());
  }

  @Test
  public void testCallCombineMethod() throws IOException {
    StringBuilder mockLog = new StringBuilder();
    ViewInterface view = new View();
    String path = new File(".").getCanonicalPath() + "\\test\\Mumbai.jpg";
    String input = "load " + "\"" + path + "\"" + " " + "a" + "\n"
            + "load " + "\"" + path + "\"" + " " + "b" + "\n"
            + "load " + "\"" + path + "\"" + " " + "c" + "\n"
            + "rgb-combine" + " " + "m" + " " + "a" + " " + "b" + " " + "c";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
    controller.execute();
    StringBuilder expected = new StringBuilder();
    expected.append(images.get("a"));
    expected.append(images.get("b"));
    expected.append(images.get("c"));
    assertEquals(expected.toString(), mockLog.toString());
  }

  @Test
  public void testCallBrightenMethod() throws IOException {
    StringBuilder mockLog = new StringBuilder();
    ViewInterface view = new View();
    String path = new File(".").getCanonicalPath() + "\\test\\Mumbai.jpg";
    String input = "load " + "\"" + path + "\"" + " " + "a" + "\n"
            + "brighten" + " " + "50" + " " + "a" + " " + "b";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    MockImageCreator imageCreator = new MockImageCreator(mockLog);
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
    controller.execute();
    StringBuilder expected = new StringBuilder();
    expected.append(50);
    assertEquals(expected.toString(), mockLog.toString());
  }

  @Test
  public void testInvalidCommand() throws IOException {
    ViewInterface view = new View();
    String input = "notACommand " + "\"" + "\"" + " mumbai";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    ImageCreator imageCreator = new ImageCreatorImpl();
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
    controller.execute();
    String outputString = out.toString();
    String[] lines = outputString.split(System.lineSeparator());
    String lastLine = "";
    if (lines.length > 0) {
      lastLine = lines[lines.length - 2];
    }
    StringBuilder str = new StringBuilder();
    str.append("Invalid Command. Press 'help' to list the supported commands.");
    assertEquals(str.toString(), lastLine);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerCreation() {
    ViewInterface view = new View();
    String input = "notACommand " + "\"" + "\"" + " mumbai";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = null;
    ImageCreator imageCreator = new ImageCreatorImpl();
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
  }

  @Test
  public void testSaveJPG() throws IOException {
    ViewInterface view = new View();
    String path = new File(".").getCanonicalPath() + "\\test\\Mumbai.jpg";
    String path2 = new File(".").getCanonicalPath() + "\\test\\MumbaiJPGSave.jpg";
    String input = "load " + "\"" + path + "\"" + " mumbai" +
            "\nred-component mumbai mRed" +
            "\nsave " + "\"" + path2 + "\"" + " mRed";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    ImageCreator imageCreator = new ImageCreatorImpl();
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
    controller.execute();

    String outputString = out.toString();

    String[] lines = outputString.split(System.lineSeparator());

    String lastLine = "";
    if (lines.length > 0) {
      lastLine = lines[lines.length - 2];
    }
    StringBuilder str = new StringBuilder();
    str.append("Save: Success");
    assertEquals(str.toString(), lastLine);

  }

  @Test
  public void testLoadJPG() throws IOException {
    ViewInterface view = new View();
    String path = new File(".").getCanonicalPath() + "\\test\\Mumbai.jpg";
    String input = "load " + "\"" + path + "\"" + " mumbai";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    ImageCreator imageCreator = new ImageCreatorImpl();
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
    controller.execute();
    assertTrue(images.containsKey("mumbai"));
  }

  @Test
  public void testSavePNG() throws IOException {
    ViewInterface view = new View();
    String path = new File(".").getCanonicalPath() + "\\test\\Mumbai.png";
    String path2 = new File(".").getCanonicalPath() + "\\test\\MumbaiPNGSave.png";
    String input = "load " + "\"" + path + "\"" + " mumbai" +
            "\nred-component mumbai mRed" +
            "\nsave " + "\"" + path2 + "\"" + " mRed";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    ImageCreator imageCreator = new ImageCreatorImpl();
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
    controller.execute();

    String outputString = out.toString();

    String[] lines = outputString.split(System.lineSeparator());

    String lastLine = "";
    if (lines.length > 0) {
      lastLine = lines[lines.length - 2];
    }
    StringBuilder str = new StringBuilder();
    str.append("Save: Success");
    assertEquals(str.toString(), lastLine);

  }

  @Test
  public void testLoadPNG() throws IOException {
    ViewInterface view = new View();
    String path = new File(".").getCanonicalPath() + "\\test\\Mumbai.png";
    String input = "load " + "\"" + path + "\"" + " mumbai";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    ImageCreator imageCreator = new ImageCreatorImpl();
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
    controller.execute();
    assertTrue(images.containsKey("mumbai"));
  }

  @Test
  public void testSavePPM() throws IOException {
    ViewInterface view = new View();
    String path = new File(".").getCanonicalPath() + "\\test\\P3.ppm";
    String path2 = new File(".").getCanonicalPath() + "\\test\\PPMSave.png";
    String input = "load " + "\"" + path + "\"" + " mumbai" +
            "\nred-component mumbai mRed" +
            "\nsave " + "\"" + path2 + "\"" + " mRed";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    ImageCreator imageCreator = new ImageCreatorImpl();
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
    controller.execute();

    String outputString = out.toString();

    String[] lines = outputString.split(System.lineSeparator());

    String lastLine = "";
    if (lines.length > 0) {
      lastLine = lines[lines.length - 2];
    }
    StringBuilder str = new StringBuilder();
    str.append("Save: Success");
    assertEquals(str.toString(), lastLine);

  }

  @Test
  public void testLoadPPM() throws IOException {

    ViewInterface view = new View();
    String path = new File(".").getCanonicalPath() + "\\test\\P3.ppm";
    String input = "load " + "\"" + path + "\"" + " mumbai";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    ImageCreator imageCreator = new ImageCreatorImpl();
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
    controller.execute();
    assertTrue(images.containsKey("mumbai"));
  }

  @Test
  public void loadFormatASaveFormatB() throws IOException {
    ViewInterface view = new View();
    String path = new File(".").getCanonicalPath() + "\\test\\Mumbai.jpg";
    String path2 = new File(".").getCanonicalPath() + "\\test\\MumbaiFormatPNG.png";
    String input = "load " + "\"" + path + "\"" + " mumbai" +
            "\nred-component mumbai mRed" +
            "\nsave " + "\"" + path2 + "\"" + " mRed";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    ImageCreator imageCreator = new ImageCreatorImpl();
    ControllerInterface controller
            = new Controller(view, in, images, imageCreator);
    controller.execute();

    String outputString = out.toString();

    String[] lines = outputString.split(System.lineSeparator());

    String lastLine = "";
    if (lines.length > 0) {
      lastLine = lines[lines.length - 2];
    }
    StringBuilder str = new StringBuilder();
    str.append("Save: Success");
    assertEquals(str.toString(), lastLine);
  }

  @Test
  public void testLoadPPMInvalidFormat() throws Exception {
    ViewInterface view = new View();
    String path = new File(".").getCanonicalPath() + "\\test\\P2.ppm";
    String input = "load " + "\"" + path + "\"" + " mumbai";
    InputStream in = null;
    in = new ByteArrayInputStream(input.getBytes());
    HashMap<String, Image> images = new HashMap<>();
    ImageCreator imageCreator = new ImageCreatorImpl();
    ControllerInterface controller = new Controller(view, in, images, imageCreator);
    controller.execute();
    String outputString = out.toString();

    String[] lines = outputString.split(System.lineSeparator());

    String lastLine = "";
    if (lines.length > 0) {
      lastLine = lines[lines.length - 2];
    }
    StringBuilder str = new StringBuilder();
    str.append("Invalid PPM file: plain RAW file should begin with P3");
    assertEquals(str.toString(), lastLine);
  }
}
