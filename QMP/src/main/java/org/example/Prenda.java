package org.example;

import org.example.atributos.Categoria;
import org.example.atributos.Formalidad;
import org.example.atributos.TipoDePrenda;
import org.example.atributos.Trama;

public class Prenda {
  private final TipoDePrenda tipo;
  private final String material;
  private final String colorPrimario;
  private final String colorSecundario;
  private Trama trama = Trama.LISA; //es lisa por default
  private final Formalidad formalidad;

  // Constructor

  public Prenda(TipoDePrenda tipo, String material, String colorPrimario, String colorSecundario, Trama trama, Formalidad formalidad) {
    this.tipo = tipo;
    this.material = material;
    this.colorPrimario = colorPrimario;
    this.colorSecundario = colorSecundario;
    this.trama = trama;
    this.formalidad = formalidad;
  }

  // Getters

  public TipoDePrenda getTipo() { return tipo; }

  public Trama getTrama() { return trama; }

  public String getMaterial() { return material; }

  public String getColorPrimario() { return colorPrimario; }

  public String getColorSegundario() { return colorSecundario; }

  public Formalidad getFormalidad() { return formalidad; }

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

}