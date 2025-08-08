package LivreStream;

import java.time.LocalDateTime;
import java.util.List;

public class Stream {
  private final Canal canal;
  private final String titulo;
  private final LocalDateTime horaInicio;
  private LocalDateTime horaFin;
  private Integer participantesMax;
  private final Chat chat;
  private List<String> categorias;

  public Stream(Canal canal, String titulo, Chat chat) {
    this.canal = canal;
    this.titulo = titulo;
    this.chat = chat;
    this.horaInicio = LocalDateTime.now();
  }

  public void finalizarTransmision(){
    horaFin = LocalDateTime.now();
  }

}
