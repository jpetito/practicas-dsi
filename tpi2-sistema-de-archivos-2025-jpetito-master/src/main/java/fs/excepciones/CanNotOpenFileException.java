package fs.excepciones;

public class CanNotOpenFileException extends RuntimeException {
  public CanNotOpenFileException(String message) {
    super(message);
  }
}