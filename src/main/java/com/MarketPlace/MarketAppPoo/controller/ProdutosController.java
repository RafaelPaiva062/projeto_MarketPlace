// Desenvolved by Rafael Paiva 🔥

package com.MarketPlace.MarketAppPoo.controller;

import com.MarketPlace.MarketAppPoo.model.ModelUser;
import com.MarketPlace.MarketAppPoo.model.Produtos;
import com.MarketPlace.MarketAppPoo.service.ProdutosService;
import com.MarketPlace.MarketAppPoo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProdutosService produtosService;

    // Página de produtos do usuário logado
    @GetMapping
    public String listarProdutosDoUsuario(Model model, Principal principal) {
        ModelUser usuario = userService.buscarPorEmail(principal.getName()); // Obtém o usuário logado
        List<Produtos> produtos = produtosService.listarPorUsuario(usuario.getCod()); // Obtém os produtos do usuário

        model.addAttribute("produtos", produtos);
        model.addAttribute("produto", new Produtos()); // Cria um objeto em branco de Produto para o formulário
        model.addAttribute("user", usuario); // Adiciona o usuário ao modelo
        return "produtos"; // Retorna a página "produtos.html"
    }

    // Método para cadastrar um novo produto
    @PostMapping
    public String cadastrarProduto(@ModelAttribute Produtos produto, Principal principal) {
        ModelUser modelUser = userService.buscarPorEmail(principal.getName()); // Obtém o usuário logado
        produto.setUsuario(modelUser); // Associa o produto ao usuário logado
        produtosService.salvarProduto(produto); // Salva o produto no banco de dados
        return "redirect:/produtos"; // Redireciona para a página de produtos
    }

    // Método para alterar a foto de perfil do usuário
    @PostMapping("/foto")
    public String alterarFoto(@RequestParam("foto") MultipartFile foto, Principal principal) throws IOException {
        ModelUser modelUser = userService.buscarPorEmail(principal.getName()); // Obtém o usuário logado

        // Salva a foto na pasta "resources/static/img"
        String nomeArquivo = foto.getOriginalFilename();
        String caminho = "src/main/resources/static/img/" + nomeArquivo;
        foto.transferTo(new File(caminho)); // Faz o upload da foto para o diretório

        // Atualiza o nome do arquivo da foto no banco de dados
        modelUser.setFotoPerfil(nomeArquivo);
        userService.salvar(modelUser); // Salva o usuário com a foto atualizada

        return "redirect:/produtos"; // Redireciona para a página de produtos
    }

}

// Controller para listar todos os produtos, caso seja necessário separar
@Controller
@RequestMapping("/produtos/lista")
class ListarProdutos {

    @Autowired
    private ProdutosService produtosService;

    // Página para listar todos os produtos cadastrados
    @GetMapping
    public String listarProdutos(Model model) {
        List<Produtos> listaProdutos = produtosService.listarProdutos(); // Obtém a lista de todos os produtos
        model.addAttribute("listaprodutos", listaProdutos); // Adiciona a lista de produtos ao modelo
        return "listaprodutos"; // Retorna a página "listaprodutos.html"
    }
}
