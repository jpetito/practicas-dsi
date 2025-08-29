package canchaDePaddle.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Table (name = "cancha")
@Entity
public class Cancha {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) //PARA PONER LA PK
  private Long id;

  private String nombre;

  private boolean iluminacion;

  @OneToMany(mappedBy = "cancha") //no crea una nueva columna en la tabla cancha sino que usa la columna que ya existe en partido
  private List<Partido> partidos;

  // Getters y Setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }

  public boolean isIluminacion() { return iluminacion; }
  public void setIluminacion(boolean iluminacion) { this.iluminacion = iluminacion; }

  public List<Partido> getPartidos() { return partidos; }
  public void setPartidos(List<Partido> partidos) { this.partidos = partidos; }

}
