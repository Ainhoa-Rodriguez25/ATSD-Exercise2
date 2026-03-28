package todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import todolist.authentication.ManagerUserSession;
import todolist.dto.UsuarioData;
import todolist.service.UsuarioService;

import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ManagerUserSession managerUserSession;

    @GetMapping("/registered")
    public String listadoUsuarios(Model model) {
        // Se obtiene el usuario logueado para la navbar
        Long idUsuarioLogeado = managerUserSession.usuarioLogeado(); // Devuelve el id del usuario que está logueado
        UsuarioData usuarioLogeado = usuarioService.findById(idUsuarioLogeado); // Buscar datos completos del usuario en base de datos
        model.addAttribute("usuario", usuarioLogeado);

        // Se obtiene la lista de todos los usuarios
        List<UsuarioData> usuarios = usuarioService.findAll();
        model.addAttribute("usuarios", usuarios);

        return "listaUsuarios";
    }

    // Método para obtener descripcion con id usuario logeado
    @GetMapping("/registered/{id}")
    public String descripcionUsuario(@PathVariable(value = "id") Long idUsuario, Model model) {
        // Se obtiene el usuario logueado para la navbar
        Long idUsuarioLogeado = managerUserSession.usuarioLogeado();
        UsuarioData usuarioLogeado = usuarioService.findById(idUsuarioLogeado);
        model.addAttribute("usuario", usuarioLogeado);

        // Se obtiene el usuario que se quiere ver en la descripción
        UsuarioData usuarioDescripcion = usuarioService.findById(idUsuario);

        if (usuarioDescripcion == null) {
            throw new RuntimeException("Usurario no encontrado: " + idUsuario);
        }

        model.addAttribute("usuarioDescripcion", usuarioDescripcion);

        return "descripcionUsuario";
    }
}
