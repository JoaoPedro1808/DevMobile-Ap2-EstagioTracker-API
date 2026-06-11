package com.example.estagiotrackerAPI.usuario;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioDaoImpl implements UsuarioDAO {
    private final NamedParameterJdbcOperations jdbcTemplate;

    public UsuarioDaoImpl(NamedParameterJdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Usuario> usuarioRowMapper = (rs, rowNum) -> Usuario.builder()
            .id(rs.getLong("id"))
            .nome(rs.getString("nome"))
            .email(rs.getString("email"))
            .senha(rs.getString("senha"))
            .build();

    @Override
    public void salvar(Usuario entidade) {
        String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (:nome, :email, :senha)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("nome", entidade.getNome())
                .addValue("email", entidade.getEmail())
                .addValue("senha", entidade.getSenha());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        String sql = "SELECT * FROM usuarios WHERE id = :id";
        MapSqlParameterSource params =new MapSqlParameterSource("id", id);

        return jdbcTemplate.queryForStream(sql, params, usuarioRowMapper)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
    }

    @Override
    public List<Usuario> listartodos() {
        String sql = "SELECT * FROM usuarios";
        return jdbcTemplate.query(sql, usuarioRowMapper);
    }

    @Override
    public void atualizar(Usuario entidade) {
        String sql = "UPDATE usuarios SET nome = :nome, email = :email, senha = :senha WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", entidade.getId())
                .addValue("nome", entidade.getNome())
                .addValue("email", entidade.getEmail())
                .addValue("senha", entidade.getSenha());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void deletar(Long id) {
        String sql = "DELETE FROM usuarios WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(sql, params);
    }
}
