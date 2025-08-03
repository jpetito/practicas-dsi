package fs.excepciones;

public class CanNotReadFileException extends RuntimeException {
  public CanNotReadFileException(String message) {
    super(message);
  }
}
