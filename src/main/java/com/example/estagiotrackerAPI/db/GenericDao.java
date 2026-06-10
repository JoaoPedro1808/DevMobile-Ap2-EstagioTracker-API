package com.example.estagiotrackerAPI.db;

import java.util.List;

public interface GenericDao<T, ID> {
    void salvar (T entidade);
    T buscarPorNome(ID id);
    List<T> listartodos();
    void atualizar(T entidade);
    void deletar(ID id);
}
