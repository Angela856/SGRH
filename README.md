#  Sistema de Gestión de Reserva Hotelera (SGRH)

## Avance Técnico - Examen Transversal
**Asignatura:** Desarrollo FullStack 1 (DSY1103)  
**Institución:** Duoc UC  
**Integrantes del Equipo:** Almendra González & Ángela Anhuamán  

---

## Descripción General
Ecosistema distribuido basado en una arquitectura de **Microservicios** independientes para la administración automatizada de operaciones hoteleras. El sistema utiliza **Spring Cloud Gateway** como punto único de entrada, bases de datos aisladas en contenedores **MySQL** y soporte completo de documentación **OpenAPI (Swagger)**.

---

## Tecnologías Aplicadas
* **Java 17 / Spring Boot 3:** Framework core para el desarrollo de los microservicios.
* **Spring Cloud Gateway:** Enrutador y punto único de entrada perimetral.
* **Docker & Docker Compose:** Contenerización y orquestación de la infraestructura.
* **Springdoc OpenAPI 3 (Swagger):** Generación automática de documentación técnica e interfaz interactiva.
* **JPA / Hibernate & MySQL:** Persistencia relacional aislada por microservicio.
* **JUnit 5 & Mockito:** Plan de pruebas unitarias desacopladas bajo el patrón **AAA (Given-When-Then)**.

---

## Guía de Despliegue con Docker

### Prerrequisitos
* Tienes **Docker** y **Docker Compose** instalados y ejecutándose.

### 1. Levantar la Infraestructura Completa
Para compilar y construir todas las imágenes e iniciar la red de contenedores:
```bash
docker compose up -d --build
### 2. Verificar Estado de los Contenedores
Para revisar que todos los servicios y bases de datos estén en estado Up:
```bash
docker compose ps

### 3. Recompilar un Microservicio en Específico
Si se realiza un cambio en un microservicio (por ejemplo api-gateway o hotel-service) se necesita recompilarlo sin afectar al resto:
```bash
docker compose build --no-cache <nombre-del-servicio>
docker compose up -d <nombre-del-servicio>

### 4. Ver Logs en Tiempo Real
Para diagnosticar la salida de un servicio en particular:
```bash
docker compose logs -f <nombre-del-servicio>

### 5. Detener el Entorno
Para apagar los servicios manteniendo los volúmenes de datos:
```bash
docker compose down

Para detener y eliminar completamente imágenes, contenedores y volúmenes:
```bash
docker compose down -v --rmi all
---

## Mapeo de Puertos y Acceso a Documentación Swagger

Toda la interacción del cliente o aplicaciones externas se realiza a través del **API Gateway** en el puerto `8080`. A continuación se detallan las URLs directas para acceder a la documentación interactiva **Swagger UI** de cada microservicio:

| # | Microservicio | Puerto Interno | Ruta Gateway (`8080`) | Acceso Directo a Swagger UI |
| :---: | :--- | :---: | :--- | :--- |
| **0** | **API Gateway** | `8080` | `/` | [Ver Swagger UI](http://localhost:8080/swagger-ui.html) |
| **1** | **Autorización** | `8081` | `/api/auth/**` | [Ver Swagger UI](http://localhost:8081/swagger-ui/index.html) |
| **2** | **Huésped** | `8082` | `/api/huespedes/**` | [Ver Swagger UI](http://localhost:8082/swagger-ui/index.html) |
| **3** | **Hotel** | `8083` | `/api/hoteles/**` | [Ver Swagger UI](http://localhost:8083/swagger-ui/index.html) |
| **4** | **Habitación** | `8084` | `/api/habitacion/**` | [Ver Swagger UI](http://localhost:8084/swagger-ui/index.html) |
| **5** | **Disponibilidad**| `8085` | `/api/disponibilidad/**` | [Ver Swagger UI](http://localhost:8085/doc/swagger-ui.html) |
| **6** | **Tarifa** | `8086` | `/api/tarifa/**` | [Ver Swagger UI](http://localhost:8086/swagger-ui/index.html) |
| **7** | **Reserva** | `8087` | `/api/reservas/**` | [Ver Swagger UI](http://localhost:8087/swagger-ui/index.html) |
| **8** | **Servicio Extra** | `8088` | `/api/servicios-extras/**` | [Ver Swagger UI](http://localhost:8088/swagger-ui/index.html) |
| **9** | **Pago** | `8089` | `/api/pagos/**` | [Ver Swagger UI](http://localhost:8089/swagger-ui/index.html) |
| **10**| **Comentario** | `8090` | `/api/comentario/**` | [Ver Swagger UI](http://localhost:8090/swagger-ui/index.html) |
---

## Cobertura de Pruebas Unitarias (JUnit 5 + Mockito)

El proyecto incluye suites de pruebas unitarias implementadas en la ruta estándar `src/test/java` de cada microservicio.

### Características del Plan de Pruebas:
* **Estructura AAA:** Diseño estricto bajo las etapas *Arrange, Act, Assert* (Dado, Cuando, Entonces).
* **Aislamiento con Mocks:** Uso de `@Mock` e `@InjectMocks` para simular repositorios y dependencias externas sin alterar las bases de datos.
* **Cobertura de Casos:**
  * **Camino Feliz:** Validación del comportamiento correcto cuando los datos de entrada son válidos.
  * **Camino Alternativo:** Verificación del lanzamiento de excepciones de negocio ante anomalías (ej: recursos inexistentes o duplicados).

### Comando para ejecutar las pruebas unitarias:
Desde la raíz de cualquier microservicio individual:
```bash
mvn test
