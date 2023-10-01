package com.example.cadastro.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor

@Table(name = "clientes")
@Entity(name = "clientes")
@Getter 
@Setter

@EqualsAndHashCode(of = "id") /* Seleciona a chave primária da tabela em questão */

public class Clientes {
	
	@Id @GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String Nome;
	private String Endereco;
	private String Cep;
	private Integer Numero;
	private String Rg;
	private String Cpf;
	private String Email;
	private String DataNascimento;
	
	
	/* Indica que o id é gerado automaticamente e 
	 * não é um valor atribuido pelo usuário
	 */
	
	public Clientes(RequestClientes requestClientes) {
		this.Nome = requestClientes.Nome();
		this.Endereco = requestClientes.Endereco();
		this.Cep = requestClientes.Cep();
		this.Numero = requestClientes.Numero();
		this.Rg = requestClientes.Rg();
		this.Cpf = requestClientes.Cpf();
		this.Email = requestClientes.Email();
		this.DataNascimento = requestClientes.DataNascimento();
		
	}
}
