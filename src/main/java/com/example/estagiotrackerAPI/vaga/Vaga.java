package com.example.estagiotrackerAPI.vaga;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vaga {
    private Long id;
    private String nomeEmpresa;
    private String nomeVaga;
    private BigDecimal salario;
    private String statusKanban;
    private String modelo;
    private LocalDate dataAplicacao;
    private String descricao;
    private Long usuarioId;
}
