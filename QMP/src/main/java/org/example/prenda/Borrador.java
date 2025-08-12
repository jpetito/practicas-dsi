package org.example.prenda;

import org.example.prenda.atributos.Formalidad;
import org.example.prenda.atributos.TipoDePrenda;
import org.example.prenda.atributos.Trama;

// PATRON BUILDER --> ya que la construccion es paso a paso, no de golpe.

class Borrador {
  private TipoDePrenda tipo;
  private String material;
  private String colorPrimario;
  private String colorSecundario = null;
  private Trama trama = Trama.LISA; // es lisa por default
  private Formalidad formalidad;

  public Borrador(TipoDePrenda tipo) {
    validateNonNull(tipo);
    this.tipo = tipo;
  }

  public void especificarColorPrincipal(String color) {
    validateNonNull(color);
    this.colorPrimario = color;
  }

  public void especificarMaterial(String material) {
    validateNonNull(material);
    this.material = material;
  }

  public void especificarTrama(Trama trama) {
    this.trama = trama == null ? Trama.LISA : trama;
  }

  public Prenda crearPrenda() {
    return new Prenda(tipo, material, colorPrimario, colorSecundario, trama, formalidad);
  }

  private void validateNonNull(Object parametro) {
    if (parametro == null) {
      throw new IllegalArgumentException("El parámetro no puede ser null");
    }
  }
}
