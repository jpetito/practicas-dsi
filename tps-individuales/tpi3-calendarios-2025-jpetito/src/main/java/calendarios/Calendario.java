package calendarios;

import calendarios.eventos.Evento;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Calendario {
  private final List<Evento> eventos = new ArrayList<Evento>();

  // --- Getter ---

  public List<Evento> getEventos() {
    return new ArrayList<>(eventos);
  }

  // --- Metodos ---

  public void agendar(Evento evento) {
    eventos.add(evento);
  }

  public boolean estaAgendado(Evento evento) {
    return eventos.contains(evento);
  }

  public List<Evento> eventosEntreFechas(LocalDateTime initio, LocalDateTime fin) {
    return eventos.stream().flatMap(e -> e.repeticionesEntre(initio, fin).stream()).toList();
  }

  public List<Evento> eventosSolapadosCon(Evento evento) {
    return eventos.stream().filter(evento::estaSolapadoCon).toList();
  }

  public Evento proximoEvento() {
    return eventos.stream()
        .filter(e -> e.getInicio().isAfter(LocalDateTime.now()))
        .min(Comparator.comparing(Evento::getInicio))
        .orElse(null);
  }

  private boolean estaEntre(LocalDateTime fecha, LocalDateTime inicio, LocalDateTime fin) {
    return fecha.isAfter(inicio) && fecha.isBefore(fin);
  }

  public void enviarRecordatorio(String email) {
    eventos.forEach(e -> e.enviarRecordatorio(email));
  }
}
