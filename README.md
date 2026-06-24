🏨 Sistema de Gestión de Reserva Hotelera (SGRH)Avance Técnico Consolidado y Formal - Evaluación 3Asignatura: Desarrollo FullStack 1 (DSY1103)Institución: Duoc UCIntegrantes del Equipo: Almendra González & Ángela Anhuamán📄 Descripción General del ProyectoEste ecosistema distribuido ha sido diseñado bajo una arquitectura de Microservicios independientes para la administración automatizada de operaciones hoteleras. El sistema resuelve de forma nativa problemáticas de concurrencia, alta disponibilidad y acoplamiento mediante la separación estricta de dominios de negocio, garantizando persistencia real aislada y modularidad absoluta en la lógica empresarial.🛠️ Tecnologías AplicadasSpring Boot 3 (Java): Framework core para la construcción de los microservicios corporativos y reactivos.Spring Cloud Gateway: Enrutador y punto único de entrada perimetral que centraliza la seguridad y los prefijos de rutas REST.Spring HATEOAS: Mecanismo hipermedia para añadir madurez RESTful (Hypermedia As The Engine Of Application State) mediante enlaces dinámicos _links.Springdoc OpenAPI 3 (Swagger): Motor de autogeneración de documentación técnica interactiva y pruebas de endpoints en entorno web.Net DataFaker: Librería automatizada para el poblado de registros ficticios realistas al inicializar el contexto del servidor.JPA / Hibernate: Orquestación de la persistencia orientada a objetos hacia esquemas relacionales.MySQL (Laragon): Motor relacional encargado del almacenamiento persistente aislado por microservicio.Lombok: Optimización y limpieza de código fuente mediante anotaciones en capas de datos y transporte (@Data, @AllArgsConstructor, etc.).Jakarta Bean Validation (JSR 380): Validación preventiva y semántica de datos en los puntos de entrada HTTP (@Valid, @NotBlank, @Email).JUnit 5 & Mockito: Herramientas y frameworks para el diseño del plan de pruebas unitarias desacopladas bajo el patrón AAA / Given-When-Then.🏛️ Justificación de Decisiones de ArquitecturaEl sistema implementa de manera rigurosa las directrices de la escuela de informática y los estándares globales de desarrollo de software:Patrón CSR (Controller-Service-Repository): Cada microservicio implementa una separación física estricta de paquetes, aislando las responsabilidades operativas:Controlador (Presentación): Expone endpoints semánticos REST, gestiona respuestas utilizando ResponseEntity, asocia metadatos de Swagger e inyecta enlaces de navegación HATEOAS.Servicio (Negocio): Concentra centralizadamente las reglas lógicas del negocio hotelero y gestiona las excepciones controladas (orElseThrow).Repositorio y Modelo (Persistencia): Las clases @Entity representan las estructuras relacionales físicas en la base de datos MySQL, mientras que las interfaces extienden de JpaRepository para las operaciones CRUD.Separación de DTOs y Entidades (Seguridad e Integridad): Se crearon clases de tipo DTO independientes de las entidades mapeadas. Esto previene la sobreexposición (Mass Assignment Vulnerability) de columnas internas y centraliza las restricciones de formato en la capa de transporte antes de interactuar con la persistencia.Manejo Centralizado de Excepciones: Se implementaron interceptores globales (@RestControllerAdvice) que capturan excepciones específicas del negocio (como IllegalArgumentException ante registros inexistentes o documentos duplicados) y devuelven respuestas HTTP controladas con códigos semánticos uniformes (ej: 404 Not Found), evitando la caída tosca del servidor (500).🗺️ Mapa Arquitectónico de Puertos, Rutas y EsquemasToda la interacción del cliente se realiza exclusivamente a través del API Gateway en el puerto 8080. Este se encarga de redirigir los prefijos de rutas internas a cada contenedor de servicio local:#MicroservicioPuerto InternoBase de Datos (Laragon)Ruta Raíz en el Gateway (8080)Cobertura / Estado de Implementación0Gateway Service8080N/A/Enrutamiento perimetral unificado mediante YAML dinámico.1Autorización8081db_autorizacion/api/auth/**Seguridad del sistema, generación de tokens e identidades.2Huésped8082db_huesped/api/huesped/**CRUD completo, DTOs aislados y validación preventiva anti-duplicados por documento de identidad.3Hotel8083db_hotel/api/hoteles/**Catálogo de hoteles de la cadena y metadatos de ubicación.4Habitación8084db_habitacion/api/habitacion/**Gestión de piezas y control automatizado de precios base. Consumido de forma externa.5Disponibilidad8085db_disponibilidad/api/disponibilidad/**Sincronizado en tabla singular (disponibilidad). Consume el puerto 8084 mediante OpenFeign.6Tarifa8086db_tarifa/api/tarifas/**Factor de precios por noche asociados a temporadas del año.7Reserva8087db_reserva/api/reservas/**Lógica central de solicitudes de hospedaje y control de estancias.8Servicio Extra8088db_servicioextra/api/servicios-extras/**Catálogo de amenidades y servicios adicionales (comidas, tours).9Pago8089db_pago/api/pagos/**Transacciones financieras vinculadas a reservas. Filtros Gateway activos.10Comentario8090db_comentario/api/comentarios/**Registro de calificaciones numéricas y retroalimentación de clientes.📈 Cumplimiento de Criterios Avanzados (Evaluación 3)1. Documentación Viva con Swagger/OpenAPI 3Se integraron metadatos explícitos en los controladores y DTOs principales utilizando anotaciones como @Tag, @Operation, y @Schema.Ruta de acceso estándar: http://localhost:PUERTO/swagger-ui/index.htmlRuta personalizada (Disponibilidad): Se modificó la propiedad en el properties (springdoc.swagger-ui.path=/doc/swagger-ui.html) exponiendo la UI en: http://localhost:8085/doc/swagger-ui.html.2. Navegación Hipermedia Dinámica (HATEOAS)Las respuestas de los endpoints clave ya no devuelven estructuras JSON planas. Al extender los DTOs de RepresentationModel<T>, la API inyecta dinámicamente un objeto contenedor _links con referencias puntuales (self y navegación de colecciones), permitiendo al cliente descubrir servicios sin conocer la composición estática del backend.3. Poblado Automatizado Inteligente (DataFaker)Se añadieron clases de configuración que implementan CommandLineRunner bajo la anotación @Configuration. Al iniciar el servicio, el sistema evalúa si las tablas físicas locales de Laragon se encuentran vacías; en caso afirmativo, DataFaker inserta de forma autónoma registros realistas en español, acelerando los procesos de testing de caja negra.4. Cobertura de Pruebas Unitarias Aisladas (JUnit 5 + Mockito)Se implementaron suites de pruebas en la ruta lógica de test (src/test/java). Las pruebas cumplen con el estándar AAA (Arrange, Act, Assert):Mockito aísla la capa de negocio mediante el uso de mokes (@Mock, @InjectMocks), evitando la alteración de las bases de datos de desarrollo.Se validan tanto los caminos de éxito (Camino Feliz) como la correcta emisión de excepciones de negocio ante anomalías de entrada (Camino Alternativo).⚙️ Especificación Técnica del Enrutador Perimetral (Gateway application.yml)El archivo de configuración unificado del microservicio de Gateway organiza el tráfico distribuido de la siguiente forma:YAMLspring:
  application:
    name: gateway-service
  profiles:
    active: dev
  cloud:
    gateway:
      routes:
        - id: autorizacion-service
          uri: http://localhost:8081
          predicates:
            - Path=/api/auth/**

        - id: huesped-service
          uri: http://localhost:8082
          predicates:
            - Path=/api/huesped/**

        - id: hotel-service
          uri: http://localhost:8083
          predicates:
            - Path=/api/hoteles/**

        - id: habitacion-service
          uri: http://localhost:8084
          predicates:
            - Path=/api/habitacion/**

        - id: disponibilidad-service
          uri: http://localhost:8085
          predicates:
            - Path=/api/disponibilidad/**

        - id: reserva-service
          uri: http://localhost:8087
          predicates:
            - Path=/api/reservas/**

        - id: servicioextra-service
          uri: http://localhost:8088
          predicates:
            - Path=/api/servicios-extras/**

        - id: pago-service
          uri: http://localhost:8089
          predicates:
            - Path=/api/pagos/**
          filters:
            - AddResponseHeader=X-Gateway-Time, Development-Environment

server:
  port: 8080
🚀 Pasos de Inicialización y Despliegue LocalPaso 1: Infraestructura de Persistencia Relacional (Laragon)Abra la consola administrativa de Laragon y ejecute "Start All" para levantar los motores de MySQL y Apache de forma local.Ingrese a su software gestor de base de datos preferido y establezca conexión con las credenciales por defecto (Usuario: root, Contraseña:  ) bajo el alias identificador del equipo.Abra una pestaña de consultas SQL, pegue el siguiente bloque de sentencias secuenciales y ejecútelas para crear de forma limpia los 10 entornos relacionales:SQLCREATE DATABASE IF NOT EXISTS db_autorizacion;
CREATE DATABASE IF NOT EXISTS db_huesped;
CREATE DATABASE IF NOT EXISTS db_hotel;
CREATE DATABASE IF NOT EXISTS db_habitacion;
CREATE DATABASE IF NOT EXISTS db_disponibilidad;
CREATE DATABASE IF NOT EXISTS db_tarifa;
CREATE DATABASE IF NOT EXISTS db_reserva;
CREATE DATABASE IF NOT EXISTS db_servicioextra;
CREATE DATABASE IF NOT EXISTS db_pago;
CREATE DATABASE IF NOT EXISTS db_comentario;
Paso 2: Compilación de Proyectos
En la carpeta raíz de cada microservicio, ejecute el comando de ciclo de vida de Maven para asegurar la correcta descarga de dependencias e instalación de librerías agregadas:

Bash
mvn clean install
Paso 3: Despliegue Secuencial
Arranque los microservicios en sus entornos IDE correspondientes, asegurando el levantamiento obligatorio de gateway-service (8080) para habilitar las pasarelas unificadas de consumo REST.
