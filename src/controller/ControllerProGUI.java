package controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import model.Image;
import model.ImageCreator;
import view.ViewGUIInterface;

/**
 * This class is an extended version of original Controller for additional functionalities such as
 * compression, histogram, color correction, level adjustment.
 * It retains support for all operations performed by original Controller.
 */
public class ControllerProGUI implements Features {

  protected ViewGUIInterface view;
  private Map<String, Image> images;
  private ImageCreator imageCreator;
  private Integer splitPerc;
  private boolean split;
  private boolean isSaved;
  private ControllerUtil controllerUtil;


  /**
   * A constructor for ControllerPro class.
   *
   * @param view         View object.
   * @param images       Hashmap of images.
   * @param imageCreator Factory for creating Image object.
   */
  public ControllerProGUI(ViewGUIInterface view, HashMap<String, Image> images,
                          ImageCreator imageCreator) {
    this.view = view;
    this.images = images;
    split = false;
    this.imageCreator = imageCreator;
    this.controllerUtil = new ControllerUtil();
    splitPerc = 50;
  }

  /**
   * This method sets the view and adds all the features.
   */
  public void setView() {
    //provide view with all the callbacks
    view.addFeatures(this);
  }

  @Override
  public void loadImage() throws IOException {
    String status = "";
    if (images.containsKey("originalImage") && !isSaved) {
      if (view.saveOption() == 0) {
        saveImage();
        isSaved = true;
      }
    }
    File selectedFile = view.loadSelectedImage();
    if (selectedFile != null) {
      status = controllerUtil.loadImage(selectedFile.getCanonicalPath(), "originalImage", images, imageCreator);
    }
    if (status.equals("Load Successful.")) {
      images.put("newImage", images.get("originalImage"));
      view.updateImageLabel(images.get("originalImage"), images.get("originalImage").createHistogram());
      isSaved = false;
    }
    view.showDialog(status);
  }

  @Override
  public void saveImage() throws IOException {
    String status = "Save failed.";
    if (!checkImageExists()) {
      return;
    }
    File selectedDirectory = view.selectedDirectory();
    if (selectedDirectory != null) {
      String imageName = view.getImageName();
      status = controllerUtil.saveImage(selectedDirectory.getCanonicalPath() + imageName, images.get("originalImage"));
    }
    view.showDialog(status);
  }

  @Override
  public void apply() {
    if (!checkImageExists()) {
      return;
    }
    String selectedOption = view.getComboBoxSelectedItem();
    if ("red-component".equals(selectedOption)) {
      images.put("newImage", images.get("originalImage").redComponent());
    } else if ("green-component".equals(selectedOption)) {
      images.put("newImage", images.get("originalImage").greenComponent());
    } else if ("blue-component".equals(selectedOption)) {
      images.put("newImage", images.get("originalImage").blueComponent());
    } else if ("flip-vertical".equals(selectedOption)) {
      images.put("newImage", images.get("originalImage").flipVertical());
    } else if ("flip-horizontal".equals(selectedOption)) {
      images.put("newImage", images.get("originalImage").flipHorizontal());
    } else if ("blur".equals(selectedOption)) {
      images.put("newImage", images.get("originalImage").blur());
    } else if ("sharpen".equals(selectedOption)) {
      images.put("newImage", images.get("originalImage").sharpen());
    } else if ("sepia".equals(selectedOption)) {
      images.put("newImage", images.get("originalImage").sepia());
    } else if ("greyscale".equals(selectedOption)) {
      images.put("newImage", images.get("originalImage").luma());
    } else if ("color-correct".equals(selectedOption)) {
      images.put("newImage", images.get("originalImage").colorCorrect());
    }
    if (handleSplit()) {
      return;};
    view.updateImageLabel(images.get("newImage"), images.get("newImage").createHistogram());
  }

  @Override
  public void adjustLevel() {
    if (!checkImageExists()) {
      return;
    }
    try {
      int b = Integer.parseInt(view.bInput());
      int m = Integer.parseInt(view.mInput());
      int w = Integer.parseInt(view.wInput());
      if (b < 0 || m < 0 || w < 0 || b > 255 || m > 255 || w > 255) {
        view.showDialog("b, m, w values should be in the range 0 - 255");
        return;
      }
      if (!(b < m && b < w && m < w)) {
        view.showDialog("b, m, w values should be in ascending order");
        return;
      }
      images.put("newImage", images.get("originalImage").adjustLevels(b, m, w));
      if (handleSplit()) return;
      view.updateImageLabel(images.get("newImage"), images.get("newImage").createHistogram());
    } catch (Exception adlevel) {
      view.showDialog("Please enter valid b, m, w values");
    }
  }

  @Override
  public void setSplitPercentage() {
    if (!checkImageExists()) {
      return;
    }
    if (!(view.getSplit().equals(""))) {
      try {
        splitPerc = Integer.parseInt(view.getSplit());
      } catch (NumberFormatException ex) {
        view.showDialog("Please enter a valid integer.");
      }
    } else {
      splitPerc = 50;
    }

    if (splitPerc >= 1 && splitPerc <= 100) {
      images.put("splitImage", images.get("newImage").applySplit(images.get("originalImage"), splitPerc));
      view.updateImageLabel(images.get("splitImage"), images.get("splitImage").createHistogram());
    } else {
      view.showDialog("Please enter a number between 1 and 100.");
    }
  }

  @Override
  public void confirmTransformation() {
    if (!checkImageExists()) {
      return;
    }
    images.put("originalImage", images.get("newImage"));
    view.updateImageLabel(images.get("originalImage"),
            images.get("originalImage").createHistogram());
    split = !split;
    view.toggleSet(split);
  }

  @Override
  public void applyCompress() {
    if (!checkImageExists()) {
      return;
    }
    int userInput = view.getCompressInput();
    if (userInput >= 0 && userInput <= 100) {
      // Perform an action based on the entered number
      images.put("newImage", images.get("originalImage").compress(userInput));
      if (handleSplit()) return;
      view.updateImageLabel(images.get("newImage"), images.get("newImage").createHistogram());
    } else {
      // Display an error message for an invalid range
      view.showDialog("Please enter a number between 0 and 100.");
    }
  }

  @Override
  public void splitMode(boolean isEnabled) {
    if (!checkImageExists()) {
      return;
    }
    split = !split;
    view.toggleSet(split);
    if (isEnabled) {
      if (images.containsKey("newImage")) {
        images.put("splitImage", images.get("newImage").applySplit(images.get("originalImage"), splitPerc));
        view.updateImageLabel(images.get("splitImage"), images.get("splitImage").createHistogram());
      } else {
        if (images.containsKey("originalImage")) {
          view.updateImageLabel(images.get("originalImage"), images.get("originalImage").createHistogram());
        }
      }
    } else {
      images.put("newImage", images.get("originalImage"));
      view.updateImageLabel(images.get("originalImage"), images.get("originalImage").createHistogram());
    }
  }

  private boolean checkImageExists() {
    if (!(images.containsKey("originalImage"))) {
      view.showDialog("Please load an image first");
      return false;
    } else {
      return true;
    }
  }

  private boolean handleSplit() {
    if (split) {
      if (!(view.getSplit().equals(""))) {
        try {
          splitPerc = Integer.parseInt(view.getSplit());
        } catch (NumberFormatException ex) {
          view.showDialog("Please enter a valid integer.");
        }
      } else {
        splitPerc = 50;
      }
      if (splitPerc >= 1 && splitPerc <= 100) {
        images.put("splitImage", images.get("newImage").applySplit(images.get("originalImage"), splitPerc));
        view.updateImageLabel(images.get("splitImage"), images.get("splitImage").createHistogram());
        return true;
      } else {
        view.showDialog("Please enter a number between 1 and 100.");
        return true;
      }
    } else {
      images.put("originalImage", images.get("newImage"));
    }
    return false;
  }

}
