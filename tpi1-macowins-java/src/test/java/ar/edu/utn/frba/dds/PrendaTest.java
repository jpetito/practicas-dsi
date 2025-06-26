package ar.edu.utn.frba.dds.macowins;

import ar.edu.utn.frba.dds.macowins.prendas.Liquidacion;
import ar.edu.utn.frba.dds.macowins.prendas.Nueva;
import ar.edu.utn.frba.dds.macowins.prendas.Promocion;
import org.junit.jupiter.api.Test;
import ar.edu.utn.frba.dds.macowins.prendas.Prenda;


import static org.junit.jupiter.api.Assertions.*;

public class PrendaTest {

  @Test
  public void elTipoDeUnaCamisaNuevaEsCAMISA() {
    assertEquals("CAMISA", camisaNueva(200).getTipo());
  }

  @Test
  public void elTipoDeUnSacoEnLiquidacionEsSACO() {
    assertEquals("SACO", sacoEnLiquidacion(200).getTipo());
  }

  @Test
  public void elPrecioDeUnaCamisaNuevaEsSuPrecioBase() {
    assertEquals(4000, camisaNueva(4000).precio());
    assertEquals(5000, camisaNueva(5000).precio());
  }

  @Test
  public void elPrecioDeUnSacoEnLiquidacionEsSuLaMitadDeSuPrecioBase() {
    assertEquals(1500, sacoEnLiquidacion(3000).precio());
    assertEquals(4000, sacoEnLiquidacion(8000).precio());
  }

  @Test
  public void elPrecioDeUnPantalonEnPromocionEsSuPrecioBaseMenosSuDecuento() {
    assertEquals(1300, pantalonEnPromocion(1500, 200).precio());
    assertEquals(1400, pantalonEnPromocion(1500, 100).precio());
  }

  private Prenda pantalonEnPromocion(int precioBase, int descuento) {
    return new Prenda("PANTALON", precioBase, new Promocion(descuento));
  }


  private Prenda camisaNueva(int precioBase) {
    return new Prenda("CAMISA", precioBase, new Nueva());
  }

  private Prenda sacoEnLiquidacion(int precioBase) {
    return new Prenda("SACO", precioBase, new Liquidacion());
  }

}
