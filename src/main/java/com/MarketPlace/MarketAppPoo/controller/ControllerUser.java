// Desenvolved by Samuel M. Dias🕷
package com.MarketPlace.MarketAppPoo.controller;

import com.MarketPlace.MarketAppPoo.model.ModelUser;
import com.MarketPlace.MarketAppPoo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/cadastrarPessoa")// Anotação que mostra que será um controller
public class ControllerUser {

    @Autowired
    private UserService userService;// Chamamento do repository e suas funções

    @PostMapping("/Cadastrar")
    public String cadastrarUsuario(@ModelAttribute("usuarios") ModelUser modelUser) { // Método de cadastro
        userService.saveUser(modelUser);
        return "redirect:/cadastrarPessoa";
    }

    @GetMapping("/cadastroUsuario")
    public String listarUsuario(Model model) {
;
        model.addAttribute("usuarios", new ModelUser());
        return "UserForCadaster";
    }

}
