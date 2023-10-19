package com.nodebounty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.nodebounty.domain.cartao.Cartao;
import com.nodebounty.domain.cartao.CartaoRepository;

import jakarta.el.ELException;

@Service
public class CartaoService {

	@@ -26,7 +37,37 @@ public class CartaoService {
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
