package org.example.prenda;

import org.example.prenda.atributos.Categoria;
import org.example.prenda.atributos.Formalidad;
import org.example.prenda.atributos.TipoDePrenda;
import org.example.prenda.atributos.Trama;

public class Prenda {
  private final TipoDePrenda tipo;
  private final String material;
  private final String colorPrimario;
  private final String colorSecundario;
  private Trama trama = Trama.LISA; // es lisa por default
  private final Formalidad formalidad;
  private int temperaturaMax;

  // Constructor

  public Prenda(
      TipoDePrenda tipo,
      String material,
      String colorPrimario,
      String colorSecundario,
      Trama trama,
      Formalidad formalidad,
      int TemperaturaMax) {
    this.tipo = tipo;
    this.material = material;
    this.colorPrimario = colorPrimario;
    this.colorSecundario = colorSecundario;
    this.trama = trama;
    this.formalidad = formalidad;
    this.temperaturaMax = TemperaturaMax;
  }

  // Getters

  public TipoDePrenda getTipo() {
    return tipo;
  }

  public Trama getTrama() {
    return trama;
  }

  public String getMaterial() {
    return material;
  }

  public String getColorPrimario() {
    return colorPrimario;
  }

  public String getColorSegundario() {
    return colorSecundario;
  }

  public Formalidad getFormalidad() {
    return formalidad;
  }

  public int getTemperaturaMax() {
    return temperaturaMax;
  }

  // Metodos

  public boolean esSuperior() {
    return this.tipo.getCategoria() == Categoria.PARTE_SUPERIOR;
  }

  public boolean esInferior() {
    return this.tipo.getCategoria() == Categoria.PARTE_INFERIOR;
  }

  public boolean esCalzado() {
    return this.tipo.getCategoria() == Categoria.CALZADO;
  }

  public boolean esAccesorio() {
    return this.tipo.getCategoria() == Categoria.ACCESORIO;
  }

  public boolean esAptoTemperatura(int temperatura) {
    return this.temperaturaMax >= temperatura;
  }
}
