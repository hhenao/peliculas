# Microservicios Backend

Se crean **dos microservicios backend** independientes: **peliculas-service** y **usuarios-service**. Cada uno se encarga de un dominio distinto de la aplicación y expone una API REST consumida por el frontend a través del API Gateway.

- **peliculas-service**: Gestiona toda la información relacionada con películas, actores y críticas. Su función principal es ofrecer una API REST para el catálogo de películas, el listado y la gestión de actores, y las críticas asociadas a cada película. Se implementa con Spring Boot, estructurado en capas (controladores REST, repositorios JPA/DAO) y se registra en Eureka para ser descubierto por el gateway.

- **usuarios-service**: Gestiona los usuarios de la aplicación y la autenticación. Su función principal es el CRUD de usuarios (tabla `admin_user`), el login por nombre de usuario y la integración con Spring Security (UsuarioDetailsService, BCrypt). Se implementa con Spring Boot, estructurado en capas (controladores REST, servicios de negocio, repositorio JPA/DAO) y también se registra en Eureka.

Ambos microservicios comparten la base de datos MySQL **peliculasdb**, pero cada uno trabaja sobre las entidades de su dominio: peliculas-service sobre `pelicula`, `actor`, `critica` y `pelicula_actor`; usuarios-service sobre `admin_user`. La estructura en capas y el uso del patrón DAO/Repositorio JPA se reflejan en la figura 1.
