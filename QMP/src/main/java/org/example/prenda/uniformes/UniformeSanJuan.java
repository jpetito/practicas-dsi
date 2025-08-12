package org.example.prenda.uniformes;

import org.example.prenda.Prenda;
import org.example.prenda.atributos.TipoDePrenda;

public class UniformeSanJuan extends Uniforme {

  @Override
  public Prenda fabricarParteSuperior() {
    return new Prenda(TipoDePrenda.CHOMBA, "piqué", "verde", null, null, null);
  }

  @Override
  public Prenda fabricarParteInferior() {
    return new Prenda(TipoDePrenda.PANTALON, "acetato", "gris", null, null, null);
  }

  @Override
  public Prenda fabricarCalzado() {
    return new Prenda(TipoDePrenda.ZAPATILLA, "cuero", "blanco", null, null, null);
  }
}
