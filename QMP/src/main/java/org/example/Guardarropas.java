package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.example.prenda.Prenda;

public class Guardarropas { // SINGLETON
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
}
