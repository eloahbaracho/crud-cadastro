package com.nodebounty.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nodebounty.domain.cartao.Cartao;
import com.nodebounty.domain.cartao.CartaoRepository;

import jakarta.el.ELException;

@Service
public class CartaoService {

	@Autowired
	private CartaoRepository repository;

	/*
	 * Método para listar todos os cartões do banco. Poderiamos usar o repository
	 * direto no controller e chamar o findAll() lá? Poderiamos, mas eu desconfio
	 * que eventualmente vamos precisar usar de fato uma classe de serviço pro
	 * cartão, então melhor deixar aqui mesmo
	 */

	/*
	 * TALVEZ O PROFESSOR RECLAME TAMBÉM, e a gente acabe tendo que criar um service
	 * pra todas as classes mesmo que possa usar o repository direto, vamos ver.
	 */

	Logger logger = LogManager.getLogger(this.getClass());


    public List<Cartao> consultaCartao() {
        return repository.findAll();
    }


    public boolean gerarCartao(){
        Cartao novoCartao =new Cartao();
        try{
            repository.save(novoCartao);
            return true;
        }
        catch(ELException e){
            logger.info(e);
            return false;
        }
        
    }
    
    public boolean excluiCartao(Long cartaoId) {
        Optional<Cartao> cartaoOptional = repository.findById(cartaoId);

        if (cartaoOptional.isPresent()) {
            repository.delete(cartaoOptional.get());
            logger.info("Cartão com ID " + cartaoId + " excluído com sucesso.");
            return true;
        } else {
            logger.info("Tentativa de exclusão de um cartão inexistente com ID " + cartaoId);
            return false;
        }
    }
}
