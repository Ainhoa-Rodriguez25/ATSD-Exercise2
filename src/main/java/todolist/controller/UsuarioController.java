package todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
