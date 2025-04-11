package ar.edu.utn.frba.dds.macowins.ventas;

import ar.edu.utn.frba.dds.macowins.prendas.Prenda;
import java.util.List;

public class Venta {
  private String fecha;
  private List<Prenda> prendas;
  private MetodoDePago metodoDePago;

  public Venta(String fecha, List<Prenda> prendas, MetodoDePago metodoDePago) {
    this.fecha = fecha;
    this.prendas = prendas;
    this.metodoDePago = metodoDePago;
  }

  public String getFecha() {
    return this.fecha;
  }

  public int precioTotal() {
    return this.prendas.stream().mapToInt( prenda -> prenda.precioFinal()).sum();
  }

  public int ganancia() {
    return this.metodoDePago.precioFinal(this.precioTotal());
  }

  public int cantidadPrendasVendidas() {
    return this.prendas.size();
  }

}

