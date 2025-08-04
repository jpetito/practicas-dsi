package org.example.uniformes;

import org.example.Prenda;
import org.example.atributos.TipoDePrenda;

public class UniformeJohnson extends Uniforme {

  @Override
  public Prenda fabricarParteSuperior(){
    return new Prenda(TipoDePrenda.CAMISA, "seda", "blanca", null, null);
  }

  @Override
  public Prenda fabricarParteInferior(){
    return new Prenda(TipoDePrenda.PANTALON, "no se", "negro", null, null);
  }

  @Override
  public  Prenda fabricarCalzado(){
    return new Prenda(TipoDePrenda.ZAPATO, "cuero", "negro", null, null);
  }
}
