package com.nodebounty.domain.contacorrente;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.nodebounty.domain.cliente.Cliente;
import com.nodebounty.domain.plano.Plano;
import com.nodebounty.domain.transacao.Transacao;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "CONTACORRENTE")
public class ContaCorrente {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "IDCONTA")
	private String idConta;
	
	/* Associação 1:1, uma conta corrente possui somente um cliente, a ligação ocorre por meio do atributo de Cliente.java -> IDCLIENTE */
	@OneToOne
	@JoinColumn(name = "IDCLIENTE", referencedColumnName = "IDCLIENTE")
	private Cliente cliente;

	/* Associação 1:1, uma conta corrente possui somente um plano, a ligação ocorre por meio do atributo de Plano.java -> IDPLANO */
	@OneToOne
	@JoinColumn(name = "IDPLANO", referencedColumnName = "IDPLANO") 
	private Plano plano;

	@Column(name = "SALDOCONTA")
	private double saldoConta;
	
	/* Como a gente não vai usar o número para nenhuma operação matématica, acho que String é mais performático */
	@Column(name = "NUMEROCONTA")
	private String numeroConta;
	
	// Propriedade que guarda todas transições que a conta aparece como emissor
	@OneToMany(mappedBy = "emissor", cascade = CascadeType.PERSIST)
    private List<Transacao> transacoesComoEmissor = new ArrayList<>();

 // Propriedade que guarda todas transições que a conta aparece como receptor
	@OneToMany(mappedBy = "receptor", cascade = CascadeType.PERSIST)
    private List<Transacao> transacoesComoReceptor = new ArrayList<>();
	
	/* Método que será executado ANTES de salvar a conta no banco. Ele vai gerar um número aleatório de 20 digitos
	 * para a conta corrente e converter para string*/
    @PrePersist
    protected void onCreate() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(20);
        for (int i = 0; i < 20; i++) {
            sb.append(random.nextInt(10)); // Append a random digit (0-9)
        }
        this.numeroConta = sb.toString();
    }
    
    public void depositar(double valor) {
    	this.saldoConta += valor;
    }
    
    public void sacar(double valor) {
    	this.saldoConta -= valor;
    }
	
}