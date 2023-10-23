package com.nodebounty.domain.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosAutenticacacao(
		@NotBlank(message = "Esse campo é obrigatório") @Email(message = "Insira um e-mail válido") String email, 
		@NotBlank(message = "Esse campo é obrigatório") String senha) {
}
