package com.example.estagiotrackerAPI.vaga;

import com.example.estagiotrackerAPI.db.GenericDao;

import java.util.List;

public interface VagaDAO extends GenericDao<Vaga, Long> {
    List<Vaga> buscarPorNome(String nome);
    List<Vaga> listarTodasAsVagas(Long usuarioId);
}
