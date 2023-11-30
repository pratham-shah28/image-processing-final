package controller;

import java.io.IOException;

public interface Features {
  void loadImage() throws IOException;
  void saveImage() throws IOException;
  void apply();
  void adjustLevel();
  void setSplitPercentage();
  void saveTransformation();
  void applyCompress();
  void splitMode(boolean isEnabled);
}
