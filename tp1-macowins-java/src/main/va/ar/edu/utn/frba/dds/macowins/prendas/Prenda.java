package ar.edu.utn.frba.dds.macowins.prendas;

public class Prenda {
  private String tipo;
  private final int precioBase;
  private Estado estado;

  public Prenda(String tipo, int precioBase, Estado estado) {
    this.tipo = tipo;
    this.precioBase = precioBase;
    this.estado = estado;
  }

  public String getTipo() {
    return this.tipo;
  }

  public int precio() {
    return this.precioFinal();
  }

  public int precioFinal() {
    return estado.calcularPrecio(precioBase);
  }

}