package org.example.sugerencias;

import org.example.prenda.Prenda;

public class Sugerencia {
  private Prenda superior;
  private Prenda inferior;
  private Prenda calzado;

  public Sugerencia(Prenda superior, Prenda inferior, Prenda calzado) {
    this.superior = superior;
    this.inferior = inferior;
    this.calzado = calzado;
  }

  public boolean aptoParaTemperatura(int temperatura) {
    return this.superior.esAptoTemperatura(temperatura)
        && this.inferior.esAptoTemperatura(temperatura)
        && this.calzado.esAptoTemperatura(temperatura);
  }
}
