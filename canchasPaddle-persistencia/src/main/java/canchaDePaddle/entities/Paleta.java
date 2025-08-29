package canchaDePaddle.entities;

import canchaDePaddle.entities.jugadorPorPartido.JugadorPartidoPaleta;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.lang.reflect.Constructor;

@Table(name = "paleta")
@Entity
public class Paleta {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) //PARA PONER LA PK
  private Long id;

  private String nombre;

  private int grosor_mm;

  @ManyToOne
  @JoinColumn(name = "color_id")
  private Color color;

  @ManyToOne
  @JoinColumn(name = "constructor_id")
  private Constructor constructor;

  @OneToMany(mappedBy = "paleta")
  private List<JugadorPartidoPaleta> jugadorPartidoPaletas;


  // Getters y Setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }

  public int getGrosorMm() { return grosor_mm; }
  public void setGrosorMm(int grosor_mm) { this.grosor_mm = grosor_mm; }

  public Color getColor() { return color; }
  public void setColor(Color color) { this.color = color; }

  public Constructor getConstructor() { return constructor; }
  public void setConstructor(Constructor constructor) { this.constructor = constructor; }

  public List<JugadorPartidoPaleta> getJugadorPartidoPaletas() { return jugadorPartidoPaletas; }
  public void setJugadorPartidoPaletas(List<JugadorPartidoPaleta> jugadorPartidoPaletas) { this.jugadorPartidoPaletas = jugadorPartidoPaletas; }


}
