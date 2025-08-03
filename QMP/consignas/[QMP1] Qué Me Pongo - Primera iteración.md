# Qué Me Pongo

QuéMePongo es una empresa dedicada al armado de atuendos adecuados a las condiciones climáticas y preferencias de sus clientes. El servicio que provee se basa en tomar las prendas del guardarropas de une usuarie y generar diferentes combinaciones posibles que cubran las necesidades de les mismes en términos de distintos factores climáticos tales como temperatura, viento, sol, lluvia, etc. Asimismo, se busca que estos atuendos se adecuen de la mejor forma a las sensibilidades particulares respecto de dichos factores de cada usuarie y a sus gustos en el aspecto estético.

## **Primera Iteración**

Comenzaremos definiendo que un *atuendo* es una combinación de prendas que tiene sentido usar juntas. Algunos ejemplos de atuendos podrían ser:

* tus anteojos de sol favoritos, una remera de mangas cortas azul, el pantalón que te regaló tu mamá y unas zapatillas converse.  
* un pañuelo verde, una remera de mangas largas rayada, un pantalón de jean y un par de botas de cuero.  
* una musculosa de mickey, una pollera amarilla y unas crocs.

Como primer paso para generar los atuendos, les usuaries de QuéMePongo identificaron el siguiente requerimiento como principal:

| *Como usuarie de QuéMePongo, quiero poder cargar prendas válidas para generar atuendos con ellas.* |
| :---- |

Y luego, al consultar más sobre este requerimiento general, logramos descomponerlo con mayor detalle en los siguientes:

* Como usuarie de QuéMePongo, quiero especificar qué tipo de prenda estoy cargando (zapatos, camisa de mangas cortas, pantalón, etc).  
    
* Como usuarie de QuéMePongo, quiero identificar a qué categoría pertenece una prenda (parte superior, calzado, parte inferior, accesorios).  
    
* Como usuarie de QuéMePongo, quiero poder indicar de qué tela o material está hecha una prenda  
    
* Como usuarie de QuéMePongo, quiero poder indicar un color principal para mis prendas  
    
* Como usuarie de QuéMePongo, quiero poder indicar, si existe, un color secundario para mis prendas.  
    
* Como usuarie de QuéMePongo, quiero evitar que haya prendas sin tipo, tela, categoría o color primario.

* Como usuarie de QuéMePongo, quiero evitar que haya prendas cuya categoría no se condiga con su tipo. (Ej, una remera no puede ser calzado).