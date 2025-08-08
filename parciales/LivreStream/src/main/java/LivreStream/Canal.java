package LivreStream;

import java.util.ArrayList;
import java.util.List;

public class Canal {
  private Stream streamEnVivo = null;
  private List<Stream> listadoStreams = new ArrayList<>();
  private List<Canal> suscriptores = new ArrayList<>();
  private List<Integer> MuestrasApoyo= new ArrayList<>();

  public Canal() {
    RepositorioStreams.agregarCanal(this);
  }

  // Metodos

  public void recibirSuscripcion(Canal canal) {
    suscriptores.add(canal);
  }

  public void perderSuscripcion(Canal canal) {
    suscriptores.remove(canal);
  }

  public void recibirApoyo(int numero){
    validarNumeroApoyo(numero);
    MuestrasApoyo.add(numero);
  }

  private void validarNumeroApoyo(Integer numero) {
    if (numero < 1 || numero > 10) {
      throw new RuntimeException("El apoyo debe ser un numero entre 1 y 10");
    }
  }

  private void finalizarTransmisionEnVivo() {
    if (streamEnVivo != null) {
      streamEnVivo.finalizarTransmision();
      listadoStreams.add(streamEnVivo);
      streamEnVivo = null;
    }
  }

  private void iniciarTransmisionEnVivo(Stream stream) {
    if(this.streamEnVivo != null){
      throw new IllegalStateException("Ya existe una transmisión en vivo en este canal.");
    }
    this.setTransmisionEnCurso(streamEnVivo);
  }

  public List<Stream> transmicionesHistoricas() {
    return new ArrayList<>(listadoStreams);
  }

  public List<Integer> getMuestrasDeApoyo() {
    return new ArrayList<>(MuestrasApoyo);
  }

  public List<Canal> suscripciones() {
    return new ArrayList<>(suscriptores);
  }

  public void agregarStreamHistorico(Stream s){
    listadoStreams.add(s);
  }

  public void setTransmisionEnCurso(Stream nueva){
    this.streamEnVivo = nueva;
  }

  public Stream getStreamEnVivo(){
    return streamEnVivo;
  }

}
