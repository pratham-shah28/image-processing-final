
# Image Manipulation and Enhancement

This program is a text-based image processing application where users can load different images of "png", "jpg", and "ppm" format, perform operations on them and save the results. In this assignment, we add incremental features to the existing application.


## Design and Architecture
For this assignment, we have followed the MVC (Model, View, Controller) architecture.

Model: It is responsible for the logic of the application, which is to perform different image manipulation operations.

View: It simply displays the application's interface for the user.

Controller: It acts as an intermediary between the Model and the View. It takes input from the user, does the required pre-processing (loading image) before passing it on to the Model. It does the same pre-processing while giving the output (saving the image) according to the user's choice (different format - png, jpg etc). It contains the application's logic for handling user actions and coordinating the flow of data between the Model and the View.
To accomodate new features, we introduced a new version of the controller 'ControllerPro' while keeping the original version the same (backward compatible).

Directory structure:
* src
* MainApp.java
* Model package 
    - Image Interface
    - ImageImpl
    - ImageCreator
    - ImageImpl
* View package
    - ViewInterface
    - View 
* Controller package
    - Controller
    - ControllerInterface
    - ControllerPro

# Explanation:

Model (Represents an image and its operations):

- Image interface:
	* This interface provides a blueprint for an image and its operations. 
- ImageImpl:
	* This class implements the Image interface. 
	* This class in the Model represents an image and its operations. Image is stored using three matrices, One for each of its (Red, Green, Blue) channel.
	* An image has a height and a width.
	* The channel matrices stores values of pixel corresponsding to the value of pixel for that color in the image. 
	* The constructor for this class takes 3 two dimensional arrays, one for each channel.
	* It has getters for getting pixel at a particular position from each channel. It has also getters for height and width of matrices which are useful for operations.
	* Each operation returns a new Image object. 
- ImageCreator interface:
	* This interface provides a blueprint for ImageCreatorImpl which is responsible for creating ImageImpl objects.
- ImageCreatorImpl:
	* This class implements ImageCreator interface.
	* This class is responsible for creating new ImageImpl Object and works as an intermediary between controller and ImageImpl. This is like a fatory that creates Images according to 	  users need (user input commands).

Controller (Bridge between model and view): 

- Controller interface:
	* This interface provides a blueprint to create controllers.
- Controller
	* This class implements controller interface.
 	* This class represents the controller of the application whose reponsibility is the handle the user inputs and delegate work to our Model and View.
	* It is reponsible for loading the images given by the user via command line. When an image is loaded it does the necessary pre-processing to represent the image as 3 matrices of 3 	  channels and creates a new ImageImpl object using ImageCreator.
	* All the images are tracked using a HashMap. For each session it maintains a HashMap 'images' with ImageImpl object as value and Image name (provided by user) as the key. 
	  This not only keeps track of all images loaded by the users but also tracks all the new images generated as a result of the user commands (image operations on loaded images).
	  If the same image is passed as input and output destination by user (via command), the existing image will be overriden.
	* It is also responsible for saving the image to a path given by the user in a particular format. It reads the 3 channels from Image object and writes an image at the destination 	  using the ImageIO library.
	* It is also responsible for command line interface logic. When a user inputs commands, it is validated by the controller and necessary actions are taken (eg: delegating model to perform transformation on an image or asking the view to output something to the command line).

- ControllerPro
    * This class implements the ControllerInterface and extends the original controller class.
    * This represents a new version of the controller that supports new operations and is backward compatible.
    * Logic to parse new commands are handled by the controllerPro while old commands are parsed with the help of existing method in the controller hence avoiding code duplication. This is possible because controllerPro extends controller.


View (Text Based Command Line Interface): 

- View Interface:
	* This class provides a blueprint to create a view.
- View:
	* This class implements the view interface.
	* This class represents the text-based interface for the user. (command line outputs visible to user).
	* It has methods which show the resultant output according to the user inputs (commands). Controller calls view's methods to show output to user. Controller is responsible for 	  reading commands and view is responsible for printing output message.
	* We have an interactive interface where users get feedback on inputed commands (success, failure etc). Users can make use of 'help' command to list all the supported commands and 	  its usage.

MainApp:
* We start our application via MainApp. It is responsible for creating controller object by passing it's contructor the required arguements. 
* We call controller.execute() to start the program.
* We now have the ability to accept a script file as a command-line option. For example "-file name-of-script.txt". If a valid file is provided, the program will run the script and exit. If the program is run without any command line options, then it will allow interactive entry of script commands as before.

## Changes from the previous version
1. New operations like split, color correct, histogram, level adjust and compression are added to the existing ImageImpl class. New methods for each were added without changing the existing. This design choice was chosen because we wanted to maintain backward compatiblilty and keep our model simple. The controller handles the separation of version.
2. We introduced a new controller which extends our old controller. This new controller 'ControllerPro' has the ability to handle new operations while supporting the existing operations as it is. The original controller remains unchanged.
3. We added more JUnit tests to test new operations and new controller.
4. Application can now run in two ways. A user has the option to enter a script file while running the application using command line option. If a valid file is provided, the program will run the script and exit. If the program is run without any command line options, then it will allow interactive entry of script commands as before.
5. Rest of the code base (view etc) remains the same.

## Running the application
1. Using command line option
Go to the source folder and open terminal. Run the following commands:
```bash
javac MainApp.java
java MainApp.java -file name-of-script.txt
```
2. Running the application using JAR file
You can run the JAR file by entering the following command from the terminal:
```bash
java -jar assignment5.jar script-path
```
   

## Running the script file
Run the MainApp file, then enter the following command\
```bash
run "C:\Users\PrathamS\IdeaProjects\final assignment4\commands.txt"
```
## Citation 
The 'balcony_view' image is my own and I'm authorizing its use for the project.
The 'Mumbai' image is my own and I'm authorizing its use for the project.