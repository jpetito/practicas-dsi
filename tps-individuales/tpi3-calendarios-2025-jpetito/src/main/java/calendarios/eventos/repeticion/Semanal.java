package calendarios.eventos.repeticion;

import calendarios.eventos.Evento;
import calendarios.eventos.EventoRecurrente;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Semanal implements Repeticion {
  private final int cantidad;

  public Semanal(int cantidad) {
    this.cantidad = cantidad;
  }

  public Semanal() {
    this.cantidad = 1;
  }

  @Override
  public LocalDateTime masUnaIteracion(LocalDateTime fecha) {
    return fecha.plusWeeks(cantidad);
  }

  @Override
  public List<Evento> eventosEntre(
      EventoRecurrente evento, LocalDateTime inicio, LocalDateTime fin) {
    List<Evento> eventos = new ArrayList<>();

    LocalDateTime fecha = evento.getInicio();

    while (fecha.isBefore(fin) || fecha == fin) {
      if (fecha.isAfter(inicio) || fecha == inicio) {
        eventos.add(
            new Evento(
                evento.getNombre(),
                fecha,
                evento.getDuracion(),
                evento.getUbicacion(),
                evento.getInvitados(),
                evento.getEmailer()));
      }

      fecha = masUnaIteracion(fecha);
    }

    return eventos.isEmpty() ? List.of() : eventos;
  }

  @Override
  public LocalDateTime actualizar(LocalDateTime fecha) {
    while (fecha.isBefore(LocalDateTime.now())) {
      fecha = fecha.plusWeeks(cantidad);
    }

    return fecha;
  }

  @Override
  public LocalDateTime ultimaIteracion(EventoRecurrente evento) {
    return evento.getProximaIteracion().minusWeeks(cantidad);
  }
}
