package org.example;

import java.util.ArrayList;
import java.util.List;
import org.example.prenda.Prenda;
import org.example.sugerencias.MotorDeSugerencias;
import org.example.sugerencias.Sugerencia;

public class Usuario {
  List<Prenda> prendas = new ArrayList<>();
  int edad;
  MotorDeSugerencias motorDeSugerencias;
  private Guardarropas guardarropas;

  public void setMotorDeSugerencias(MotorDeSugerencias motorDeSugerencias) {
    this.motorDeSugerencias = motorDeSugerencias;
  }

  public MotorDeSugerencias getMotorDeSugerencias() {
    return this.motorDeSugerencias;
  }

  public List<Sugerencia> todasLasCombinaciones() {
    return motorDeSugerencias.generarSugerencias(guardarropas);
  }
}
