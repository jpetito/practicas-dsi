package org.example.servicioMeteorologico;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.api.AccuWeatherAPI;

public class ServicioMeteorologicoAccuWeather implements ServicioMeteorologico {
  private final Map<String, RespuestaAccuWeather> ultimasRespuestas = new HashMap<>();
  private final AccuWeatherAPI api;
  private final Duration periodoDeValidez;
  private final String ubicacion;

  public ServicioMeteorologicoAccuWeather(
      AccuWeatherAPI api, Duration periodoDeValidez, String ubicacion) {
    this.api = api;
    this.ubicacion = ubicacion;
    this.periodoDeValidez = periodoDeValidez;
  }

  // getters

  public Map<String, Object> getCondicionesClimaticas() {
    validar();
    return ultimasRespuestas.get(ubicacion).getEstadoDelTiempo();
  }

  public List<String> getAlertasMeteorologicas() {
    validar();
    return ultimasRespuestas.get(ubicacion).getAlertasMeteorologicas().get("CurrentAlerts");
  }

  public int getTemperaturaEnFarenheit() {
    Map<String, Object> temperatura;
    temperatura = (Map<String, Object>) getCondicionesClimaticas().get("Temperature");
    return (int) temperatura.get("Value");
  }

  public int getTemperaturaEnCelsius() {
    return (getTemperaturaEnFarenheit() - 32) * 5 / 9;
  }

  // metodos

  private void validar() {
    if (!ultimasRespuestas.containsKey(ubicacion) || ultimasRespuestas.get(ubicacion).expiro()) {
      actualizar();
    }
  }

  private void actualizar() {
    if (ultimasRespuestas.get(ubicacion) != null) {
      ultimasRespuestas.remove(ubicacion);
    }

    ultimasRespuestas.put(
        ubicacion,
        new RespuestaAccuWeather(
            api.getWeather(ubicacion).get(0), api.getAlerts(ubicacion), proximaExpiracion()));
  }

  private LocalDateTime proximaExpiracion() {
    return LocalDateTime.now().plus(periodoDeValidez);
  }
}
