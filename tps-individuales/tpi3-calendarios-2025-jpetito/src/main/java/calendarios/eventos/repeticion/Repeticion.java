package calendarios.eventos.repeticion;

import calendarios.eventos.Evento;
import calendarios.eventos.EventoRecurrente;
import java.time.LocalDateTime;
import java.util.List;

public interface Repeticion {
  List<Evento> eventosEntre(EventoRecurrente evento, LocalDateTime inicio, LocalDateTime fin);

  LocalDateTime actualizar(LocalDateTime fecha);

  LocalDateTime ultimaIteracion(EventoRecurrente evento);

  LocalDateTime masUnaIteracion(LocalDateTime fecha);
}
