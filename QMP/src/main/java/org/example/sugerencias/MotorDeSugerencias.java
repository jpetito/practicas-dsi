package org.example.sugerencias;

//PATRON STRATEGY
//define el algoritmo de sugerencias

import org.example.Atuendo;
import org.example.Guardarropas;

import java.util.List;

public interface MotorDeSugerencias {

  List<Atuendo> generarSugerencias(Guardarropas guardarropas);
}
