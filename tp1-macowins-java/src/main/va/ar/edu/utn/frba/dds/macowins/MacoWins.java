package ar.edu.utn.frba.dds.macowins;

import ar.edu.utn.frba.dds.macowins.ventas.Venta;
import java.util.List;

public class MacoWins {
  private List<Venta> ventas;

  public MacoWins(List<Venta> ventas) {
    this.ventas = ventas;
  }

  public int gananciaDelDia(String fecha) {
    return this.ventas.stream().filter(venta -> venta.getFecha().equals(fecha)).mapToInt(venta -> venta.ganancia()).sum();
  }
}
