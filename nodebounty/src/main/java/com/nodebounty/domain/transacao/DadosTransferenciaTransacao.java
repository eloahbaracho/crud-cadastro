package com.nodebounty.domain.transacao;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public record DadosTransferenciaTransacao(@DecimalMin(value = "0.01", inclusive = true, message = "O valor de saque deve ser no mínimo R$ 0,01") Double valor, @NotBlank String numeroConta) {

}
