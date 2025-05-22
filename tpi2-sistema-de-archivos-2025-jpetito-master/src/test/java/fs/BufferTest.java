package fs;

import fs.excepciones.BufferInsufficentSpace;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BufferTest {

  @Test
  void inicialmenteCurrentSizeYMaxSizeCoinciden() {
    Buffer buffer = new Buffer(27);
    Assertions.assertEquals(buffer.getCurrentSize(), buffer.getMaxSize());
  }

  @Test
  void currentSizeRetornaElTamanioSegunLosLimites() {
    Buffer buffer = new Buffer(27);
    buffer.limit(12);
    Assertions.assertEquals(12, buffer.getCurrentSize());
  }

  @Test
  void maxSizeRetornaElTamanioInicial() {
    Buffer buffer = new Buffer(27);
    Assertions.assertEquals(27, buffer.getMaxSize());
  }

  @Test
  void maxSizeNoSeVeAfectadoPorLosLimite() {
    Buffer buffer = new Buffer(27);
    buffer.limit(8);
    buffer.limit(14);
    buffer.limit(1);
    Assertions.assertEquals(27, buffer.getMaxSize());
  }

  @Test
  void siSeEscribeMasDeLaCapacidadDelBufferLanzaUnaExcepcion() {
    Buffer buffer = new Buffer(10);

    byte[] data = new byte[11];

    Assertions.assertThrows(BufferInsufficentSpace.class, () -> buffer.write(data));
  }
}
