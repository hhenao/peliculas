# Microservicio Frontend

El microservicio frontend (**peliculas-frontend**) implementa la capa de presentación utilizando **Spring Boot**, **Spring MVC** y **Thymeleaf**, con **Spring Security** para la autenticación y la gestión de sesiones. Está desarrollado en **Java 17**, se ejecuta en el puerto **8083** y no se registra en Eureka: actúa como cliente de la arquitectura, consumiendo las APIs de los microservicios backend a través del **API Gateway** (puerto 8081).

**Stack técnico:** Spring Boot 4.0.0, Spring MVC, Thymeleaf, Thymeleaf-extras-springsecurity6, Spring Security, RestTemplate (cliente HTTP), validación (Bean Validation). La configuración del cliente HTTP se centraliza en un `RestTemplate` inyectable (`RestTemplateConfig`). Las peticiones al backend se realizan siempre contra la URL base del gateway (`/api/...`), de modo que el frontend no conoce las direcciones de los microservicios.

**Objetivos principales:**

1. **Interfaz de usuario:** Mostrar al usuario formularios, listados y fichas de películas, actores, críticas y usuarios (administración). Las vistas se sirven en el servidor mediante plantillas Thymeleaf (por ejemplo `index`, `layout`, `peliculas/list`, `peliculas/form`, `peliculas/ficha`, `actores/list`, `actores/form`, `usuarios/list`, `usuarios/form`, `auth/login`, `auth/register`).

2. **Interacción con el administrador:** Gestionar la interacción con el administrador mediante controladores web que orquestan la lógica de presentación y las llamadas al backend: **HomeController**, **PeliculaWebController**, **ActorWebController**, **UsuarioWebController** y **AuthController** (login y registro).

3. **Consumo del backend:** Consumir la API REST del backend a través del API Gateway utilizando clientes HTTP (**PeliculaClient**, **ActorClient**, **CriticaClient**, **UsuarioClient**) basados en **RestTemplate**. Estos clientes invocan los endpoints `/api/peliculas`, `/api/actores`, `/api/criticas` y `/api/usuarios`. Una capa de servicios en el frontend (**PeliculaService**, **ActorService**, **CriticaService**, **UsuarioService**) utiliza dichos clientes para obtener, transformar y preparar los datos que se envían al modelo de las vistas.

4. **Seguridad y sesión:** Gestionar el login y el registro (AuthController), la sesión del usuario y los roles (por ejemplo ADMIN/USER), integrando con el **usuarios-service** para la autenticación (por ejemplo mediante detalles de usuario cargados desde la API).

**Aspectos técnicos adicionales:** Subida de archivos (multipart) para imágenes de películas, con límite configurado de tamaño; recursos estáticos para imágenes en `static/img/peliculas`. En este microservicio no reside la lógica de negocio compleja; está centrado en la experiencia de usuario, en el flujo de navegación y en la preparación de los datos que se presentan en las vistas, delegando la persistencia y la reglas de negocio en los microservicios backend (peliculas-service y usuarios-service) a través del gateway.
