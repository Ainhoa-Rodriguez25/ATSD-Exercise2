# To Do List app

ToDoList app usign Spring Boot and Thymeleaf templates.

---

## Author
- **Name:** Ainhoa Rodríguez González
- **Subject:** Agile Techniques For Software Development
- **University:** Miguel Hernández University

---

## Links
- **Trello board:** [P2 – To Do List app](https://trello.com/invite/b/69c2b830550aa12c7c325dcb/ATTI0e9ade6e7f43927d2641f501df1951b951585052/exercise2-to-do-list-app-atsd)
- **GitHub repository:** https://github.com/Ainhoa-Rodriguez25/ATSD-Exercise2
- **Docker Hub:** https://hub.docker.com/r/ainhoaro/p2-todolistapp

---

## Versions
### Version 1.0.1 (25/03/2026)
- About webpage added

## Version 1.1.0 - Technical Documentation (04/04/2026)

### New Classes and Methods

**New classes:**
- `UsuarioNoAutorizadoException` — exception annotated with `@ResponseStatus(HttpStatus.UNAUTHORIZED)` thrown when a non-admin user attempts to access protected pages.

**New methods in `UsuarioService`:**
- `findAll()` — retrieves all registered users from the repository and returns them as a list of `UsuarioData` DTOs.
- `existeAdmin()` — checks whether an administrator user already exists in the database.
- `blockUser(Long id)` — sets the `block` field of a user to `true` and saves the change.
- `unblockUser(Long id)` — sets the `block` field of a user to `false` and saves the change.

**New fields added to existing classes:**
- `admin` (Boolean) added to `Usuario`, `UsuarioData` and `RegistroData`.
- `block` (Boolean) added to `Usuario` and `UsuarioData`.
- `USER_BLOCKED` added to the `LoginStatus` enum in `UsuarioService`.

**New endpoints in `UsuarioController`:**
- `GET /registered` — lists all registered users (admin only).
- `GET /registered/{id}` — shows a user's details (admin only).
- `POST /registered/{id}/block` — blocks a user (admin only).
- `POST /registered/{id}/unblock` — unblocks a user (admin only).


### New Thymeleaf Templates

- `listaUsuarios.html` — displays a table with all registered users, their id and email, and block/unblock and description buttons.
- `descripcionUsuario.html` — displays the details of a specific user: id, email, name and birth date.
- `fragments.html` — updated with two new navbar fragments: `navbar(usuario)` for logged-in users and `navbar-anonymous` for anonymous users.


### Tests Implemented

**Service tests (`UsuarioServiceTest`):**
- `servicioFindAllDevuelveTodosLosUsuarios` — verifies that `findAll()` returns all registered users.
- `servicioExisteAdminDevuelveFalseSiNoHayAdmin` — verifies that `existeAdmin()` returns false when no admin exists.
- `servicioExisteAdminDevuelveTrueSiHayAdmin` — verifies that `existeAdmin()` returns true when an admin exists.
- `servicioBlockUserBloquearUsuario` — verifies that `blockUser()` sets the block field to true in the database.
- `servicioUnblockUserDesbloquearUsuario` — verifies that `unblockUser()` sets the block field to false.
- `servicioLoginUsuarioBloqueadoDevuelveUserBlocked` — verifies that a blocked user cannot log in.

**Controller tests:**
- `NavbarWebTest` — verifies the navbar renders correctly for logged-in and anonymous users.
- `UsuarioListWebTest` — verifies the user listing page shows all registered users.
- `UsuarioDescripcionWebTest` — verifies the user description page shows the correct user data.
- `AdminUsuarioWebTest` — verifies the admin checkbox appears only when no admin exists, and that admin users are redirected to `/registered` after login.
- `ProtectionWebTest` — verifies that non-logged users are redirected to login and non-admin users receive a 401 Unauthorized response when accessing `/registered` and `/registered/{id}`.
- `BlockingUsersWebTest` — verifies that the admin can block users and that blocked users appear with an unblock button in the list.


### Relevant Code Example

The following method in `UsuarioController` illustrates how the protection mechanism works:
```java
@GetMapping("/registered")
public String listadoUsuarios(Model model) {
    Long idUsuarioLogeado = managerUserSession.usuarioLogeado();
    if (idUsuarioLogeado == null) {
        return "redirect:/login";
    }
    UsuarioData usuarioLogeado = usuarioService.findById(idUsuarioLogeado);
    if (usuarioLogeado.getAdmin() == null || !usuarioLogeado.getAdmin()) {
        throw new UsuarioNoAutorizadoException();
    }
    model.addAttribute("usuario", usuarioLogeado);
    List usuarios = usuarioService.findAll();
    model.addAttribute("usuarios", usuarios);
    return "listaUsuarios";
}
```

This method first checks if there is a logged-in user — if not, it redirects to the login page. Then it checks if the logged-in user is an administrator — if not, it throws `UsuarioNoAutorizadoException` which returns a 401 Unauthorized HTTP response. Only if both checks pass does it retrieve the user list and render the view.

---

## Requirements

You need install on your system:

- Java 8 SDK

---

## Ejecución

You can run the app using the goal `run` from Maven's _plugin_ 
on Spring Boot:

```
$ ./mvn spring-boot:run 
```   

You can already create a `jar` file and run it:

```
$ ./mvn package
$ java -jar target/todolist-inicial-0.0.1-SNAPSHOT.jar 
```

Once the app is running, you can open your favourite browser and connect to:

- [http://localhost:8080/login](http://localhost:8080/login)
