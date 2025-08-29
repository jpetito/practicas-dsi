package canchaDePaddle.entities.jugadorPorPartido;

import canchaDePaddle.entities.Jugador;
import canchaDePaddle.entities.Paleta;
import canchaDePaddle.entities.Partido;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "jugadorPartidoPaleta")
@Entity
@IdClass(JugadorPartidoPaletaID.class) //PK COMPUESTA
public class JugadorPartidoPaleta {

  @Id
  @ManyToOne
  @JoinColumn(name = "jugador_id")
  private Jugador jugador;

  @ManyToOne
  @JoinColumn(name = "partido_id")
  private Partido partido;

  @ManyToOne
  @JoinColumn(name = "paleta_id")
  private Paleta paleta;

  // Getters y Setters
  public Partido getPartido() { return partido; }
  public void setPartido(Partido partido) { this.partido = partido; }

  public Jugador getJugador() { return jugador; }
  public void setJugador(Jugador jugador) { this.jugador = jugador; }

  public Paleta getPaleta() { return paleta; }
  public void setPaleta(Paleta paleta) { this.paleta = paleta; }

}
