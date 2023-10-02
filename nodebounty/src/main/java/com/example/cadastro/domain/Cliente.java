package com.example.cadastro.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

@Table(name = "CLIENTES")
@Entity
@Getter
@EqualsAndHashCode(of = "id") /* Seleciona a chave primária da tabela em questão */

public class Cliente {
	
	@Id @GeneratedValue(strategy = GenerationType.UUID) @Column(name = "ID")
	private String id;
	@Column(name = "NOME")
	private String Nome;
	@Column(name = "ENDERECO")
	private String Endereco;
	@Column(name = "CEP")
	private String Cep;
	@Column(name = "NUMERO")
	private Integer Numero;
	@Column(name = "RG")
	private String Rg;
	@Column(name = "CPF")
	private String Cpf;
	@Column(name = "DATANASCIMENTO")
	private String DataNascimento;
	@Column(name = "TELEFONE")
	private String Telefone;
	@Column(name = "EMAIL")
	private String Email;
	@Column(name = "SENHA")
	private String Senha;
	
	
	/* Indica que o id é gerado automaticamente e 
	 * não é um valor atribuido pelo usuário
	 */
	
	public Cliente(DadosCadastroCliente requestClientes) {
		this.Nome = requestClientes.Nome();
		this.Endereco = requestClientes.Endereco();
		this.Cep = requestClientes.Cep();
		this.Numero = requestClientes.Numero();
		this.Rg = requestClientes.Rg();
		this.Cpf = requestClientes.Cpf();
		this.DataNascimento = requestClientes.DataNascimento();
		this.Telefone = requestClientes.Telefone();
		this.Email = requestClientes.Email();
		this.Senha = requestClientes.Senha();
	}
	
	public void atualizarDados(DadosAtualizacaoCliente data) {
		if (data.Nome() != null) {
			this.Nome = data.Nome();
		}
		if (data.Endereco() != null) {
			this.Endereco = data.Endereco();
		}
		if (data.Cep() != null) {
			this.Cep = data.Cep();
		}
		if (data.Numero() != null) {
			this.Numero = data.Numero();
		}
		if (data.Telefone() != null) {
			this.Telefone = data.Telefone();
		}
		if (data.Senha() != null) {
			this.Senha = data.Senha();
		}
	}
}
