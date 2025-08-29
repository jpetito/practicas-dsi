package canchaDePaddle.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "jugador")
@Entity
public class Jugador {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) //PARA PONER LA PK
  private Long id;

}
