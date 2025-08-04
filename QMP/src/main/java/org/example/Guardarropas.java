package org.example;

import org.example.atributos.Formalidad;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Guardarropas { //SINGLETON
  private final Random random = new Random();
  private ArrayList<Prenda> prendas;

  public Guardarropas(ArrayList<Prenda> prendas) {
    this.prendas = prendas;
  }

  public List<Prenda> getPrendasSuperiores() {
    return new ArrayList<>(this.prendas.stream().filter(Prenda::esSuperior).toList());
  }

  public List<Prenda> getPrendasInferiores() {
    return new ArrayList<>(this.prendas.stream().filter(Prenda::esInferior).toList());
  }

  public List<Prenda> getCalzados() {
    return new ArrayList<>(this.prendas.stream().filter(Prenda::esCalzado).toList());
  }

  public Atuendo sugerenciaAleatoria() {
    Prenda s = getPrendasSuperiores()
        .get(random.nextInt(getPrendasSuperiores().size()));
    Prenda i = getPrendasInferiores()
        .get(random.nextInt(getPrendasInferiores().size()));
    Prenda c = getCalzados()
        .get(random.nextInt(getCalzados().size()));
    return new Atuendo(s, i, c);
  }

  public Atuendo sugerenciaFormal(){
    Prenda s = getPrendasSuperiores().stream()
        .filter(p -> p.getFormalidad() == Formalidad.FORMAL)
        .findAny().orElseThrow(() -> new IllegalStateException("No hay prendas formales disponibles"));
    Prenda i = getPrendasInferiores().stream()
        .filter(p -> p.getFormalidad() == Formalidad.FORMAL)
        .findAny().orElseThrow(() -> new IllegalStateException("No hay prendas formales disponibles"));
    Prenda c = getCalzados().stream()
        .filter(p -> p.getFormalidad() == Formalidad.FORMAL)
        .findAny().orElseThrow(() -> new IllegalStateException("No hay prendas formales disponibles"));
    return new Atuendo(s, i, c);
  }


}
