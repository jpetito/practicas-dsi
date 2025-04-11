package ar.edu.utn.frba.dds.macowins.prendas;

public class Liquidacion implements Estado {
  @Override
  public int calcularPrecio(int precioBase) {
    return precioBase / 2;
  }
}