package calendarios;

import static java.time.temporal.ChronoUnit.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import calendarios.eventos.Evento;
import calendarios.eventos.EventoRecurrente;
import calendarios.eventos.repeticion.Diaria;
import calendarios.eventos.repeticion.Repeticion;
import calendarios.eventos.repeticion.Semanal;
import calendarios.servicios.GugleMapas;
import calendarios.servicios.PositionService;
import calendarios.servicios.ShemailLib;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * TODO: - Partí opcionalmente esta clase entre varias para hacerla más fácil de mantener. -
 * Modificá los tests si es necesario, pero se recomienda fuertemente mantener las interfaces
 * propuestas - Agregá más casos de prueba para satisfacer los requerimientos de cobertura
 */
class CalendariosTest {
  private PositionService positionService;
  private GugleMapas gugleMapas;
  private ShemailLib emailer;

  Ubicacion utnMedrano = new Ubicacion(-34.5984145, -58.4222096);
  Ubicacion utnCampus = new Ubicacion(-34.6591644, -58.4694862);

  @BeforeEach
  void initFileSystem() {
    positionService = mock(PositionService.class);
    gugleMapas = mock(GugleMapas.class);
    emailer = mock(ShemailLib.class);
  }

  // 1. Permitir que une usuarie tenga muchos calendarios

  @Test
  void uneUsuarieTieneMuchosCalendarios() {
    Usuario rene = crearUsuario("rene@gugle.com.ar");
    Calendario calendario = crearCalendarioVacio();

    rene.agregarCalendario(calendario);

    assertTrue(rene.tieneCalendario(calendario));
  }

  // 2. Permitir que en cada calendario se agenden múltiples eventos
  // 3. Permitir que los eventos registren nombre, fecha y hora de inicio y fin, ubicación,
  // invitades (otros usuaries)

  @Test
  void unEventoPuedeTenerMultiplesInvitades() {
    Usuario franco = crearUsuario("franco@gugle.com.ar");
    Usuario gaston = crearUsuario("gaston@gugle.com.ar");

    Evento evento =
        crearEventoSimpleEnCampus(
            "Primer parcial", LocalDateTime.of(2025, 7, 11, 19, 0), Duration.ofHours(4));

    evento.invitar(franco);
    evento.invitar(gaston);

    assertTrue(evento.estaInvitado(franco));
    assertTrue(evento.estaInvitado(gaston));
  }

  @Test
  void unCalendarioPermiteAgendarUnEvento() {
    Calendario calendario = new Calendario();

    Evento seguimientoDeTP =
        crearEventoSimpleEnMedrano(
            "Seguimiento de TP", LocalDateTime.of(2021, 10, 1, 15, 30), Duration.of(30, MINUTES));
    calendario.agendar(seguimientoDeTP);

    assertTrue(calendario.estaAgendado(seguimientoDeTP));
  }

  @Test
  void unCalendarioPermiteAgendarDosEvento() {
    Calendario calendario = new Calendario();
    LocalDateTime inicio = LocalDateTime.of(2021, 10, 1, 15, 30);

    Evento seguimientoDeTPA =
        crearEventoSimpleEnMedrano("Seguimiento de TPA", inicio, Duration.of(30, MINUTES));
    Evento practicaParcial =
        crearEventoSimpleEnMedrano(
            "Practica para el primer parcial", inicio.plusMinutes(60), Duration.of(90, MINUTES));

    calendario.agendar(seguimientoDeTPA);
    calendario.agendar(practicaParcial);

    assertTrue(calendario.estaAgendado(seguimientoDeTPA));
    assertTrue(calendario.estaAgendado(practicaParcial));
  }

  // 4. Permitir listar los próximos eventos entre dos fechas

  @Test
  void sePuedeListarUnEventoEntreDosFechasParaUnCalendario() {
    // Nota: Esto es opcional pero puede ayudar a resolver el siguiente item.
    // Borrar este test si no se utiliza

    Calendario calendario = new Calendario();
    Evento tpRedes =
        crearEventoSimpleEnMedrano(
            "TP de Redes", LocalDateTime.of(2020, 4, 3, 16, 0), Duration.of(2, HOURS));

    calendario.agendar(tpRedes);

    List<Evento> eventos =
        calendario.eventosEntreFechas(
            LocalDate.of(2020, 4, 1).atStartOfDay(), LocalDate.of(2020, 4, 4).atStartOfDay());

    assertEquals(eventos, Arrays.asList(tpRedes));
  }

  @Test
  void sePuedeListarUnEventoEntreDosFechasParaUneUsuarie() {
    Usuario rene = crearUsuario("rene@gugle.com.ar");
    Calendario calendario = new Calendario();
    rene.agregarCalendario(calendario);

    Evento tpRedes =
        crearEventoSimpleEnMedrano(
            "TP de Redes", LocalDateTime.of(2020, 4, 3, 16, 0), Duration.of(2, HOURS));

    calendario.agendar(tpRedes);

    List<Evento> eventos =
        rene.eventosEntreFechas(
            LocalDate.of(2020, 4, 1).atStartOfDay(), LocalDate.of(2020, 4, 4).atStartOfDay());

    assertEquals(eventos, Arrays.asList(tpRedes));
  }

  @Test
  void noSeListaUnEventoSiNoEstaEntreLasFechasIndicadasParaUneUsuarie() {
    Usuario dani = crearUsuario("dani@gugle.com.ar");
    Calendario calendario = new Calendario();
    dani.agregarCalendario(calendario);

    Evento tpRedes =
        crearEventoSimpleEnMedrano(
            "TP de Redes", LocalDateTime.of(2020, 4, 3, 16, 0), Duration.of(1, HOURS));

    calendario.agendar(tpRedes);

    List<Evento> eventos =
        dani.eventosEntreFechas(
            LocalDate.of(2020, 5, 8).atStartOfDay(), LocalDate.of(2020, 5, 16).atStartOfDay());

    assertTrue(eventos.isEmpty());
  }

  @Test
  void sePuedenListarMultiplesEventoEntreDosFechasParaUneUsuarieConCoincidenciaParcial() {
    Usuario usuario = crearUsuario("rene@gugle.com.ar");
    Calendario calendario = new Calendario();
    usuario.agregarCalendario(calendario);

    Evento tpRedes =
        crearEventoSimpleEnMedrano(
            "TP de Redes", LocalDateTime.of(2020, 4, 3, 16, 0), Duration.of(2, HOURS));
    Evento tpDeGestion =
        crearEventoSimpleEnMedrano(
            "TP de Gestión", LocalDateTime.of(2020, 4, 5, 18, 30), Duration.of(2, HOURS));
    Evento tpDeDds =
        crearEventoSimpleEnMedrano(
            "TP de DDS", LocalDateTime.of(2020, 4, 12, 16, 0), Duration.of(2, HOURS));

    calendario.agendar(tpRedes);
    calendario.agendar(tpDeGestion);
    calendario.agendar(tpDeDds);

    List<Evento> eventos =
        usuario.eventosEntreFechas(
            LocalDate.of(2020, 4, 1).atStartOfDay(), LocalDate.of(2020, 4, 6).atStartOfDay());

    assertEquals(eventos, Arrays.asList(tpRedes, tpDeGestion));
  }

  @Test
  void sePuedenListarMultiplesEventoEntreDosFechasParaUneUsuarieConCoincidenciaTotal() {
    Usuario juli = crearUsuario("juli@gugle.com.ar");
    Calendario calendario = new Calendario();
    juli.agregarCalendario(calendario);

    Evento tpRedes =
        crearEventoSimpleEnMedrano(
            "TP de Redes", LocalDateTime.of(2020, 4, 3, 16, 0), Duration.of(2, HOURS));
    Evento tpDeGestion =
        crearEventoSimpleEnMedrano(
            "TP de Gestión", LocalDateTime.of(2020, 4, 5, 18, 30), Duration.of(30, MINUTES));
    Evento tpDeDds =
        crearEventoSimpleEnMedrano(
            "TP de DDS", LocalDateTime.of(2020, 4, 12, 16, 0), Duration.of(1, HOURS));

    calendario.agendar(tpRedes);
    calendario.agendar(tpDeGestion);
    calendario.agendar(tpDeDds);

    List<Evento> eventos =
        juli.eventosEntreFechas(
            LocalDate.of(2020, 4, 1).atStartOfDay(), LocalDateTime.of(2020, 4, 12, 21, 0));

    assertEquals(eventos, Arrays.asList(tpRedes, tpDeGestion, tpDeDds));
  }

  @Test
  void sePuedenListarEventosDeMultiplesCalendarios() {
    Usuario juli = crearUsuario("juli@gugle.com.ar");

    Calendario calendarioFacultad = new Calendario();
    juli.agregarCalendario(calendarioFacultad);

    Calendario calendarioLaboral = new Calendario();
    juli.agregarCalendario(calendarioLaboral);

    Evento eventoFacultad =
        crearEventoSimpleEnCampus(
            "Primer parcial", LocalDateTime.of(2025, 7, 11, 19, 0), Duration.ofHours(4));
    Evento eventoLaboral =
        crearEventoSimpleEnCampus(
            "No se, soy un vago", LocalDateTime.of(2025, 9, 18, 21, 0), Duration.ofHours(4));

    calendarioFacultad.agendar(eventoFacultad);
    calendarioLaboral.agendar(eventoLaboral);

    List<Evento> eventos =
        juli.eventosEntreFechas(
            LocalDateTime.of(2025, 7, 1, 0, 0), LocalDateTime.of(2025, 10, 1, 0, 0));
    assertTrue(eventos.contains(eventoFacultad) && eventos.contains(eventoLaboral));
  }

  // 5. Permitir saber cuánto falta para un cierto calendarios.evento (por ejemplo, 15 horas)

  @Test
  void unEventoSabeCuantoFalta() {
    LocalDateTime inicio = LocalDateTime.now().plusDays(60);
    Evento parcialDds = crearEventoSimpleEnMedrano("Parcial DDS", inicio, Duration.of(2, HOURS));

    assertTrue(parcialDds.cuantoFalta().compareTo(Duration.of(60, ChronoUnit.DAYS)) <= 0);
    assertTrue(parcialDds.cuantoFalta().compareTo(Duration.of(59, ChronoUnit.DAYS)) >= 0);
  }

  // 7. Permitir agendar eventos con repeticiones, con una frecuencia diaria, semanal, mensual o
  // anual

  @Test
  void sePuedenAgendarYListarEventosRecurrrentes() {
    Usuario usuario = crearUsuario("rene@gugle.com.ar");

    Calendario calendario = new Calendario();

    EventoRecurrente evento =
        crearEventoRecurrenteEnCampus(
            "Parcial DDS",
            LocalDateTime.of(2020, 9, 1, 0, 0),
            Duration.ofMinutes(45),
            new Semanal(1));

    calendario.agendar(evento);

    usuario.agregarCalendario(calendario);

    List<Evento> eventos =
        usuario.eventosEntreFechas(
            LocalDateTime.of(2020, 9, 14, 9, 0), LocalDateTime.of(2020, 9, 28, 21, 0));

    assertEquals(2, eventos.size());
  }

  @Test
  void unEventoRecurrenteSabeCuantoFaltaParaSuProximaRepeticion() {
    EventoRecurrente unRecurrente =
        crearEventoRecurrenteEnCampus(
            "Parcial DDS",
            LocalDateTime.now().minusHours(1),
            Duration.ofMinutes(45),
            new Diaria(15));

    assertEquals(14, unRecurrente.cuantoFalta().toDays());

    assertTrue(unRecurrente.cuantoFalta().compareTo(Duration.of(15, ChronoUnit.DAYS)) <= 0);
    assertTrue(unRecurrente.cuantoFalta().compareTo(Duration.of(14, ChronoUnit.DAYS)) >= 0);
  }

  // 6. Permitir saber si dos eventos están solapado, y en tal caso, con qué otros eventos del
  // calendario

  @Test
  void sePuedeSaberSiUnEventoEstaSolapadoCuandoEstaParcialmenteIncluido() {
    Evento recuperatorioSistemasDeGestion =
        crearEventoSimpleEnMedrano(
            "Recuperatorio Sistemas de Gestion",
            LocalDateTime.of(2021, 6, 19, 9, 0),
            Duration.of(2, HOURS));
    Evento tpOperativos =
        crearEventoSimpleEnMedrano(
            "Entrega de Operativos", LocalDateTime.of(2021, 6, 19, 10, 0), Duration.of(2, HOURS));

    assertTrue(recuperatorioSistemasDeGestion.estaSolapadoCon(tpOperativos));
    assertTrue(tpOperativos.estaSolapadoCon(recuperatorioSistemasDeGestion));
  }

  @Test
  void sePuedeSaberSiUnEventoEstaSolapadoCuandoEstaTotalmenteIncluido() {
    Evento recuperatorioSistemasDeGestion =
        crearEventoSimpleEnMedrano(
            "Recuperatorio Sistemas de Gestion",
            LocalDateTime.of(2021, 6, 19, 9, 0),
            Duration.of(4, HOURS));
    Evento tpOperativos =
        crearEventoSimpleEnMedrano(
            "Entrega de Operativos", LocalDateTime.of(2021, 6, 19, 10, 0), Duration.of(2, HOURS));

    assertTrue(recuperatorioSistemasDeGestion.estaSolapadoCon(tpOperativos));
    assertTrue(tpOperativos.estaSolapadoCon(recuperatorioSistemasDeGestion));
  }

  @Test
  void sePuedeSaberSiUnEventoEstaSolapadoCuandoNoEstaSolapado() {
    Evento recuperatorioSistemasDeGestion =
        crearEventoSimpleEnMedrano(
            "Recuperatorio Sistemas de Gestion",
            LocalDateTime.of(2021, 6, 19, 9, 0),
            Duration.of(3, HOURS));
    Evento tpOperativos =
        crearEventoSimpleEnMedrano(
            "Entrega de Operativos", LocalDateTime.of(2021, 6, 19, 18, 0), Duration.of(2, HOURS));

    assertFalse(recuperatorioSistemasDeGestion.estaSolapadoCon(tpOperativos));
    assertFalse(tpOperativos.estaSolapadoCon(recuperatorioSistemasDeGestion));
  }

  @Test
  void sePuedeSaberConQueEventosEstaSolapado() {
    Evento recuperatorioSistemasDeGestion =
        crearEventoSimpleEnMedrano(
            "Recuperatorio Sistemas de Gestion",
            LocalDateTime.of(2021, 6, 19, 9, 0),
            Duration.of(2, HOURS));
    Evento tpOperativos =
        crearEventoSimpleEnMedrano(
            "Entrega de Operativos", LocalDateTime.of(2021, 6, 19, 10, 0), Duration.of(2, HOURS));
    Evento tramiteEnElBanco =
        crearEventoSimpleEnMedrano(
            "Tramite en el banco", LocalDateTime.of(2021, 6, 19, 9, 0), Duration.of(4, HOURS));

    Calendario calendario = crearCalendarioVacio();

    calendario.agendar(recuperatorioSistemasDeGestion);
    calendario.agendar(tpOperativos);

    assertEquals(
        Arrays.asList(recuperatorioSistemasDeGestion, tpOperativos),
        calendario.eventosSolapadosCon(tramiteEnElBanco));
  }

  // 9. Permitir asignarle a un evento varios recordatorios, que se enviarán cuando falte un cierto
  // tiempo

  @Test
  void sePuedenAgregarRecordatorios() {
    Evento evento =
        crearEventoSimpleEnCampus(
            "Parcial DDS", LocalDateTime.now().plusMinutes(30), Duration.ofHours(4));
    evento.agregarRecordatorio(LocalDateTime.now());

    assertEquals(1, evento.getRecordatorios().size());
  }

  @Test
  void siNoEsHoraDeEjecutarUnRecordatorioEntoncesNoPasaNada() {
    Usuario usuario = new Usuario("barla@gugle.com.ar", positionService, gugleMapas);
    Calendario calendario = new Calendario();
    Evento evento =
        crearEventoSimpleEnCampus(
            "Parcial DDS", LocalDateTime.now().plusMinutes(30), Duration.ofHours(4));
    evento.agregarRecordatorio(LocalDateTime.now().plusMinutes(10));
    calendario.agendar(evento);
    usuario.agregarCalendario(calendario);

    usuario.recordatorio();

    verify(emailer, never()).enviarMailA(any(), any(), any());
  }

  @Test
  void siEsHoraDeEjecutarUnRecordatorioEntoncesSeEnviaElMail() {
    Usuario usuario = new Usuario("barla@gugle.com.ar", positionService, gugleMapas);
    Calendario calendario = new Calendario();
    Evento evento =
        crearEventoSimpleEnCampus(
            "Parcial DDS", LocalDateTime.now().plusMinutes(30), Duration.ofHours(4));
    evento.agregarRecordatorio(LocalDateTime.now());
    calendario.agendar(evento);
    usuario.agregarCalendario(calendario);

    usuario.recordatorio();

    verify(emailer).enviarMailA(any(), any(), any());
  }

  // 8. Permitir saber si le usuarie llega al evento más próximo a tiempo, tomando en cuenta la
  // ubicación actual de le usuarie y destino.

  @Test
  void llegaATiempoAlProximoEventoCuandoNoHayEventos() {
    Usuario feli = crearUsuario("feli@gugle.com.ar");

    assertTrue(feli.llegaAlProximoEvento());
  }

  @Test
  void llegaATiempoAlProximoEventoCuandoHayUnEventoCercano() {
    Usuario feli = crearUsuario("feli@gugle.com.ar");
    Calendario calendario = crearCalendarioVacio();
    feli.agregarCalendario(calendario);

    calendario.agendar(
        crearEventoSimpleEnMedrano(
            "Parcial", LocalDateTime.now().plusMinutes(30), Duration.of(2, HOURS)));

    when(positionService.ubicacionActual("feli@gugle.com.ar")).thenReturn(utnMedrano);
    when(gugleMapas.tiempoEstimadoHasta(utnMedrano, utnMedrano)).thenReturn(Duration.ofMinutes(0));

    assertTrue(feli.llegaAlProximoEvento());
  }

  @Test
  void noLlegaATiempoAlProximoEventoCuandoHayUnEventoFísicamenteLejano() {
    Usuario feli = crearUsuario("feli@gugle.com.ar");
    Calendario calendario = crearCalendarioVacio();
    feli.agregarCalendario(calendario);

    when(positionService.ubicacionActual("feli@gugle.com.ar")).thenReturn(utnCampus);
    when(gugleMapas.tiempoEstimadoHasta(utnCampus, utnMedrano)).thenReturn(Duration.ofMinutes(31));

    calendario.agendar(
        crearEventoSimpleEnMedrano(
            "Parcial", LocalDateTime.now().plusMinutes(30), Duration.of(2, HOURS)));

    assertFalse(feli.llegaAlProximoEvento());
  }

  @Test
  void llegaATiempoAlProximoEventoCuandoHayUnEventoCercanoAunqueAlSiguienteNoLlegue() {
    Usuario feli = crearUsuario("feli@gugle.com.ar");
    Calendario calendario = crearCalendarioVacio();
    feli.agregarCalendario(calendario);

    when(positionService.ubicacionActual("feli@gugle.com.ar")).thenReturn(utnMedrano);
    when(gugleMapas.tiempoEstimadoHasta(utnMedrano, utnMedrano)).thenReturn(Duration.ofMinutes(0));
    when(gugleMapas.tiempoEstimadoHasta(utnMedrano, utnCampus)).thenReturn(Duration.ofMinutes(90));

    calendario.agendar(
        crearEventoSimpleEnMedrano(
            "Parcial", LocalDateTime.now().plusMinutes(30), Duration.of(3, HOURS)));
    calendario.agendar(
        crearEventoSimpleEnCampus(
            "Final", LocalDateTime.now().plusMinutes(45), Duration.of(1, HOURS)));

    assertTrue(feli.llegaAlProximoEvento());
  }

  /**
   * @return une usuarie con el mail dado
   */
  Usuario crearUsuario(String email) {
    return new Usuario(email, positionService, gugleMapas);
  }

  /*
   * @return Un calendario sin ningún evento agendado aún
   */

  Calendario crearCalendarioVacio() {
    return new Calendario();
  }

  Evento crearEventoSimpleEnMedrano(String nombre, LocalDateTime inicio, Duration duracion) {
    return crearEventoSimple(nombre, inicio, duracion, utnMedrano);
  }

  Evento crearEventoSimpleEnCampus(String nombre, LocalDateTime inicio, Duration duracion) {
    return crearEventoSimple(nombre, inicio, duracion, utnCampus);
  }

  EventoRecurrente crearEventoRecurrenteEnCampus(
      String nombre, LocalDateTime inicio, Duration duracion, Repeticion repeticion) {
    return crearEventoRecurrente(nombre, inicio, duracion, utnCampus, repeticion);
  }

  EventoRecurrente crearEventoRecurrente(
      String nombre,
      LocalDateTime inicio,
      Duration duracion,
      Ubicacion ubicacion,
      Repeticion repeticion) {
    return new EventoRecurrente(nombre, inicio, duracion, ubicacion, repeticion, emailer);
  }

  /**
   * @return un evento sin invtades que no se repite, que tenga el nombre, fecha de inicio y fin,
   *     ubicación dados
   */
  Evento crearEventoSimple(
      String nombre, LocalDateTime inicio, Duration duracion, Ubicacion ubicacion) {
    return new Evento(nombre, inicio, duracion, ubicacion, emailer);
  }
}
