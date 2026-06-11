package com.example.estagiotrackerAPI.vaga;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class VagaDaoImpl implements VagaDAO{
    private final NamedParameterJdbcOperations jdbcTemplate;

    public VagaDaoImpl(NamedParameterJdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Vaga> vagaRowMapper = (rs, rowNum) -> Vaga.builder()
            .id(rs.getLong("id"))
            .nomeEmpresa(rs.getString("nome_empresa"))
            .nomeVaga(rs.getString("nome_vaga"))
            .salario(rs.getBigDecimal("salario"))
            .statusKanban(rs.getString("status_kanban"))
            .modelo(rs.getString("modelo"))
            .dataAplicacao(rs.getDate("data_aplicacao")!= null ? rs.getDate("data_aplicacao").toLocalDate() : null)
            .descricao(rs.getString("descricao"))
            .usuarioId(rs.getLong("usuario_id"))
            .build();

    @Override
    @Transactional
    public void salvar(Vaga entidade) {
        String sql = "INSERT INTO vagas (nome_empresa, nome_vaga, salario, status_kanban, modelo, descricao, usuario_id) VALUES (:nome_empresa, :nome_vaga, :salario, :status_kanban, :modelo, :descricao, :usuario_id)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("nome_empresa", entidade.getNomeEmpresa())
                .addValue("nome_vaga", entidade.getNomeVaga())
                .addValue("salario", entidade.getSalario())
                .addValue("status_kanban",entidade.getStatusKanban())
                .addValue("modelo", entidade.getModelo())
                .addValue("descricao", entidade.getDescricao())
                .addValue("usuario_id", entidade.getUsuarioId());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);

        if (keyHolder.getKey() != null) {
            entidade.setId(keyHolder.getKey().longValue());
        }
    }

    @Override
    public Vaga buscarPorId(Long id) {
        String sql = "SELECT * FROM vagas WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        return jdbcTemplate.queryForStream(sql, params, vagaRowMapper)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Vaga não encontrada"));
    }

    @Override
    public List<Vaga> listartodos() {
        String sql = "SELECT * FROM vagas";
        return jdbcTemplate.query(sql, vagaRowMapper);
    }


    @Override
    public List<Vaga> listarTodasAsVagas(Long usuarioId) {
        String sql = "SELECT * FROM vagas WHERE usuario_id = :usuario_id ORDER BY data_aplicacao DESC";
        MapSqlParameterSource params = new MapSqlParameterSource("usuario_id", usuarioId);
        return jdbcTemplate.query(sql, params, vagaRowMapper);
    }

    @Override
    public List<Vaga> buscarPorNome(String nome_vaga) {
        String sql = "SELECT * FROM vagas WHERE nome_vaga LIKE :nome_vaga";
        MapSqlParameterSource params = new MapSqlParameterSource("nome_vaga", "%" + nome_vaga);
        return jdbcTemplate.query(sql, params, vagaRowMapper);
    }

    @Override
    public void atualizar(Vaga entidade) {
        String sql = "UPDATE vagas SET nome_empresa = :nome_empresa, nome_vaga = :nome_vaga, salario = :salario, status_kanban = :status_kanban, modelo = :modelo, descricao = :descricao WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("nome_empresa", entidade.getNomeEmpresa())
                .addValue("nome_vaga", entidade.getNomeVaga())
                .addValue("salario", entidade.getSalario())
                .addValue("status_kanban", entidade.getStatusKanban())
                .addValue("modelo", entidade.getModelo())
                .addValue("descricao", entidade.getDescricao())
                .addValue("id", entidade.getId());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void deletar(Long id) {
        String sql = "DELETE FROM vagas WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(sql, params);
    }
}

