package org.example;

import org.example.sugerencias.MotorDeSugerencias;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
  List<Prenda> prendas = new ArrayList<>();
  int edad;
  MotorDeSugerencias motorDeSugerencias;

  public void setMotorDeSugerencias(MotorDeSugerencias motorDeSugerencias) {
    this.motorDeSugerencias = motorDeSugerencias;
  }

  public MotorDeSugerencias getMotorDeSugerencias() {
    return this.motorDeSugerencias;
  }
}
