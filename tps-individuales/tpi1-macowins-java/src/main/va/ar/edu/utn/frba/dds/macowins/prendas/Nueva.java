package ar.edu.utn.frba.dds.macowins.prendas;

public class Nueva implements Estado {
  @Override
  public int calcularPrecio(int precioBase) {
    return precioBase;
  }
}

