package fs;

import fs.excepciones.CanNotReadFileException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import fs.excepciones.CanNotOpenFileException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;

class HighLevelFileSystemTest {

  private LowLevelFileSystem lowLevelFileSystem;
  private HighLevelFileSystem fileSystem;

  @BeforeEach
  void initFileSystem() {
    lowLevelFileSystem = mock(LowLevelFileSystem.class);
    fileSystem = new HighLevelFileSystem(lowLevelFileSystem);
  }

  @Test
  void sePuedeAbrirUnArchivo() {
    when(lowLevelFileSystem.openFile("unArchivo.txt")).thenReturn(42);
    File file = fileSystem.open("unArchivo.txt");
    Assertions.assertEquals(file.getDescriptor(), 42);
  }

  @Test
  void siLaAperturaFallaUnaExcepcionEsLanzada() {
    when(lowLevelFileSystem.openFile("otroArchivo.txt")).thenReturn(-1);
    Assertions.assertThrows(CanNotOpenFileException.class, () -> fileSystem.open("otroArchivo.txt"));
  }

  @Test
  void sePuedeLeerSincronicamenteUnArchivoCuandoNoHayNadaParaLeer() {
    Buffer buffer = new Buffer(100);

    when(lowLevelFileSystem.openFile("ejemplo.txt")).thenReturn(42);
    when(lowLevelFileSystem.syncReadFile(42, buffer.getBytes(), 0, 100)).thenReturn(0);

    File file = fileSystem.open("ejemplo.txt");
    file.read(buffer);

    Assertions.assertEquals(0, buffer.getStart());
    Assertions.assertEquals(-1, buffer.getEnd());
    Assertions.assertEquals(0, buffer.getCurrentSize());
  }

  @Test
  void sePuedeLeerSincronicamenteUnArchivoCuandoHayAlgoParaLeer() {
    Buffer buffer = new Buffer(10);

    when(lowLevelFileSystem.openFile("ejemplo.txt")).thenReturn(42);
    when(lowLevelFileSystem.syncReadFile(42, buffer.getBytes(), 0, 9)).thenAnswer(invocation -> {
      Arrays.fill(buffer.getBytes(), 0, 4, (byte) 3);
      return 4;
    });

    File file = fileSystem.open("ejemplo.txt");
    file.read(buffer);

    Assertions.assertEquals(0, buffer.getStart());
    Assertions.assertEquals(3, buffer.getEnd());
    Assertions.assertEquals(4, buffer.getCurrentSize());
    Assertions.assertArrayEquals(buffer.getBytes(), new byte[]{3, 3, 3, 3, 0, 0, 0, 0, 0, 0});
  }

  @Test
  void siLaLecturaSincronicaFallaUnaExcepciónEsLanzada() {
    Buffer buffer = new Buffer(10);

    when(lowLevelFileSystem.openFile("archivoMalito.txt")).thenReturn(13);
    when(lowLevelFileSystem.syncReadFile(anyInt(), any(), anyInt(), anyInt())).thenReturn(-1);

    File file = fileSystem.open("archivoMalito.txt");

    Assertions.assertThrows(CanNotReadFileException.class, () -> file.read(buffer));
  }

  @Test
  void sePuedeEscribirSincronicamenteUnArchivoCuandoHayNoHayNadaParaEscribir() {
    Buffer buffer = new Buffer(10);
    buffer.limit(0);

    when(lowLevelFileSystem.openFile(any())).thenReturn(21);

    File file = fileSystem.open("archivoVacio.txt");
    file.write(buffer);

    verify(lowLevelFileSystem).syncWriteFile(21, buffer.getBytes(), 0, -1);
  }

  @Test
  void sePuedeEscribirSincronicamenteUnArchivoCuandoHayAlgoParaEscribir() {
    Buffer buffer = new Buffer(10);
    buffer.getBytes()[0] = 5;
    buffer.getBytes()[1] = 7;
    buffer.limit(2);

    when(lowLevelFileSystem.openFile(any())).thenReturn(22);

    File file = fileSystem.open("conContenido.txt");
    file.write(buffer);

    verify(lowLevelFileSystem).syncWriteFile(22, buffer.getBytes(), 0, 1);
  }

  @Test
  void sePuedeLeerAsincronicamenteUnArchivo() {

    when(lowLevelFileSystem.openFile(any())).thenReturn(7);

    File file = fileSystem.open("async.txt");

    AtomicBoolean callbackCalled = new AtomicBoolean(false);

    doAnswer(
        invocation -> {
          byte[] bytes = invocation.getArgument(1);
          int start = invocation.getArgument(2);
          int end = invocation.getArgument(3);
          Consumer<Integer> callback = invocation.getArgument(4);

          Arrays.fill(bytes, start, start + 2, (byte) 9);
          callback.accept(2);
          return null;
        })
        .when(lowLevelFileSystem)
        .asyncReadFile(anyInt(), any(), anyInt(), anyInt(), any());

    file.asyncRead(
        buffer -> {
          Assertions.assertEquals(0, buffer.getStart());
          Assertions.assertEquals(1, buffer.getEnd());
          Assertions.assertEquals(2, buffer.getCurrentSize());
          Assertions.assertEquals(9, buffer.getBytes()[0]);
          Assertions.assertEquals(9, buffer.getBytes()[1]);
          callbackCalled.set(true);
        });

    Assertions.assertTrue(callbackCalled.get());

  }

  @Test
  void sePuedeEscribirAsincronicamenteUnArchivo() {
    Buffer buffer = new Buffer(2);
    buffer.getBytes()[0] = 7;
    buffer.getBytes()[1] = 8;

    when(lowLevelFileSystem.openFile(any())).thenReturn(5);

    File file = fileSystem.open("asyncWrite.txt");

    AtomicBoolean callbackCalled = new AtomicBoolean(false);

    doAnswer(
        invocation -> {
          Runnable callback = invocation.getArgument(4);
          callback.run();
          return null;
        })
        .when(lowLevelFileSystem)
        .asyncWriteFile(anyInt(), any(), anyInt(), anyInt(), any());

    file.asyncWrite(
        buffer,
        b -> {
          Assertions.assertEquals(buffer, b);
          callbackCalled.set(true);
        });

    Assertions.assertTrue(callbackCalled.get());
  }

  @Test
  void sePuedeCerrarUnArchivo() {
    when(lowLevelFileSystem.openFile(any())).thenReturn(20);

    File file = fileSystem.open("close.txt");
    file.close();

    verify(lowLevelFileSystem).closeFile(20);
  }

  @Test
  void sePuedeSaberSiUnPathEsUnArchivoRegular() {
    when(lowLevelFileSystem.isRegularFile(any())).thenReturn(true);

    String path = "regular.txt";

    Assertions.assertTrue(fileSystem.isRegularFile(path));
    verify(lowLevelFileSystem).isRegularFile("regular.txt");
  }

  @Test
  void sePuedeSaberSiUnPathEsUnDirectorio() {
    when(lowLevelFileSystem.isDirectory(any())).thenReturn(true);

    String path = "home/directory/";

    Assertions.assertTrue(fileSystem.isDirectory(path));
    verify(lowLevelFileSystem).isDirectory("home/directory/");
  }

  @Test
  void sePuedeSaberSiUnPathExiste() {
    when(lowLevelFileSystem.exists(any())).thenReturn(true);

    String path = "algo.txt";

    Assertions.assertTrue(fileSystem.exists(path));
    verify(lowLevelFileSystem).exists("algo.txt");
  }

  @Test
  void leerArchivoAsincronicamenteYLuegoEscribirloAsincronicamente() {

    //aca se crean los buffers

    Buffer c0 = new Buffer(4);
    Buffer c1 = new Buffer(1);
    Buffer c2 = new Buffer(5);

    when(lowLevelFileSystem.openFile("archivoEjemplo.txt")).thenReturn(42);
    when(lowLevelFileSystem.syncReadFile(eq(42), eq(c0.getBytes()), anyInt(), anyInt()))
        .thenAnswer(
            invocation -> {
              Arrays.fill(c0.getBytes(), 0, 4, (byte) 0);
              return 4;
            });
    when(lowLevelFileSystem.syncReadFile(eq(42), eq(c1.getBytes()), anyInt(), anyInt()))
        .thenAnswer(
            invocation -> {
              c1.getBytes()[0] = (byte) 1;
              return 1;
            });
    when(lowLevelFileSystem.syncReadFile(eq(42), eq(c2.getBytes()), anyInt(), anyInt()))
        .thenAnswer(
            invocation -> {
              Arrays.fill(c2.getBytes(), 0, 5, (byte) 2);
              return 5;
            });

    //aca se leen los campos

    File file = fileSystem.open("archivoEjemplo.txt");
    file.read(c0);
    file.read(c1);
    file.read(c2);

    Assertions.assertEquals(0, c0.getStart());
    Assertions.assertEquals(3, c0.getEnd());
    Assertions.assertEquals(4, c0.getCurrentSize());

    for (int i = c0.getStart(); i <= c0.getEnd(); i++) {
      Assertions.assertEquals(0, c0.getBytes()[i]);
    }

    Assertions.assertEquals(0, c1.getStart());
    Assertions.assertEquals(0, c1.getEnd());
    Assertions.assertEquals(1, c1.getCurrentSize());

    Assertions.assertEquals(1, c1.getBytes()[0]);

    Assertions.assertEquals(0, c2.getStart());
    Assertions.assertEquals(4, c2.getEnd());
    Assertions.assertEquals(5, c2.getCurrentSize());

    for (int i = c2.getStart(); i <= c2.getEnd(); i++) {
      Assertions.assertEquals(2, c2.getBytes()[i]);
    }

    // se arma un nuevo buffer con todos los datos leidos

    Buffer buffer = new Buffer(4 + 3 + 1 + 5); // C0,  0x0, 0x10, 0x0, C1 y C2

    buffer.write(c0.getBytes());
    buffer.write((byte) 0x0);
    buffer.write((byte) 0x10);
    buffer.write((byte) 0x0);
    buffer.write(c1.getBytes());
    buffer.write(c2.getBytes());

    Assertions.assertEquals(0, buffer.getStart());
    Assertions.assertEquals(12, buffer.getEnd());
    Assertions.assertEquals(0, buffer.getCurrentSize());

    for (int i = 0; i <= 3; i++) {
      Assertions.assertEquals(0, buffer.getBytes()[i]);
    }

    Assertions.assertEquals(0x0, buffer.getBytes()[4]);
    Assertions.assertEquals(0x10, buffer.getBytes()[5]);
    Assertions.assertEquals(0x0, buffer.getBytes()[6]);

    Assertions.assertEquals(1, buffer.getBytes()[7]);

    for (int i = 8; i <= 12; i++) {
      Assertions.assertEquals(2, buffer.getBytes()[i]);
    }

    // se escribe el buffer resultante en un nuevo archivo ---

    when(lowLevelFileSystem.openFile(any())).thenReturn(22);

    File file2 = fileSystem.open("archivoEjemploFinal.txt");
    file2.write(buffer);

    verify(lowLevelFileSystem).syncWriteFile(22, buffer.getBytes(), 0, 12);
  }

}
