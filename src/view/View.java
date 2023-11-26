package view;

/**
 * This is the view class which handles what is displayed to the user via command line interface.
 * This class implements the viewInterface.
 */
public class View implements ViewInterface {
  private final String[] commandList;

  /**
   * Constructs the view class and initialized the set of valid commands.
   */
  public View() {

    commandList = new String[]{"load image-path image-name",
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
      "histogram image-name dest-image-name",
      "color-correct image-name dest-image-name",
      "levels-adjust b m w image-name dest-image-name",
      "compress percentage image-name dest-image-name",
      "blur image-name dest-image split p",
      "sharpen image-name dest-image split p",
      "sepia image-name dest-image split p",
      "luma-component image-name dest-image split p",
      "run script-file",
      "save image-path image-name"
    };
  }

  @Override
  public void showOutput(String message) {
    System.out.println(message);
  }

  @Override
  public void welcomeMessage() {
    System.out.println("Welcome");
    System.out.println("-------------------------------------------------------------------------");
    System.out.println("This is an image processing application where users can perform");
    System.out.println("operations on images by using command line interface.");
    System.out.println("Enter 'help' to list all commands and their uses.");
    System.out.println("-------------------------------------------------------------------------");
  }

  @Override
  public void enterCommandPrompt() {
    System.out.println("Enter Command:");
  }

  @Override
  public void imageDoesNotExists() {
    System.out.println("The image you are trying to operate on does not exists.");
  }

  @Override
  public void showCommandList() {
    System.out.println("-------------------------------------------------------------------------");
    System.out.println("Here is the list of operations that are supported " +
            "in our image processing application: ");
    System.out.println();
    for (String s : commandList) {
      System.out.println(s);
    }
    System.out.println("-------------------------------------------------------------------------");
  }

}