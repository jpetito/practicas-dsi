package fs;

import fs.excepciones.CanNotReadFileException;
import java.util.function.Consumer;

public class File {
  int fd;
  LowLevelFileSystem lowLevelFileSystem;

  public File(int fd, LowLevelFileSystem lowLevelFileSystem) {
    this.fd = fd;
    this.lowLevelFileSystem = lowLevelFileSystem;
  }

  public int getDescriptor() {
    return this.fd;
  }

  public void close() {
    lowLevelFileSystem.closeFile(fd);
  }

  public void read(Buffer buffer) {
    int readBytes =
        lowLevelFileSystem.syncReadFile(
            this.fd, buffer.getBytes(), buffer.getStart(), buffer.getEnd());
    this.verifySuccessfulRead(readBytes);
    buffer.limit(readBytes);
  }

  public void verifySuccessfulRead(int bytes) {
    if (bytes < 0) {
      throw new CanNotReadFileException("Hubo un error al intentar leer el archivo");
    }
  }

  public void write(Buffer buffer) {
    lowLevelFileSystem.syncWriteFile(
        this.fd, buffer.getBytes(), buffer.getStart(), buffer.getEnd());
  }

  public Buffer asyncRead(Consumer<Buffer> callback) {
    Buffer buffer = new Buffer(100);
    lowLevelFileSystem.asyncReadFile(
        fd,
        buffer.getBytes(),
        buffer.getStart(),
        buffer.getEnd(),
        readBytes -> {
          buffer.limit(readBytes);
          callback.accept(buffer);
        });

    return buffer;
  }

  public void asyncWrite(Buffer buffer, Consumer<Buffer> callback) {
    lowLevelFileSystem.asyncWriteFile(
        fd,
        buffer.getBytes(),
        buffer.getStart(),
        buffer.getEnd(),
        () -> {
          callback.accept(buffer);
        });
  }


}
