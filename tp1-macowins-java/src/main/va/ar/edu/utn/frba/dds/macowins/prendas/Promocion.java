package ar.edu.utn.frba.dds.macowins.prendas;

public class Promocion implements Estado {
  private int valorFijo;

  public Promocion(int valorFijo) {
    this.valorFijo = valorFijo;
  }

  @Override
  public int calcularPrecio(int precioBase) {

    return precioBase - valorFijo;
  }
}