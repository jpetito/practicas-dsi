package calendarios.eventos;

import calendarios.Ubicacion;
import calendarios.Usuario;
import calendarios.eventos.repeticion.Repeticion;
import calendarios.servicios.ShemailLib;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class EventoRecurrente extends Evento {
  private LocalDateTime proximaIteracion;
  private final Repeticion repeticion;

  // --- Constructores ---

  public EventoRecurrente(
      String nombre,
      LocalDateTime inicio,
      Duration duracion,
      Ubicacion ubicacion,
      List<Usuario> invitados,
      Repeticion repeticion,
      ShemailLib emailer) {
    super(nombre, inicio, duracion, ubicacion, invitados, emailer);
    this.proximaIteracion = inicio;
    this.repeticion = repeticion;
  }

  public EventoRecurrente(
      String nombre,
      LocalDateTime inicio,
      Duration duracion,
      Ubicacion ubicacion,
      Repeticion repeticion,
      ShemailLib emailer) {
    super(nombre, inicio, duracion, ubicacion, emailer);
    this.proximaIteracion = inicio;
    this.repeticion = repeticion;
  }

  // --- Getters ---

  public LocalDateTime getProximaIteracion() {
    if (proximaIteracion.isBefore(LocalDateTime.now())) {
      proximaIteracion = repeticion.actualizar(proximaIteracion);
    }

    return proximaIteracion;
  }

  public LocalDateTime getUltimaIteracion() {
    return repeticion.ultimaIteracion(this);
  }

  public Repeticion getRepeticion() {
    return repeticion;
  }

  // --- Metodos ---

  @Override
  public List<Evento> repeticionesEntre(LocalDateTime inicio, LocalDateTime fin) {
    return repeticion.eventosEntre(this, inicio, fin);
  }

  @Override
  public Duration cuantoFalta() {
    return Duration.ofMinutes(LocalDateTime.now().until(getProximaIteracion(), ChronoUnit.MINUTES));
  }

  @Override
  public void enviarRecordatorio(String email) {
    Optional<LocalDateTime> recordatorioCoincidente =
        recordatorios.stream()
            .filter(r -> r.equals(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)))
            .findFirst();

    recordatorioCoincidente.ifPresent(
        localDateTime ->
            emailer.enviarMailA(
                email,
                "Recordatorio para " + nombre,
                "Te recordamos que faltan "
                    + Duration.between(localDateTime, getProximaIteracion())
                    + " para "
                    + nombre));
  }
}
