package com.nodebounty.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nodebounty.service.CartaoService;

@RestController
@RequestMapping("/cartoes")
@SuppressWarnings("rawtypes")
public class CartaoController {
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	CartaoService cartaoServico;
	/**
	 * Consulta catalogo
	 * @return - JSON Array com todos os produtos ou um JSON Array vazio
	 */
	@CrossOrigin // desabilita o cors do spring security
	@GetMapping
	public ResponseEntity<Object> consultaTodos() {
	 logger.info(">>>>>> apicontroller consulta todos");
	 return ResponseEntity.status(HttpStatus.OK).body(cartaoServico.consultaCartao());

	 
	}
	 /**
     * Exclui um cartão com base no seu ID.
     * @param cartaoId - O ID do cartão a ser excluído.
     * @return - ResponseEntity com um status HTTP que indica o resultado da exclusão.
     */
	
	 @CrossOrigin
	 @DeleteMapping("/{cartaoId}")
	 public ResponseEntity<Object> excluiCartao(@PathVariable Long cartaoId) {
		 logger.info(">>>>>> apicontroller exclui cartao: " + cartaoId);
 
		 boolean sucesso = cartaoServico.excluiCartao(cartaoId);
 
		 if (sucesso) {
			 return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		 } else {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartão não encontrado.");
		 }
		}

		@CrossOrigin // desabilita o cors do spring security
		@PostMapping("/gerar")
		public ResponseEntity<Object> gerarCartao() {
        logger.info(">>>>>> apicontroller cria cartao");
        
        // Lógica para criar o cartão usando cartaoServico
        boolean sucesso = cartaoServico.gerarCartao();

        if (sucesso) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar o cartão.");
        }
    }

}