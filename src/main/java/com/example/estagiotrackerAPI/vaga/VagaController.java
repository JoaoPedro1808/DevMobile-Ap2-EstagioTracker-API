package com.example.estagiotrackerAPI.vaga;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping
public class VagaController {
    private final VagaDAO vagaDao;

    public VagaController(VagaDAO vagaDao) {
        this.vagaDao = vagaDao;
    }

    @PostMapping("/criar-vaga")
    @Operation(summary = "Criar vaga", description = "Criar uma vaga para o sistema")
    public ResponseEntity<Vaga> criarVaga(
            @RequestParam String nomeEmpresa,
            @RequestParam String nomeVaga,
            @RequestParam BigDecimal salario,
            @RequestParam String statusKanban,
            @RequestParam String modelo,
            @RequestParam String descricao,
            @RequestParam Long usuarioId) {

        Vaga vaga = Vaga.builder()
                .nomeEmpresa(nomeEmpresa)
                .nomeVaga(nomeVaga)
                .salario(salario)
                .statusKanban(statusKanban)
                .modelo(modelo)
                .descricao(descricao)
                .usuarioId(usuarioId)
                .build();

        vagaDao.salvar(vaga);

        return ResponseEntity.status(HttpStatus.CREATED).body(vaga);
    }

    @GetMapping("/buscar-por-id")
    @Operation(summary = "Buscar uma vaga", description = "Buscar uma vaga pelo id")
    public ResponseEntity<Vaga> buscarVagaPorId(@RequestParam Long id) {
        Vaga vaga = vagaDao.buscarPorId(id);
        return ResponseEntity.ok(vaga);
    }

    @GetMapping("/listar-vagas")
    @Operation(summary = "Listar as vagas", description = "Listar todas as vagas do usuario")
    public ResponseEntity<List<Vaga>> litarTodasAsVagas(@RequestParam Long usuarioId) {
        List<Vaga> vagas = vagaDao.listarTodasAsVagas(usuarioId);
        return ResponseEntity.ok(vagas);
    }

    @GetMapping("/buscar-por-nome")
    @Operation(summary = "Buscar pelo nome", description = "Buscar uma vaga pelo nome")
    public ResponseEntity<List<Vaga>> buscarVagaPorNome(@RequestParam String nomeVaga) {
        List<Vaga> vagas = vagaDao.buscarPorNome(nomeVaga);
        return ResponseEntity.ok(vagas);
    }

    @GetMapping("/listar-todas-vagas")
    @Operation(summary = "Lista todas as vagas", description = "Lista todas as vagas do sistema")
    public ResponseEntity<List<Vaga>> listarTodasVagasSistema() {
        List<Vaga> vagas = vagaDao.listartodos();
        return ResponseEntity.ok(vagas);
    }

    @PutMapping("/atualizar-vaga")
    @Operation(summary = "Atualizar vaga", description = "Atualizar uma vaga do sistema")
    public ResponseEntity<String> atualizarVaga(
            @RequestParam Long id,
            @RequestParam String nomeEmpresa,
            @RequestParam String nomeVaga,
            @RequestParam BigDecimal salario,
            @RequestParam String statusKanban,
            @RequestParam String modelo,
            @RequestParam String descricao) {

        Vaga vaga = Vaga.builder()
                .id(id)
                .nomeEmpresa(nomeEmpresa)
                .nomeVaga(nomeVaga)
                .salario(salario)
                .statusKanban(statusKanban)
                .modelo(modelo)
                .descricao(descricao)
                .build();

        vagaDao.atualizar(vaga);

        return ResponseEntity.ok("Vaga atualizada");
    }

    @DeleteMapping("/deletar-vaga")
    @Operation(summary = "Deletar vaga", description = "Deletar uma vaga do sistema")
    public ResponseEntity<Void> deletarVaga(@RequestParam Long id) {
        vagaDao.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
