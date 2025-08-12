package org.example.servicioMeteorologico;

import java.util.List;
import java.util.Map;

public interface ServicioMeteorologico {
  Map<String, Object> getCondicionesClimaticas();

  int getTemperaturaEnFarenheit();

  int getTemperaturaEnCelsius();

  List<String> getAlertasMeteorologicas();
}
