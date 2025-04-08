
class Prenda{
    const tipo
    var property precio
    var estado

    method precioPrenda() = estado.precioPorEstado(precio)

}

//estados de la prenda
class Estado{

    method precioPorEstado(precio)
}

class Nueva inherits Estado{

    override method precioPorEstado(precio) = precio

}

class Promocion inherits Estado{
    var valorFijo

    override method precioPorEstado(precio) = precio - valorFijo

}

class Liquidacion inherits Estado{
    
    override method precioPorEstado(precio) = precio * 0.5 //50% de descuento

}


//tipos de prenda
object saco {
  
}

object pantalon{

}

object camisa{

}