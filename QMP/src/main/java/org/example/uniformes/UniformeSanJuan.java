package org.example.uniformes;

import org.example.Prenda;
import org.example.atributos.TipoDePrenda;

public class UniformeSanJuan extends Uniforme {

  @Override
  public Prenda fabricarParteSuperior(){
    return new Prenda(TipoDePrenda.CHOMBA, "piqué", "verde", null, null);
  }

  @Override
  public Prenda fabricarParteInferior(){
    return new Prenda(TipoDePrenda.PANTALON, "acetato", "gris", null, null);
  }

  @Override
  public  Prenda fabricarCalzado(){
    return new Prenda(TipoDePrenda.ZAPATILLA, "cuero", "blanco", null, null);
  }
}
