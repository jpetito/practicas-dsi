package org.example;

import org.example.atributos.TipoDePrenda;
import org.example.atributos.Trama;

public class Prenda {
  private TipoDePrenda tipo;
  private String material;
  private String colorPrimario;
  private String colorSegundario = null;
  private Trama trama;

  // Constructor

  public Prenda(TipoDePrenda tipo, String material, String colorPrimario, String colorSegundario, Trama trama) {
    this.tipo = tipo;
    this.material = material;
    this.colorPrimario = colorPrimario;
    this.colorSegundario = colorSegundario;
    this.trama = trama;
  }

  // Getters

  public TipoDePrenda getTipo() { return tipo; }

  public Trama getTrama() { return trama; }

  public String getMaterial() { return material; }

  public String getColorPrimario() { return colorPrimario; }

  public String getColorSegundario() { return colorSegundario; }

}