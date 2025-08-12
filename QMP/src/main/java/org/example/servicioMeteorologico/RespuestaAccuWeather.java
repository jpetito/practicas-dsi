package org.example.servicioMeteorologico;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RespuestaAccuWeather {
  private final Map<String, Object> estadoDelTiempo;
  private final Map<String, List<String>> alertasMeteorologicas;
  private final LocalDateTime expiracion;

  public RespuestaAccuWeather(
      Map<String, Object> estadoDelTiempo,
      Map<String, List<String>> alertasMeteorologicas,
      LocalDateTime expiracion) {
    this.estadoDelTiempo = new HashMap<>(estadoDelTiempo);
    this.alertasMeteorologicas = new HashMap<>(alertasMeteorologicas);
    this.expiracion = expiracion;
  }

  // getters

  public Map<String, Object> getEstadoDelTiempo() {
    return new HashMap<>(this.estadoDelTiempo);
  }

  public Map<String, List<String>> getAlertasMeteorologicas() {
    return new HashMap<>(alertasMeteorologicas);
  }

  // metodos

  public boolean expiro() {
    return this.expiracion.isAfter(LocalDateTime.now());
  }
}
