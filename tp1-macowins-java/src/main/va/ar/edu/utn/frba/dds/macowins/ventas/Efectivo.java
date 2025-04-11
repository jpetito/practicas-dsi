package ar.edu.utn.frba.dds.macowins.ventas;

class Efectivo implements MetodoDePago {

  @Override
  public int precioFinal(int precioBase) {
    return precioBase;
  }
}