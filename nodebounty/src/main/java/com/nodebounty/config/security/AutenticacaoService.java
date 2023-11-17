package com.nodebounty.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nodebounty.domain.cliente.ClienteRepository;

/* Implementando classe necessária para configurar autenticação */
@Service
public class AutenticacaoService implements UserDetailsService {
	
	@Autowired
	private ClienteRepository repository;

	/* Método para autenticar o usuário, buscando os dados do banco de dados*/
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return repository.findByEmail(email);
	}

}
