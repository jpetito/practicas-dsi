package org.example.prenda;

import java.util.List;
import org.example.Usuario;
import org.example.servicioMeteorologico.ServicioMeteorologico;
import org.example.sugerencias.Sugerencia;

public class AsesorDeImagen {
  private ServicioMeteorologico servicioMeteorologico;

  public AsesorDeImagen(ServicioMeteorologico servicioMeteorologico) {
    this.servicioMeteorologico = servicioMeteorologico;
  }

  public List<Sugerencia> sugerirAtuendos(Usuario usuario) {
    int temperatura = this.servicioMeteorologico.getTemperaturaEnCelsius();

    List<Sugerencia> combinaciones = usuario.todasLasCombinaciones();

    return combinaciones.stream().filter(a -> a.aptoParaTemperatura(temperatura)).toList();
  }
}
