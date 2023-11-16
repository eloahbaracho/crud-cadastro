package com.nodebounty.domain.transacao;

import jakarta.validation.constraints.DecimalMin;

public record DadosDepositoTransacao(@DecimalMin(value = "0.01", inclusive = true, message = "O valor de depósito deve ser no mínimo R$ 0,01") Double valor) {
}
