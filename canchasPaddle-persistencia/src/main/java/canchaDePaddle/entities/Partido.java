package canchaDePaddle.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.awt.*;
import java.util.Date;

@Table(name = "partido")
@Entity
public class Partido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Date fecha_inicio;
  private Date fecha_fin;

  @ManyToOne
  @JoinColumn(name = "id_cancha")//Especifica la FK que se utilizará para realizar el join entre dos entidades.
  private Cancha cancha; // Si se aplica sobre una relación @OneToMany, evita la generación de una tabla intermedia

  @ManyToOne
  @JoinColumn(name = "id_color")
  private Color colorCancha;

  @ManyToOne
  @JoinColumn(name = "id_jugador")
  private Jugador jugadorReserva;

  // Getters y Setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public Date getFechaInicio() { return fecha_inicio; }
  public void setFechaInicio(Date fecha_inicio) { this.fecha_inicio = fecha_inicio; }

  public Date getFechaFin() { return fecha_fin; }
  public void setFechaFin(Date fecha_fin) { this.fecha_fin = fecha_fin; }

  public Cancha getCancha() { return cancha; }
  public void setCancha(Cancha cancha) { this.cancha = cancha; }

  public Color getColorCancha() { return colorCancha; }
  public void setColorCancha(Color colorCancha) { this.colorCancha = colorCancha; }

  public Jugador getJugadorReserva() { return jugadorReserva; }
  public void setJugadorReserva(Jugador jugadorReserva) { this.jugadorReserva = jugadorReserva; }

}
