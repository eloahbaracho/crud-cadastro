package com.example.cadastro.controllers;

import org.springframework.http.ResponseEntity;

public record RequestClientes(String Nome, String Endereco, String Cep) {

}
