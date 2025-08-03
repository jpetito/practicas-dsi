package calendarios;

import calendarios.eventos.Evento;
import calendarios.servicios.GugleMapas;
import calendarios.servicios.PositionService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Usuario {
  private final List<Calendario> calendarios = new ArrayList<Calendario>();
  private final PositionService ubicacion;
  private final GugleMapas gugleMapas;
  private final String email;

  // --- Constructor

  public Usuario(String email, PositionService ubicacion, GugleMapas gugleMapas) {
    this.email = email;
    this.ubicacion = ubicacion;
    this.gugleMapas = gugleMapas;
  }

  // --- Metodos ---

  public void agregarCalendario(Calendario calendario) {
    calendarios.add(calendario);
  }

  public List<Evento> eventosEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
    return calendarios.stream().flatMap(c -> c.eventosEntreFechas(inicio, fin).stream()).toList();
  }

  public boolean llegaAlProximoEvento() {
    Evento evento =
        calendarios.stream()
            .map(Calendario::proximoEvento)
            .min(Comparator.comparing(Evento::getInicio))
            .orElse(null);

    if (evento != null) {
      return gugleMapas
              .tiempoEstimadoHasta(ubicacion.ubicacionActual(email), evento.getUbicacion())
              .compareTo(evento.cuantoFalta())
          <= 0;
    }

    return true;
  }

  public boolean tieneCalendario(Calendario calendario) {
    return calendarios.contains(calendario);
  }

  public void recordatorio() {
    calendarios.forEach(c -> c.enviarRecordatorio(email));
  }
}
