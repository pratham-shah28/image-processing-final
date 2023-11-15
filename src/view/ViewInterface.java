package view;

/**
 * Interface for text based view class.
 */
public interface ViewInterface {
  public void showOutput(String message);

  public void welcomeMessage();

  public void enterCommandPrompt();

  public void imageDoesNotExists();

  public void showCommandList();
}
