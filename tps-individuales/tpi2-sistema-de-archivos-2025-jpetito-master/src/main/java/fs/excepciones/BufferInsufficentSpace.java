package fs.excepciones;

public class BufferInsufficentSpace extends RuntimeException {
  public BufferInsufficentSpace(String message) {
    super(message);
  }
}