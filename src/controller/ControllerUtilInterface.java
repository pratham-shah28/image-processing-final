package controller;

import java.awt.image.BufferedImage;

import model.Image;

public interface ControllerUtilInterface {


  Image processLoadImage(BufferedImage image);

  String saveImage(String path, String imageName);
}
