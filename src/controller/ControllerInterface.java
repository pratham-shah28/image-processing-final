package controller;

/**
 * This is an interface for the controller class. Controller is responsible managing IO operations
 * and delegating work between model and view.
 */
public interface ControllerInterface {

  /**
   * This is the entry point of our application. This method is called from the main class.
   * This method runs and listens for next user commands until the user enters 'exit'.
   */
  void execute();
}
