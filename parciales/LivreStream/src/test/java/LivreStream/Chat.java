package LivreStream;

import java.util.List;

public class Chat {
  private List<String> mensajes;

  public void agregarMensaje(String mensaje){
    mensajes.add(mensaje);
  }

  public List<String> listarMensajes(){
    return mensajes;
  }
}
