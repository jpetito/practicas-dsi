package org.example;

import java.util.List;

public class Atuendos {
  private Prenda parteSuperior;
  private Prenda parteInferior;
  private Prenda calzado;
  private List<Prenda> accesorios = null;

  public Atuendos(Prenda parteSuperior, Prenda parteInferior, Prenda calzado, List<Prenda> accesorios) {
  this.parteSuperior = parteSuperior;
  this.parteInferior = parteInferior;
  this.calzado = calzado;
  this.accesorios = accesorios;
  }

}
