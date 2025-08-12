package org.example.prenda.uniformes;

import org.example.prenda.Atuendo;
import org.example.prenda.Prenda;

public abstract class Uniforme { // FACTORY METHOD

  public Atuendo fabricarUniforme() {
    return new Atuendo(
        this.fabricarParteSuperior(), this.fabricarParteInferior(), this.fabricarCalzado());
  }

  protected abstract Prenda fabricarParteSuperior(); // TEMPLATE METHOD

  protected abstract Prenda fabricarParteInferior();

  protected abstract Prenda fabricarCalzado();
}
