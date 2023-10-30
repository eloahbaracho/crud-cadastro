package com.nodebounty.domain.cliente;

/* O ID é o único campo obrigatório. Todos os outros serão opcionais. Algumas coisas em teoria não pode mudar
 * Então nem insiro eles aqui, pois não são uma opção. Na minha concepção seria RG, CPF, data de nascimento 
 * e e-mail pra evitar uma chatice depois
 */

public record DadosAtualizacaoCliente(
		String nome, 
		String endereco,
		String cep,
		Integer numero,
		String telefone,
		String senha
) {
}
