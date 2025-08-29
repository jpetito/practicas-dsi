package canchaDePaddle.entities.jugadorPorPartido;

import java.io.Serializable;
import java.util.Objects;

public class JugadorPartidoPaletaID implements Serializable {
  private Long partido;
  private Long jugador;

  public JugadorPartidoPaletaID() {}

  public JugadorPartidoPaletaID(Long partido, Long jugador) {
    this.partido = partido;
    this.jugador = jugador;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof JugadorPartidoPaletaID)) return false;
    JugadorPartidoPaletaID that = (JugadorPartidoPaletaID) o;
    return Objects.equals(partido, that.partido) &&
        Objects.equals(jugador, that.jugador);
  }

  @Override
  public int hashCode() {
    return Objects.hash(partido, jugador);
  }
}
