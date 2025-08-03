package fs;

import fs.excepciones.CanNotOpenFileException;

public class HighLevelFileSystem {
  LowLevelFileSystem lowLevelFileSystem;

  public HighLevelFileSystem(LowLevelFileSystem lowLevelFileSystem) {
    this.lowLevelFileSystem = lowLevelFileSystem;
  }

  public File open(String path) {
    int fd = lowLevelFileSystem.openFile(path);
    this.verifySuccessfulOpen(fd, path);
    return new File(fd, lowLevelFileSystem);
  }

  private void verifySuccessfulOpen(int fd, String path) {
    if (fd == -1) {
      throw new CanNotOpenFileException("No se pudo abrir el archivo: " + path);
    }
  }

  public boolean isDirectory(String path) {
    return lowLevelFileSystem.isDirectory(path);
  }

  public boolean isRegularFile(String path) {
    return lowLevelFileSystem.isRegularFile(path);
  }

  public boolean exists(String path) {
    return lowLevelFileSystem.exists(path);
  }

}
