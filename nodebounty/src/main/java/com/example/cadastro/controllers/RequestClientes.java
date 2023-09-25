package com.example.cadastro.controllers;

import jakarta.validation.constraints.NotBlank;

public record RequestClientes(
		@NotBlank String Nome, 
		@NotBlank String Endereco, 
		@NotBlank String Cep
		){

}
