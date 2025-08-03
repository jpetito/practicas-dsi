# Patrones de Diseño  

Clasificación de los patrones de diseño

---

## 1. Patrones Creacionales  
Se centran en la **creación de objetos** de manera flexible y eficiente.  
Se utilizan cuando es necesario **controlar el proceso de instanciación**.  

### 🔹 Builder  JULI
Permite construir objetos complejos **paso a paso**.  
El patrón nos permite producir distintos tipos y representaciones de un objeto empleando el mismo código de construcción.  

- Útil cuando un objeto requiere una inicialización laboriosa con muchos parámetros.  
- Evita constructores enormes y difíciles de mantener.  
- Separa la lógica de construcción en clases llamadas **constructores**.  

```Java
// Al momento de construir una inscripción a materia con alumno, turno, comisión, etc

class Inscripción {
    String alumno;
    String materia;
    String turno;
    String sede;


    static class Builder {
        Inscripción i = new Inscripcion();


        Builder conAlumno(String a) { i.alumno = a; return this; }
        Builder conMateria(String m) { i.materia = m; return this; }
        Builder conTurno(String t) { i.turno = t; return this; }
        Builder conSede(String s) { i.sede = s; return this; }


        Inscripción build() { return i; }
    }
}


// Ejemplo de uso 
Inscripcion insc = new Inscripcion.Builder()
    .conAlumno("Matias")
    .conMateria("Diseño de Sistemas")
    .conTurno("Mañana")
    .build();

```

---

### 🔹 Factory Method  JULI
Define una **interfaz o método** para crear objetos, delegando a las subclases la decisión de qué tipo de objeto instanciar.  

- Se llama a un **método fábrica** en lugar de usar `new`.  
- Los objetos creados se llaman **productos**.  
- Permite centralizar la lógica de creación.  
- Las subclases pueden personalizar qué tipo de producto se crea **sin modificar el código cliente**.  
- Todos los productos deben compartir una **clase base o interfaz común**.  

```Java
// Cuándo quieres desacoplar la idea de crear varios tipos de materias como MateriaPresencial vs MateriaVirtual

abstract class Materia {
    abstract void dictarClase();}


class MateriaPresencial extends Materia {
    void dictarClase() { System.out.println("Clase en aula"); }}


class MateriaVirtual extends Materia {
    void dictarClase() { System.out.println("Clase por Zoom"); }}


class MateriaFactory {
    static Materia crear(String tipo) {
        return switch (tipo) {
            case "PRESENCIAL" -> new MateriaPresencial();
            case "VIRTUAL" -> new MateriaVirtual();
        default -> throw new IllegalArgumentException("TipoDesconocido");
        };
    }
}
        
// Ejemplo de uso 

Materia m1 = MateriaFactory.crear("PRESENCIAL");
Materia m2 = MateriaFactory.crear("VIRTUAL");
```

---

### 🔹 Singleton  JULI
Garantiza que una clase tenga **una única instancia** y proporciona un **punto de acceso global** a ella.  

Pasos típicos:  
1. Hacer privado el constructor para evitar el uso de `new`.  
2. Crear un **método estático** que:  
   - Cree la instancia la primera vez.  
   - Devuelva la misma instancia en llamadas posteriores.  

```Java
// Una clase que maneja el sistema de autenticación de usuarios

class GestorLogin {
    private static GestorLogin instancia;


    private GestorLogin() {}


    public static GestorLogin getInstancia() {
        if (instancia == null) instancia = new GestorLogin();
        return instancia;
    }
}

// Ejemplo de uso

GestorLogin login = GestorLogin.getInstancia();
login.autenticar("MATIAS", "unaContraseña");
```

---

### 🔹 Abstract Factory JULI

### Definición  
El **Abstract Factory** es un patrón creacional que permite crear **familias de objetos relacionados** sin especificar sus clases concretas.  
El cliente no usa `new` directamente, sino que pide los objetos a una **fábrica abstracta**, lo que da **desacoplamiento y coherencia**.  

### Ejemplo: Tienda Online  

* Queremos vender distintos tipos de productos en nuestra web.  
* Cada categoría (ej. Electrónica o Muebles) necesita **productos y accesorios coherentes**.

```Java
// Interfaces comunes
interface Producto { String getDescripcion(); }
interface Accesorio { String getDescripcion(); }

// Fábrica abstracta
interface TiendaFactory {
    Producto crearProducto();
    Accesorio crearAccesorio();
}

// Fábrica concreta: Electrónica
class ElectronicaFactory implements TiendaFactory {
    public Producto crearProducto() { return () -> "Notebook"; }
    public Accesorio crearAccesorio() { return () -> "Mouse"; }
}

// Fábrica concreta: Muebles
class MueblesFactory implements TiendaFactory {
    public Producto crearProducto() { return () -> "Silla"; }
    public Accesorio crearAccesorio() { return () -> "Almohadón"; }
}

// Uso en el cliente
TiendaFactory tienda = new ElectronicaFactory();
System.out.println(tienda.crearProducto().getDescripcion()); // Notebook
System.out.println(tienda.crearAccesorio().getDescripcion()); // Mouse
```

---


## 🔹 Prototype JULI

### Definición  
El **Prototype** es un patrón creacional que permite **crear nuevos objetos clonando un objeto existente** (el prototipo), en lugar de instanciarlo con `new`.  
Es útil cuando:  
- Crear un objeto desde cero es **costoso** o **complejo**.  
- Queremos copiar un objeto con muchas configuraciones o estados.  
- Necesitamos múltiples copias con pequeñas variaciones.  

Básicamente: en vez de construir desde cero, **copiamos un objeto ya preparado**.

### Ejemplo: Tienda Online  

Imaginá que tu tienda tiene un **producto base (Notebook configurada)** con varias especificaciones (RAM, disco, sistema operativo).  
En lugar de crear manualmente cada nueva notebook, podemos **clonar el prototipo** y modificar solo lo necesario.  

```java
interface Producto extends Cloneable {
    Producto clonar();
    String getDescripcion();
}

// Producto concreto
class Notebook implements Producto {
    private String descripcion;
    private double precio;

    public Notebook(String descripcion, double precio) {
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public Producto clonar() {
        return new Notebook(this.descripcion, this.precio);
    }

    public String getDescripcion() { return descripcion + " - $" + precio; }

    // Permite ajustes después del clon
    public void setPrecio(double precio) { this.precio = precio; }
}

// Uso
Notebook prototipo = new Notebook("Notebook básica 8GB RAM", 300000);

// Clonamos para ofrecer otra variante
Notebook oferta = (Notebook) prototipo.clonar();
oferta.setPrecio(280000);

System.out.println(prototipo.getDescripcion()); // Notebook básica 8GB RAM - $300000
System.out.println(oferta.getDescripcion());    // Notebook básica 8GB RAM - $280000

```

---

## 2. Patrones Estructurales  
Se enfocan en la **organización y composición de clases y objetos** para mejorar la arquitectura.  

### 🔹 Adapter JULI
Permite que dos clases con **interfaces incompatibles** trabajen juntas.  

- Se crea un objeto intermedio (**adaptador**) que actúa como traductor.  
- Casos de uso:  
  - Reutilizar clases existentes sin modificarlas.  
  - Integrar librerías externas con tu sistema.  
  - Unificar interfaces en sistemas grandes.  

```Java

class NotasAdapter implements SistemaNotas {
    SistemaNotasV1 viejo;

    public double obtenerNota(String alumno) {
        return viejo.getNotaPorLegajo(alumno);
    }
}

```
---

## 🔹 Decorator  JULI

El **patrón Decorator** sirve para **agregar responsabilidades extra a un objeto de forma dinámica**, sin modificar su código original.  
La idea es **envolver** un objeto dentro de otro que añade comportamiento.  

### Ejemplo Sencillo  
Imaginá que tenés un **café básico** y querés agregarle ingredientes sin tener que crear una clase distinta para cada combinación.  

```Java
// Clase base
interface Cafe {
    String getDescripcion();
    double getPrecio();
}

// Implementación concreta
class CafeSimple implements Cafe {
    public String getDescripcion() { return "Café simple"; }
    public double getPrecio() { return 50; }
}

// Decorator base
abstract class CafeDecorator implements Cafe {
    protected Cafe cafe;
    public CafeDecorator(Cafe cafe) { this.cafe = cafe; }
}

// Decoradores concretos
class ConLeche extends CafeDecorator {
    public ConLeche(Cafe cafe) { super(cafe); }
    public String getDescripcion() { return cafe.getDescripcion() + " con leche"; }
    public double getPrecio() { return cafe.getPrecio() + 20; }
}

class ConAzucar extends CafeDecorator {
    public ConAzucar(Cafe cafe) { super(cafe); }
    public String getDescripcion() { return cafe.getDescripcion() + " con azúcar"; }
    public double getPrecio() { return cafe.getPrecio() + 5; }
}

// Uso
Cafe pedido = new ConAzucar(new ConLeche(new CafeSimple()));
System.out.println(pedido.getDescripcion()); // Café simple con leche con azúcar
System.out.println(pedido.getPrecio());      // 75
```
---


### 🔹 Facade 
Proporciona una **interfaz simplificada** a una librería, framework o grupo complejo de clases.  

- Oculta la complejidad del sistema.  
- Útil cuando solo se necesita una parte de una librería extensa.  
- Mejora la **legibilidad y mantenibilidad** del código.  

---

## 3. Patrones de Comportamiento  
Gestionan la **comunicación e interacción entre objetos**, facilitando la asignación de responsabilidades.  

### 🔹 State  
Permite que un objeto cambie su comportamiento cuando su **estado interno** cambia.  

- Se crean clases separadas para cada estado.  
- Evita grandes bloques de condicionales.  
- Los estados pueden conocer a otros y definir transiciones.  

---

### 🔹 Strategy  JULI
Define una familia de algoritmos, encapsula cada uno en su propia clase y los hace **intercambiables**.  

- La clase **Contexto** almacena la estrategia.  
- Permite cambiar el algoritmo en **tiempo de ejecución**.  
- Todas las estrategias implementan la misma interfaz.  

---

### 🔹 Observer  
Establece una relación **uno-a-muchos**:  
cuando un objeto cambia de estado, notifica automáticamente a todos sus observadores.  

**Componentes:**  
- **Subject:** mantiene la lista de observadores y notifica cambios.  
- **Observer:** define la interfaz `update()` para reaccionar a cambios.  

**Ventajas:**  
- Desacopla el emisor del receptor.  
- Fácil de extender (nuevos observadores sin tocar el sujeto).  

**Desventajas:**  
- La notificación suele ser **sincrónica**.  
- Riesgo de bloqueos si un observador tarda mucho.  

---

### 🔹 Template Method  JULI
Define el **esqueleto de un algoritmo**, dejando que las subclases implementen algunos pasos específicos.  

- Útil cuando varios algoritmos comparten la misma estructura.  
- Favorece el **polimorfismo**.  

---

### 🔹 Command  
Encapsula una solicitud como un **objeto**, permitiendo ejecutar, almacenar, encolar o deshacer operaciones.  

**Estructura:**  
- **Command:** interfaz con `ejecutar()`.  
- **ConcreteCommand:** implementación concreta que delega la acción.  
- **Receiver:** quien realiza la acción real.  
- **Invoker:** quien invoca el comando.  
- **Client:** configura los comandos y sus receptores.  

**Ventajas:**  
- Desacopla el solicitante del ejecutor.  
- Facilita **undo/redo**, logging y macros.  

---

## Principios SOLID  
Propuestos por Robert C. Martin (**Uncle Bob**) para mejorar el diseño orientado a objetos.  

### 🔹 S – Single Responsibility Principle (SRP)  
Cada clase debe tener **una sola responsabilidad**.  
- Favorece la cohesión.  

### 🔹 O – Open/Closed Principle (OCP)  
Las clases deben estar **abiertas a la extensión** pero **cerradas a la modificación**.  
- Se logra con **herencia y polimorfismo**.  

### 🔹 L – Liskov Substitution Principle (LSP)  
Las subclases deben poder **reemplazar a la clase base** sin alterar el programa.  

### 🔹 I – Interface Segregation Principle (ISP)  
Los clientes no deben depender de interfaces que **no usan**.  
- Preferir interfaces pequeñas y específicas.  

### 🔹 D – Dependency Inversion Principle (DIP)  
Los módulos de alto nivel y bajo nivel deben depender de **abstracciones**, no de implementaciones.  
- Se implementa con **inyección de dependencias**.  

---

# Patrones de Comunicación Basados en Eventos  

## 🔹 Patrón Suscripción a Eventos  
Es un patrón donde un **sujeto** notifica a múltiples **observadores** cuando ocurre un evento.  
Los observadores se registran para recibir notificaciones.  

### Características  
- **Desacoplamiento más leve**: el sujeto conoce a los observadores mediante su registro.  
- **Notificación automática**: no requiere polling (consulta constante).  
- **Un evento → múltiples respuestas**: un solo cambio puede activar muchas acciones.  
- **Limitación**: no escala bien si se necesita persistencia o distribución entre procesos o redes.  

### Casos de uso  
- **Interfaces gráficas** (ej. botones y eventos).  
- **Flujos de datos**.  
- **Programación reactiva** (ej. RxJava, ReactiveX).  

---

## 🔹 Patrón Publicación-Suscripción (Pub/Sub)  
Es un patrón de **mensajería asíncrona y desacoplada** en el que los emisores (**publishers**) y receptores (**subscribers**) no se conocen directamente.  
Se comunican mediante **topics** (canales o temas).  

### Componentes  
- **Publisher (emisor)**: envía el mensaje.  
- **Subscriber (receptor)**: se suscribe a un topic y recibe los mensajes.  
- **Topic**: canal intermedio que conecta publishers y subscribers.  

### Características  
- **Desacoplamiento total**: publisher y subscriber no tienen relación directa.  
- **Escalabilidad natural**: múltiples publishers y subscribers por topic.  
- **Asincronía**: envío y recepción de mensajes sin bloqueo.  
- **Limitación**: no garantiza persistencia de mensajes (a menos que se combine con colas o buffers).  

### Casos de uso  
- Notificaciones **PUSH** en móviles.  
- Procesamiento de eventos de usuarios.  
- **Logs de auditoría**.  
- Comunicación entre **microservicios** en arquitecturas distribuidas.  

---

## Relación con Otros Conceptos  

### Observer  
- Versión orientada a objetos.  
- Adecuada para **poca escala**, dentro de una sola aplicación.  

### Pub/Sub  
- Versión orientada a **eventos y redes**.  
- Ideal para **entornos distribuidos** y escalables.  

### Broker  
- Componente que **intermedia** entre publishers y subscribers.  
- Recibe, filtra y distribuye los mensajes.  

### WebSockets  
- Tecnología para implementar **Pub/Sub en tiempo real** en aplicaciones web.  
- Permiten una conexión **persistente y bidireccional** entre navegador y backend.  
- Muy usados para transportar eventos en tiempo real y aplicar patrones como **Observer** o **Pub/Sub** entre frontend y backend.  

---

## 🔹 Patrón Broker  
Un **patrón arquitectónico** cuyo propósito es **desacoplar emisores y receptores** mediante un intermediario inteligente: el **broker**.  

### Funciones del Broker  
- Recibir mensajes.  
- Almacenarlos temporalmente.  
- Filtrarlos.  
- Enrutarlos hacia los subscribers correspondientes.  
- Distribuirlos según **topics internos**.  

### Características  
- Es la forma más común de implementar **Pub/Sub** en la práctica.  
- Permite un **desacoplamiento total** entre publisher y subscriber.  
- Útil cuando:  
  - Se requiere **persistencia**.  
  - Se necesita **alta escalabilidad**.  
  - Se cuenta con **mucho hardware** (indicio de que un broker es necesario).  

### Ejemplo de uso  
> “Los dispositivos deberán enviar sus datos recopilados cada cierto tiempo para que el sistema pueda procesarlos.”  
- Cada dispositivo actúa como **publisher**.  
- El broker centraliza, filtra y distribuye la información.  
- Los servicios interesados se suscriben como **subscribers**.  

---


---

## Callback en Automatización ⚙️🤖  

### Definición  
En el contexto de **automatización** (de procesos, pruebas o tareas), un **callback** es una función o rutina que se **ejecuta automáticamente cuando ocurre un evento o cuando finaliza una tarea programada**.  

👉 Es una forma de **programar reacciones automáticas** dentro de un flujo sin intervención manual.  

---

### Usos típicos en automatización  

1. **Notificaciones automáticas**  
   - Ejemplo: después de que un robot de RPA termina de procesar facturas, un callback envía un correo al área contable.  

2. **Flujos condicionales**  
   - Ejemplo: si una prueba automatizada falla, un callback se ejecuta para tomar una captura de pantalla y guardar un log.  

3. **Integraciones con otros sistemas**  
   - Ejemplo: un sistema de automatización de builds (CI/CD) ejecuta un callback cuando una compilación termina, para desplegar el software en un servidor.  

4. **Manejo de tareas asíncronas**  
   - Ejemplo: en un sistema de IoT, cuando un sensor envía datos, un callback se activa para procesarlos y guardarlos en una base de datos.  




---

### Ejemplo Corto: Automatización en un E-commerce  

Imaginemos que un **script automatizado** confirma pedidos y queremos hacer algo extra cuando termine.  

```java
interface Callback {
    void ejecutar(String resultado);
}

class AutomatizadorPedidos {
    public void procesarPedido(Callback callback) {
        // Simulamos el procesamiento automático
        String resultado = "✅ Pedido #123 procesado con éxito";
        
        // Al terminar, se dispara el callback
        callback.ejecutar(resultado);
    }
}

// Uso
public class Main {
    public static void main(String[] args) {
        AutomatizadorPedidos robot = new AutomatizadorPedidos();

        robot.procesarPedido(mensaje -> {
            System.out.println("Callback ejecutado: " + mensaje);
            // Aquí podríamos: enviar un email, actualizar un dashboard, etc.
        });
    }
}

```