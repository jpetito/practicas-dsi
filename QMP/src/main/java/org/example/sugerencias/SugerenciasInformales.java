package org.example.sugerencias;

import org.example.Atuendo;
import org.example.Guardarropas;
import org.example.Prenda;
import org.example.atributos.Formalidad;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SugerenciasInformales implements MotorDeSugerencias {

  @Override
  public List<Atuendo> generarSugerencias(Guardarropas g) {
    List<Prenda> sup = g.getPrendasSuperiores().stream().filter(p -> p.getFormalidad() == Formalidad.INFORMAL).collect(Collectors.toList());
    List<Prenda> inf = g.getPrendasInferiores().stream().filter(p -> p.getFormalidad() == Formalidad.INFORMAL).collect(Collectors.toList());
    List<Prenda> cal = g.getCalzados().stream().filter(p -> p.getFormalidad() == Formalidad.INFORMAL).collect(Collectors.toList());

    List<Atuendo> resultado = new ArrayList<>();
    for (Prenda s : sup) {
      for (Prenda i : inf) {
        for (Prenda c : cal) {
          resultado.add(new Atuendo(s, i, c));
        }
      }
    }
    return resultado;

  }
}
