# Manual de Usuario – TOP Pelis UAH

**Sistema de gestión de películas y actores**  
Versión para Trabajo Final – Microservicios (peliculas-frontend)

---

## 1. Introducción

**TOP Pelis UAH** es una aplicación web que permite consultar un catálogo de películas, ver actores, leer y escribir críticas, y (según el rol) administrar películas, actores y usuarios. La aplicación se ejecuta en el navegador; la URL por defecto es **http://localhost:8083** (cuando el servidor está en marcha).

**Roles de usuario:**

| Rol   | Descripción |
|-------|-------------|
| **USER** | Puede ver el ranking y el listado de películas, ver fichas de películas y **añadir críticas**. |
| **ADMIN** | Todo lo de USER y además: crear, editar y eliminar películas; gestionar actores (crear, editar, eliminar); gestionar usuarios (listar, editar, eliminar); eliminar críticas. |

Para usar la aplicación (excepto las pantallas de login y registro) es necesario **iniciar sesión**.

---

## 2. Acceso a la aplicación

### 2.1 Requisitos

- Navegador web actualizado (Chrome, Firefox, Edge, Safari).
- Servicios backend en ejecución (API Gateway, Eureka, peliculas-service, usuarios-service) y frontend en el puerto 8083.

### 2.2 URL de acceso

- **Aplicación:** `http://localhost:8083`
- **Inicio de sesión:** `http://localhost:8083/login`
- **Registro de usuario:** `http://localhost:8083/register`

---

## 3. Inicio de sesión y registro

### 3.1 Iniciar sesión

1. Abra en el navegador la URL **http://localhost:8083** o **http://localhost:8083/login**.
2. Si no está autenticado, se mostrará la pantalla de **Iniciar sesión**.
3. Introduzca su **Usuario** (nombre de usuario) y **Contraseña**.
4. Pulse **Entrar**.
5. Si los datos son correctos, se le redirigirá a la **página de inicio** (ranking de películas). En la barra superior verá el mensaje *Bienvenido, [nombre de usuario]* y el enlace **Salir**.
6. Si el usuario o la contraseña son incorrectos, aparecerá el mensaje: *Usuario o contraseña incorrectos.*

### 3.2 Registrarse

1. En la pantalla de login, pulse **Registrar usuario** (o vaya a **http://localhost:8083/register**).
2. Rellene:
   - **Usuario:** nombre de usuario (único).
   - **Contraseña:** contraseña deseada.
3. Pulse **Registrar**.
4. Si el registro es correcto, se le redirigirá al **login** y podrá iniciar sesión con el nuevo usuario. Por defecto los nuevos usuarios suelen tener rol **USER** (dependiendo de la configuración del backend).
5. Si el nombre de usuario ya existe u ocurre otro error, se mostrará la misma pantalla de registro con el mensaje correspondiente.

### 3.3 Cerrar sesión

- En la barra superior, pulse **Salir**. Será redirigido a la pantalla de login.

---

## 4. Página de inicio (ranking)

Tras iniciar sesión, la **página de inicio** muestra:

- El título **TOP Pelis UAH** y el subtítulo *Sistema de gestión de Películas y Actores*.
- Una sección **Top Películas Mejor Valoradas** con tarjetas de películas ordenadas por nota media.
- En cada tarjeta: imagen de portada (o imagen por defecto), título, género, año, director y **nota media** (valoración).
- Al hacer clic en una tarjeta se abre la **ficha de la película**.

**Menú de navegación (barra superior):**

- **Películas:** listado de películas (visible para USER y ADMIN).
- **Actores:** listado de actores (solo ADMIN).
- **Usuarios:** listado de usuarios (solo ADMIN).
- **Bienvenido, [usuario]** y **Salir** (cuando está autenticado).

---

## 5. Películas

### 5.1 Listado de películas

- En el menú, pulse **Películas** (o acceda a **http://localhost:8083/peliculas/listar**).
- Se muestra una lista de películas en tarjetas con imagen, título, año, género y botones **Ver**, y (solo ADMIN) **Editar** y **Eliminar**.

**Búsqueda:**

- En la parte superior hay un formulario con tres campos: **Título**, **Género**, **Actor**.
- Rellene uno o varios y pulse **Buscar**. La lista se actualiza con los resultados.
- Pulse **Limpiar** para volver al listado completo.

### 5.2 Ver ficha de una película

- Desde el listado o desde el ranking, pulse **Ver** en una película (o haga clic en la tarjeta).
- Se abre la **ficha** con:
  - Imagen de portada.
  - Título, año, duración, país, dirección, género.
  - Reparto (lista de actores).
  - Sinopsis.
  - **Nota media** de las críticas.
  - **Listado de críticas** (autor, nota, descripción, fecha).
- **Usuario con rol USER:** al final de la ficha verá el formulario **Agregar crítica** (nota 1–10 y descripción). Solo los USER pueden añadir críticas desde esta pantalla.
- **Usuario con rol ADMIN:** en cada crítica aparece el botón **Eliminar** para borrar esa crítica.

### 5.3 Crear película (solo ADMIN)

1. En el listado de películas, pulse **+ Nueva Película**.
2. Rellene el formulario: título, año, duración, país, dirección, género, sinopsis y, si lo desea, **imagen de portada** (archivo).
3. Opcionalmente seleccione **actores** de la lista para asociarlos a la película.
4. Pulse el botón de envío del formulario para guardar.
5. Será redirigido al listado de películas con la nueva película incluida.

### 5.4 Editar película (solo ADMIN)

1. En el listado, pulse **Editar** en la película deseada.
2. Modifique los campos que necesite (título, año, duración, país, dirección, género, sinopsis, imagen, actores).
3. Puede **asociar** o **eliminar** actores de la película mediante los controles del formulario.
4. Pulse el botón de actualizar. Será redirigido al listado.

### 5.5 Eliminar película (solo ADMIN)

1. En el listado, pulse **Eliminar** en la película deseada.
2. Confirme en el mensaje de confirmación.
3. La película se eliminará y se mostrará de nuevo el listado.

### 5.6 Añadir crítica (solo USER)

1. Abra la **ficha** de la película.
2. En la sección **Agregar crítica**, indique una **Nota** (1 a 10) y una **Descripción**.
3. Pulse el botón de envío.
4. La crítica se añadirá y se actualizará la ficha (lista de críticas y nota media). Si aparece un mensaje de error (por ejemplo por validación), corríjalo y vuelva a enviar.

### 5.7 Eliminar crítica (solo ADMIN)

1. En la ficha de la película, localice la crítica en el listado.
2. Pulse el botón **Eliminar** de esa crítica.
3. La crítica se borrará y se actualizará la ficha.

---

## 6. Actores (solo ADMIN)

- En el menú, pulse **Actores** (o **http://localhost:8083/actores/listar**).

### 6.1 Listado de actores

- Se muestra una tabla o lista con los actores. Desde aquí puede acceder a **Crear**, **Editar** y **Eliminar** (según los botones disponibles en la interfaz).

### 6.2 Crear actor

1. Pulse el botón para **crear** o **nueva actor** (según el texto de la aplicación).
2. Rellene los campos (nombre, fecha de nacimiento, país de nacimiento, etc.).
3. Guarde. Volverá al listado de actores.

### 6.3 Editar actor

1. En el listado, pulse **Editar** en el actor deseado.
2. Modifique los datos y guarde. Volverá al listado.

### 6.4 Eliminar actor

1. En el listado, pulse **Eliminar** en el actor deseado.
2. Confirme si se solicita. El actor se eliminará del listado.

---

## 7. Usuarios (solo ADMIN)

- En el menú, pulse **Usuarios** (o **http://localhost:8083/usuarios/listar**).

### 7.1 Listado de usuarios

- Se muestra una lista de usuarios registrados (por ejemplo nombre de usuario y rol). Desde aquí puede **Editar** o **Eliminar** (según los botones de la pantalla).

### 7.2 Editar usuario

1. Pulse **Editar** en el usuario deseado.
2. Puede modificar **Usuario** (nombre de usuario) y **Rol** (por ejemplo USER o ADMIN).
3. Guarde. Volverá al listado de usuarios.

### 7.3 Eliminar usuario

1. Pulse **Eliminar** en el usuario deseado.
2. Confirme si se solicita. El usuario se eliminará. Puede mostrarse un mensaje de éxito o de error (por ejemplo si no está permitido eliminar ese usuario).

---

## 8. Resumen de permisos por pantalla

| Pantalla / Acción           | USER | ADMIN |
|----------------------------|------|--------|
| Login / Registro           | Sí   | Sí     |
| Página de inicio (ranking) | Sí   | Sí     |
| Listado de películas      | Sí   | Sí     |
| Búsqueda de películas     | Sí   | Sí     |
| Ver ficha de película     | Sí   | Sí     |
| Añadir crítica             | Sí   | No     |
| Eliminar crítica           | No   | Sí     |
| Crear / Editar / Eliminar película | No | Sí     |
| Listado y gestión de actores | No  | Sí     |
| Listado y gestión de usuarios | No  | Sí     |

---

## 9. Mensajes de error habituales

- **Usuario o contraseña incorrectos:** Compruebe el nombre de usuario y la contraseña en la pantalla de login.
- **Acceso denegado / 403:** La acción que intenta realizar no está permitida para su rol. Por ejemplo, un USER no puede acceder a **Actores** ni **Usuarios**, ni crear o editar películas.
- **Error al registrar:** El nombre de usuario puede estar ya en uso. Pruebe con otro.
- **Error al guardar crítica:** Compruebe que la nota esté entre 1 y 10 y que la descripción no esté vacía (según las reglas de la aplicación).
- Si la página no carga o aparece error de conexión, compruebe que el frontend (puerto 8083) y el resto de servicios (API Gateway, Eureka, microservicios) estén en ejecución.

---

## 10. Contacto y soporte

Para incidencias o dudas sobre el uso de la aplicación en el contexto del Trabajo Final, contacte con el responsable del proyecto o con el equipo docente según las indicaciones de la asignatura.

---

*Manual de usuario – TOP Pelis UAH – Trabajo Final (Microservicios).*
