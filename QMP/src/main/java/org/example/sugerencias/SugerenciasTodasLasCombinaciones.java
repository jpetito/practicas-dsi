package org.example.sugerencias;

import java.util.ArrayList;
import java.util.List;
import org.example.Guardarropas;
import org.example.prenda.Prenda;

public class SugerenciasTodasLasCombinaciones implements MotorDeSugerencias {

  @Override
  public List<Sugerencia> generarSugerencias(Guardarropas g) {
    List<Prenda> sup = g.getPrendasSuperiores();
    List<Prenda> inf = g.getPrendasInferiores();
    List<Prenda> cal = g.getCalzados();

    List<Sugerencia> resultado = new ArrayList<>();
    for (Prenda s : sup) {
      for (Prenda i : inf) {
        for (Prenda c : cal) {
          resultado.add(new Sugerencia(s, i, c));
        }
      }
    }
    return resultado;
  }
}
