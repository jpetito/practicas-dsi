package org.example.atributos;

public enum TipoDePrenda {
  REMERA(Categoria.PARTE_SUPERIOR),
  CAMISA(Categoria.PARTE_SUPERIOR),
  CAMPERA(Categoria.PARTE_SUPERIOR),
  BUZO(Categoria.PARTE_SUPERIOR),
  CHOMBA(Categoria.PARTE_SUPERIOR),
  PANTALON(Categoria.PARTE_INFERIOR),
  SHORT(Categoria.PARTE_INFERIOR),
  POLLERA(Categoria.PARTE_INFERIOR),
  VERMUDA(Categoria.PARTE_INFERIOR),
  ZAPATO(Categoria.CALZADO),
  ZAPATILLA(Categoria.CALZADO),
  COLLAR(Categoria.ACCESORIO),
  ANILLO(Categoria.ACCESORIO),
  ARITO(Categoria.ACCESORIO),
  LENTES(Categoria.ACCESORIO);

  private final Categoria categoria;

  TipoDePrenda(Categoria categoria) {
    this.categoria = categoria;
  }

  public Categoria getCategoria() {
    return this.categoria;
  }
}