import venta.*
import prenda.*

object macowins{
    var ventas = #{}

    method ventasDel(dia){
        return ventas.filter({venta => venta.esDel(dia)})
    }

    method ganancias(dia){
        return self.ventasDel(dia).sum({venta => venta.totalDeLaVenta()})
    }

}