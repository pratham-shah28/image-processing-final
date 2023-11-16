package view;

/**
 * This interface provides a blueprint for the view class. A view is responsible to show output to
 * the user.
 */
public interface ViewInterface {
  /**
   * This method displays the 'message' string to the user via command line interface.
   *
   * @param message the message to be displayed.
   */
  void showOutput(String message);

  /**
   * This method displays the welcome message after the application starts.
   */
  void welcomeMessage();

  /**
   * This method displays a message that prompts the user to enter a command.
   */
  void enterCommandPrompt();

  /**
   * This method when called displays the error message stating image doesn't exist.
   */
  void imageDoesNotExists();

  /**
   * This method displays the list of valid commands supported by the application.
   */
  void showCommandList();
}
