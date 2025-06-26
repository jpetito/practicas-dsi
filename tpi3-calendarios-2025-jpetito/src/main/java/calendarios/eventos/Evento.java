package calendarios.eventos;

import calendarios.Ubicacion;
import calendarios.Usuario;
import calendarios.servicios.ShemailLib;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Evento {
  protected final String nombre;
  protected final LocalDateTime inicio;
  protected final Duration duracion;
  protected final Ubicacion ubicacion;
  protected final List<Usuario> invitados;
  protected final List<LocalDateTime> recordatorios = new ArrayList<>();
  protected final ShemailLib emailer;

  // --- Constructores ---

  public Evento(
      String nombre,
      LocalDateTime inicio,
      Duration duracion,
      Ubicacion ubicacion,
      List<Usuario> invitados,
      ShemailLib emailer) {
    this.nombre = nombre;
    this.inicio = inicio;
    this.duracion = duracion;
    this.ubicacion = ubicacion;
    this.invitados = new ArrayList<>(invitados);
    this.emailer = emailer;
  }

  public Evento(
      String nombre,
      LocalDateTime inicio,
      Duration duracion,
      Ubicacion ubicacion,
      ShemailLib emailer) {
    this.nombre = nombre;
    this.inicio = inicio;
    this.duracion = duracion;
    this.ubicacion = ubicacion;
    this.invitados = new ArrayList<>();
    this.emailer = emailer;
  }

  // --- Getters ---

  public LocalDateTime getInicio() {
    return inicio;
  }

  public Duration getDuracion() {
    return duracion;
  }

  public LocalDateTime getFin() {
    return inicio.plus(duracion);
  }

  public Ubicacion getUbicacion() {
    return ubicacion;
  }

  public String getNombre() {
    return nombre;
  }

  public List<Usuario> getInvitados() {
    return new ArrayList<>(invitados);
  }

  public List<LocalDateTime> getRecordatorios() {
    return new ArrayList<>(recordatorios);
  }

  public ShemailLib getEmailer() {
    return emailer;
  }

  // --- Metodos ---

  public Duration cuantoFalta() {
    // Este es un ejemplo de cómo se puede obtener una duración
    // Modificar en caso de que sea necesario
    return Duration.ofMinutes(LocalDateTime.now().until(getInicio(), ChronoUnit.MINUTES));
  }

  public boolean estaSolapadoCon(Evento otro) {
    return estaEntre(otro.getInicio(), otro.getFin());
  }

  public boolean estaEntre(LocalDateTime inicio, LocalDateTime fin) {
    return this.inicio.isBefore(fin) && inicio.isBefore(this.getFin());
  }

  public List<Evento> repeticionesEntre(LocalDateTime inicio, LocalDateTime fin) {
    if (estaEntre(inicio, fin)) {
      return List.of(this);
    }
    return List.of();
  }

  public void invitar(Usuario usuario) {
    invitados.add(usuario);
  }

  public boolean estaInvitado(Usuario usuario) {
    return invitados.contains(usuario);
  }

  public void agregarRecordatorio(LocalDateTime recordatorio) {
    recordatorios.add(recordatorio);
  }

  public void enviarRecordatorio(String email) {
    Optional<LocalDateTime> recordatorioCoincidente =
        recordatorios.stream()
            .filter(
                r ->
                    r.truncatedTo(ChronoUnit.MINUTES)
                        .equals(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)))
            .findFirst();

    recordatorioCoincidente.ifPresent(
        localDateTime ->
            emailer.enviarMailA(
                email,
                "Recordatorio para " + nombre,
                "Te recordamos que faltan "
                    + Duration.between(localDateTime, inicio)
                    + " para "
                    + nombre));
  }
}
