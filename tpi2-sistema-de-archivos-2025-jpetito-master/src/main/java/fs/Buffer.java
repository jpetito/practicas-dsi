package fs;

import fs.excepciones.BufferInsufficentSpace;

public class Buffer {
  private byte[] bytes;
  private int offset;
  private int start;
  private int end;

  public Buffer(int size) {
    this.start = 0;
    this.offset = 0;
    this.end = size - 1;
    this.bytes = new byte[size];
  }

  // getters

  public byte[] getBytes() {
    return this.bytes;
  }

  public int getStart() {
    return this.start;
  }

  public int getEnd() {
    return this.end;
  }

  public int getMaxSize() {
    return bytes.length;
  }

  public int getCurrentSize() {
    return this.end - this.offset + 1;
  }

  public void limit(int offset) {
    this.end = this.start + offset - 1;
  }

  public void write(byte[] data) {
    int bytesAmmount = data.length;

    this.verifyEnoughSpace(bytesAmmount);

    for (int i = this.offset; i < this.offset + bytesAmmount; i++) {
      this.bytes[i] = data[i - this.offset];
    }

    this.offset = this.offset + bytesAmmount;
  }

  public void write(byte data) {
    int bytesAmmount = 1;

    this.verifyEnoughSpace(bytesAmmount);

    this.bytes[this.offset] = data;

    this.offset = this.offset + bytesAmmount;
  }

  public void unlimitedWrite(byte[] data) {
    int bytesAmmount = data.length;

    this.ensureCapacity(bytesAmmount);

    for (int i = this.offset; i < this.offset + bytesAmmount; i++) {
      this.bytes[i] = data[i - this.offset];
    }

    this.offset = this.offset + bytesAmmount;
  }

  private void verifyEnoughSpace(int bytesAmmount) {
    if (this.getCurrentSize() < bytesAmmount) {
      throw new BufferInsufficentSpace(
          "No hay suficiente espacio para escribir en el buffer, se requieren: "
              + bytesAmmount
              + " bytes, pero hay: " + this.getCurrentSize()
              + " bytes disponibles");
    }
  }

  private void ensureCapacity(int bytesAmmount) {
    if (getCurrentSize() < bytesAmmount) {
      int currentDataSize = offset - start;
      int newSize = currentDataSize + bytesAmmount;

      byte[] newBytes = new byte[newSize];
      System.arraycopy(this.bytes, start, newBytes, 0, currentDataSize);

      this.bytes = newBytes;
      this.start = 0;
      this.offset = currentDataSize;
      this.end = this.bytes.length - 1;
    }
  }

}
