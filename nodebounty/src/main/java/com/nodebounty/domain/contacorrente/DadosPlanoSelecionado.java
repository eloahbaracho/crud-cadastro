package com.nodebounty.domain.contacorrente;

import jakarta.validation.constraints.NotBlank;

public record DadosPlanoSelecionado(@NotBlank(message = "Esse campo é obrigatório") String nomePlano) {
}
