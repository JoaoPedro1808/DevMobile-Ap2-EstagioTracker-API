package com.example.estagiotrackerAPI.conexao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import org.springframework.jdbc.datasource.DataSourceUtils;

@Component
public class Conexao {
private final DataSource dataSource;

    public Conexao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
}
