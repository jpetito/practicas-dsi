package canchaDePaddle.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Table(name = "color")
@Entity
public class Color {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) //PARA PONER LA PK
  private Long id;

  private String descripcion;

  @OneToMany(mappedBy = "colorCancha")
  List<Partido> partidos;

  @OneToMany(mappedBy = "colorPaletas")
  List<Paleta> paletas;

  // Getters y Setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getDescripcion() { return descripcion; }
  public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

  public List<Paleta> getPaletas() { return paletas; }
  public void setPaletas(List<Paleta> paletas) { this.paletas = paletas; }

  public List<Partido> getPartidos() { return partidos; }
  public void setPartidos(List<Partido> partidos) { this.partidos = partidos; }

}
