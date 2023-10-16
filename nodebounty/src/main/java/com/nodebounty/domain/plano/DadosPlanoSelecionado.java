package com.nodebounty.domain.plano;

import jakarta.validation.constraints.NotBlank;

public record DadosPlanoSelecionado(@NotBlank String nomePlano) {
}
