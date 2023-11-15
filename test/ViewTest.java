import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import view.ViewInterface;
import view.View;

import static org.junit.Assert.assertEquals;

/**
 * This is a JUnit Test class to test our View class that displays output to the command line.
 */
public class ViewTest {
  ViewInterface view = new View();

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
  public void testCommandList() {
    StringBuilder expected = new StringBuilder();
    expected.append("-------------------------------------------------------------------------");
    expected.append(System.getProperty("line.separator"));
    expected.append("Here is the list of operations that are supported ");
    expected.append("in our image processing application: ");
    expected.append(System.getProperty("line.separator"));
    expected.append(System.getProperty("line.separator"));
    String[] commandList = new String[]{"load image-path image-name",
      "brighten factor image-name dest-image-name",
      "vertical-flip image-name dest-image-name",
      "horizontal-flip image-name dest-image-name",
      "rgb-split image-name dest-image-name-red dest-image-name-green dest-image-name-blue",
      "rgb-combine dest-image-name red-image green-image blue-image",
      "blur image-name dest-image-name",
      "sharpen image-name dest-image-name",
      "value-component image-name dest-image-name",
      "intensity-component image-name dest-image-name",
      "luma-component image-name dest-image-name",
      "red-component image-name dest-image-name",
      "green-component image-name dest-image-name",
      "blue-component image-name dest-image-name",
      "sepia image-name dest-image-name",
      "run script-file",
      "save image-path image-name"
    };
    for (String s : commandList) {
      expected.append(s);
      expected.append(System.getProperty("line.separator"));
    }
    expected.append("-------------------------------------------------------------------------");
    expected.append(System.getProperty("line.separator"));
    expected.append(System.getProperty("line.separator"));
    view.showCommandList();
    assertEquals(expected.toString(), out.toString());
  }

  @Test
  public void showOutputTest() {
    StringBuilder expected = new StringBuilder();
    expected.append("Test");
    expected.append(System.getProperty("line.separator"));
    view.showOutput("Test");
    assertEquals(expected.toString(), out.toString());
  }

  @Test
  public void welcomeMessageTest() {
    StringBuilder expected = new StringBuilder();
    expected.append("Welcome");
    expected.append(System.getProperty("line.separator"));
    expected.append("-------------------------------------------------------------------------");
    expected.append(System.getProperty("line.separator"));
    expected.append("This is an image processing application where users can perform");
    expected.append(System.getProperty("line.separator"));
    expected.append("operations on images by using command line interface.");
    expected.append(System.getProperty("line.separator"));
    expected.append("Enter 'help' to list all commands and their uses.");
    expected.append(System.getProperty("line.separator"));
    expected.append("-------------------------------------------------------------------------");
    expected.append(System.getProperty("line.separator"));
    view.welcomeMessage();
    assertEquals(expected.toString(), out.toString());
  }

  @Test
  public void enterPromptTest() {
    StringBuilder expected = new StringBuilder();
    expected.append("Enter Command:");
    expected.append(System.getProperty("line.separator"));
    view.enterCommandPrompt();
    assertEquals(expected.toString(), out.toString());
  }

  @Test
  public void imageNotExistsTest() {
    StringBuilder expected = new StringBuilder();
    expected.append("The image you are trying to operate on does not exists.");
    expected.append(System.getProperty("line.separator"));
    view.imageDoesNotExists();
    assertEquals(expected.toString(), out.toString());
  }

}