package com.example.cadastro.domain;

import jakarta.validation.constraints.NotBlank;

/* O ID é o único campo obrigatório. Todos os outros serão opcionais. Algumas coisas em teoria não pode mudar
 * Então nem insiro eles aqui, pois não são uma opção. Na minha concepção seria RG, CPF, data de nascimento 
 * e e-mail pra evitar uma chatice depois
 */

public record DadosAtualizacaoCliente(
		@NotBlank(message="Esse campo é obrigatório") String id,
		String Nome, 
		String Endereco,
		String Cep,
		Integer Numero,
		String Senha
) {
}
