package org.example.prenda.uniformes;

import org.example.prenda.Prenda;
import org.example.prenda.atributos.TipoDePrenda;

public class UniformeJohnson extends Uniforme {

  @Override
  public Prenda fabricarParteSuperior() {
    return new Prenda(TipoDePrenda.CAMISA, "seda", "blanca", null, null, null);
  }

  @Override
  public Prenda fabricarParteInferior() {
    return new Prenda(TipoDePrenda.PANTALON, "no se", "negro", null, null, null);
  }

  @Override
  public Prenda fabricarCalzado() {
    return new Prenda(TipoDePrenda.ZAPATO, "cuero", "negro", null, null, null);
  }
}
