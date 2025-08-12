package org.example.sugerencias;

// PATRON STRATEGY
// define el algoritmo de sugerencias

import java.util.List;
import org.example.Guardarropas;

public interface MotorDeSugerencias {

  List<Sugerencia> generarSugerencias(Guardarropas guardarropas);
}
