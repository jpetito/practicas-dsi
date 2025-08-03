package ar.edu.utn.frba.dds.macowins.ventas;

class Tarjeta implements MetodoDePago {
  private int cantidadCuotas;
  private int coeficienteFijo;

  @Override
  public int precioFinal(int precioBase) {
    return (int) (precioBase + cantidadCuotas * coeficienteFijo + precioBase * 0.01);
  }
}
