package LivreStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RepositorioStreams { //Singleton
  private static final RepositorioStreams instancia = new RepositorioStreams();
  private static final List<Canal> canales = new ArrayList<>();

  // Metodos
  public static RepositorioStreams getInstancia() {
    return instancia;
  }

  public List<Canal> getCanales() {
    return new ArrayList<>(canales);
  }

  public static void agregarCanal(Canal canal) {
    canales.add(canal);
  }

  public static void eliminarCanal(Canal canal) {
    canales.remove(canal);
  }

  public static void limpiarLista() {
    canales.clear();
  }

  public static List<Stream> getStreamsEnVivo() {
    return canales.stream().map(Canal::getStreamEnVivo).filter(Objects::nonNull).toList();
  }
}

