package org.example;

import org.example.atributos.TipoDePrenda;
import org.example.atributos.Trama;

public class Prenda {
  private final TipoDePrenda tipo;
  private final String material;
  private final String colorPrimario;
  private final String colorSecundario;
  private Trama trama = Trama.LISA; //es lisa por default

  // Constructor

  public Prenda(TipoDePrenda tipo, String material, String colorPrimario, String colorSecundario, Trama trama) {
    this.tipo = tipo;
    this.material = material;
    this.colorPrimario = colorPrimario;
    this.colorSecundario = colorSecundario;
    this.trama = trama;
  }

  // Getters

  public TipoDePrenda getTipo() { return tipo; }

  public Trama getTrama() { return trama; }

  public String getMaterial() { return material; }

  public String getColorPrimario() { return colorPrimario; }

  public String getColorSegundario() { return colorSecundario; }


}