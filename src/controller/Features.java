package controller;

public interface Features {
  void loadImage();
  void saveImage();
  void apply();
  void adjustLevel();
  void setSplitPercentage();
  void saveTransformation();
  void applyCompress();
  void splitMode(boolean isEnabled);
}
