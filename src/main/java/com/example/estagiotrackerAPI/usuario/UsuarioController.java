package com.example.estagiotrackerAPI.usuario;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioDAO usuarioDAO;

    public UsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @PostMapping("/criar-usuario")
    @Operation(summary = "Criar usuario", description = "Criar um usuario no sistema")
    public ResponseEntity<Usuario> criarUsuario(@RequestParam String nome, @RequestParam String email, @RequestParam String senha) {
        Usuario usuario = Usuario.builder()
                .nome(nome)
                .email(email)
                .senha(senha)
                .build();

        usuarioDAO.salvar(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @GetMapping("/buscar-por-id")
    @Operation(summary = "Buscar usuario", description = "Buscar usuario por id")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@RequestParam Long id) {
        Usuario usuario = usuarioDAO.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/listar-usuarios")
    @Operation(summary = "Listar usuarios", description = "Lista todos os usuarios do sistema")
    public ResponseEntity<List<Usuario>> listarTodosOsUsuarios() {
        List<Usuario> usuarios =usuarioDAO.listartodos();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/atualizar-usuario")
    @Operation(summary = "Atualizar um usuario", description = "Atualizar um usuario do sistema")
    public ResponseEntity<String> atualizarUsuario(@RequestParam Long id, @RequestParam String nome, @RequestParam String email, @RequestParam String senha) {
        Usuario usuario = Usuario.builder()
                .id(id)
                .nome(nome)
                .email(email)
                .senha(senha)
                .build();

        usuarioDAO.atualizar(usuario);

        return ResponseEntity.ok("Usuario atualizado");
    }

    @DeleteMapping("/deletar-usuario")
    @Operation(summary = "Deletar usuario", description = "Deletar um usuario do sistema")
    public ResponseEntity<String> deletarUsuario(@RequestParam Long id) {
        usuarioDAO.deletar(id);
        return ResponseEntity.ok("Usuario deletadp");
    }
}
