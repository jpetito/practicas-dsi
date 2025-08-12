package org.example.sugerencias;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.example.Guardarropas;
import org.example.prenda.Prenda;
import org.example.prenda.atributos.Formalidad;

public class SugerenciasInformales implements MotorDeSugerencias {

  @Override
  public List<Sugerencia> generarSugerencias(Guardarropas g) {
    List<Prenda> sup =
        g.getPrendasSuperiores().stream()
            .filter(p -> p.getFormalidad() == Formalidad.INFORMAL)
            .collect(Collectors.toList());
    List<Prenda> inf =
        g.getPrendasInferiores().stream()
            .filter(p -> p.getFormalidad() == Formalidad.INFORMAL)
            .collect(Collectors.toList());
    List<Prenda> cal =
        g.getCalzados().stream()
            .filter(p -> p.getFormalidad() == Formalidad.INFORMAL)
            .collect(Collectors.toList());

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
