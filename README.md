## Sistema de Gestión de Reserva Hotelera (SGRH)
### Avance Técnico Formal - Evaluación Sumativa 2
**Asignatura:** Desarrollo FullStack 1 (DSY1103)  
**Institución:** Duoc UC  


##  Integrantes del Equipo
*Almendra Gonzalez*
*Angela Anhuaman*



Descripción General del Proyecto
Este ecosistema distribuido ha sido diseñado bajo una arquitectura de **Microservicios independientes** para la administración automatizada de operaciones hoteleras. El sistema resuelve problemáticas de concurrencia y desacoplamiento mediante la separación estricta de dominios de negocio, garantizando alta disponibilidad, persistencia real aislada y modularidad en la lógica empresarial.

Tecnologías Aplicadas
* **Spring Boot (Java):** Framework core para la construcción de los microservicios corporativos.
* **Flyway Migrations:** Versionamiento de esquemas relacionales automáticos controlados por código.
* **JPA / Hibernate:** Orquestación de la persistencia orientada a objetos en la base de datos.
* **MySQL (Laragon):** Motor relacional encargado del almacenamiento persistente.
* **Lombok:** Optimización de código mediante anotaciones en capas de datos y transporte.
* **Jakarta Bean Validation (JSR 380):** Validación preventiva de datos en los puntos de entrada HTTP.


Justificación de Decisiones de Arquitectura

De acuerdo con las exigencias del encargo y la pauta de evaluación, el sistema implementa los siguientes estándares técnicos fundamentales:

 1. Patrón CSR (Controller-Service-Repository)
Cada microservicio cuenta con una separación física estricta de paquetes, aislando las responsabilidades operacionales sin generar acoplamientos dañinos:
* **Controller (Capa de Presentación):** Expone endpoints semánticos REST, orquesta las solicitudes HTTP, gestiona los códigos de estado mediante `ResponseEntity` y captura datos estructurados en formato JSON.
* **Service (Capa de Negocio):** Concentra de manera centralizada las reglas lógicas del negocio hotelero, validando transacciones y flujos funcionales complejos.
* **Repository & Model (Capa de Persistencia):** Las clases `@Entity` representan las estructuras relacionales de las tablas de MySQL. Se extienden interfaces de `JpaRepository` para la manipulación real de datos CRUD operacionales.

2. Separación de DTOs y Entidades (Seguridad e Integridad)
Se crearon clases de tipo **DTO** independientes de las entidades mapeadas. Esto previene la sobreexposición de columnas internas de la base de datos a los clientes REST y centraliza las validaciones de datos en los controladores antes de que alteren la lógica de negocio (`JSR 380`).


Funcionalidades Implementadas, Puertos y Esquemas

El entorno local unifica sus conexiones hacia la instancia MySQL de Laragon bajo la firma de conexión base (`UWU`). Cada servicio opera con su esquema limpio propio:

| # | Microservicio | Puerto | Base de Datos | Funcionalidades y Cobertura de Código |
|---|---|---|---|---|
| 1 | `autorizacion` | `8081` | `db_autorizacion` | Seguridad del sistema, tokens e identidades. |
| 2 | `huesped` | `8082` | `db_huesped` | CRUD Completo de huéspedes. Validación de unicidad en correo electrónico mediante Bean Validation (`@Email`). |
| 3 | `hotel` | `8083` | `db_hotel` | Catálogo de hoteles de la cadena y metadatos de locación. |
| 4 | `habitacion` | `8084` | `db_habitacion` | Gestión física de habitaciones asociadas a hoteles. Control automatizado de precios base. |
| 5 | `disponibilidad`| `8085` | `db_disponibilidad`| Registro de estados de reserva por habitación (`disponible` BOOLEAN). Sincronizado a tabla en singular (`disponibilidad`). |
| 6 | `tarifa` | `8086` | `db_tarifa` | Factor de precios por noche asociados a temporadas del año. |
| 7 | `reserva` | `8087` | `db_reserva` | Lógica central de solicitudes de hospedaje. Comunicación externa proyectada con Feign Client/WebClient. |
| 8 | `servicioextra` | `8088` | `db_servicioextra`| Catálogo de amenidades y servicios adicionales del hotel. |
| 9 | `pago` | `8089` | `db_pago` | Transacciones financieras vinculadas a reservas. Logs estructurados activos. |
| 10| `comentario` | `8090` | `db_comentario` | Registro de calificaciones numéricas y textos de retroalimentación de huéspedes. |

 Inicialización Automática de Persistencia (Flyway)

Para garantizar la directiva `spring.jpa.hibernate.ddl-auto=validate`, cada microservicio utiliza scripts SQL descriptivos almacenados de forma local en la ruta estricta `src/main/resources/db/migration/`:

* **Microservicio Huesped:** Genera la tabla relacional `huespedes` mapeando campos obligatorios y llaves primarias únicas.
* **Microservicio Habitación:** Modela la tabla `habitaciones`, asociando los campos directos de la entidad y configurando la propiedad adaptada `numero_habitacion` y tipos primitivos numéricos.
* **Microservicio Disponibilidad:** Implementa la tabla `disponibilidad` en concordancia estricta con la anotación `@Table` en singular del modelo de datos.
* **Microservicio Tarifa:** Monta la estructura permanente `tarifas` automatizando el manejo de tipos primitivos `DOUBLE`.
* **Microservicio Comentario:** Registra el esquema `comentarios` correlacionando el ingreso semántico de IDs externos.



Pasos para Ejecutar y Desplegar el Proyecto

Sigue rigurosamente las siguientes instrucciones para levantar la arquitectura en tu estación de trabajo local:

Paso 1: Inicialización de la Infraestructura de Base de Datos (Laragon)
1. Abre la interfaz de **Laragon** y presiona **Start All** para arrancar los servicios de MySQL y Apache.
2. Abre **Visual Studio Code** y conéctate a tu base de datos local mediante la extensión instalada usando el perfil configurado como **`UWU`**.
3. Haz clic derecho sobre la conexión `UWU`, selecciona **`Open SQL Worksheet`**, pega el siguiente código y presiona `Ctrl + Enter` para crear los 10 esquemas limpios necesarios:

```sql
CREATE DATABASE IF NOT EXISTS db_autorizacion;
CREATE DATABASE IF NOT EXISTS db_huesped;
CREATE DATABASE IF NOT EXISTS db_hotel;
CREATE DATABASE IF NOT EXISTS db_habitacion;
CREATE DATABASE IF NOT EXISTS db_disponibilidad;
CREATE DATABASE IF NOT EXISTS db_tarifa;
CREATE DATABASE IF NOT EXISTS db_reserva;
CREATE DATABASE IF NOT EXISTS db_servicioextra;
CREATE DATABASE IF NOT EXISTS db_pago;
CREATE DATABASE IF NOT EXISTS db_comentario;