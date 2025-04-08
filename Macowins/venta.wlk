import prenda.*

class Venta{
    var prendas = #{}

    var property fecha

    method esDel(dia){
        return fecha == dia
    }

    method totalDeLaVenta(){
        return prendas.sum({prenda => prenda.precioPrenda()})
    }

    method cantidadDePrendas(){
        return prendas.size()
    }

}

class VentaEfectivo inherits Venta{

}

class VentaTarjeta inherits Venta{
    var cantCuotas
    var coeficienteFijo

    method totalDeLaVenta(){
        return super() + (cantCuotas * coeficienteFijo + self.sumaPorPrendas())
    }

    method sumaPorPrendas(){
        return prendas.sum({prenda => prenda.precioPrenda() * 0.01})
    }
}