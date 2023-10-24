package com.nodebounty.domain.contacorrente;

import java.util.Random;

import com.nodebounty.domain.cliente.Cliente;
import com.nodebounty.domain.plano.Plano;
import com.nodebounty.domain.transacao.Transacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
	private double saldoConta = 0.0;
	
	/* Como a gente não vai usar o número para nenhuma operação matématica, acho que String é mais performático */
	@Column(name = "NUMEROCONTA")
	private String numeroConta;
	
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

	private static int ContadorDeContas = 1;
	
	public String getnumeroConta() {
		return numeroConta;
	}
	
	public void setnumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Double getSaldo() {
		return saldoConta = 0.0;
		
	}
	
	public void setSaldo(Double saldoConta) {
		this.saldoConta = saldoConta;
	}
	
	public String toString() {
		return "\nNome: " + this.cliente.getNome() +
		 		"\nCpf: " + this.cliente.getCpf() +
		 		"\nNumero da Conta: " + this.getnumeroConta() +
		 		"\nEmail: " + this.cliente.getEmail() +
		 		"\nSaldo: " + Transacao.DoubleToString(this.getSaldo()) +
		 		"\n";
	}
	
	/* ------------------------------------------------------------- */
    @Autowired /* Injeção de dependência */
	private ClienteRepository repository;
    @PostMapping("/depositar")
    @Transactional
    public ResponseEntity depositar(@RequestBody @Valid DadosTransacaoCliente data) {
        Optional<Cliente> optionalCliente = repository.findById(data.idCliente());
 
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            double valorDeposito = DadosTransacaoCliente
            
            if (valorDeposito > 0) {
                cliente.depositar(valorDeposito);
                repository.save(cliente);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().body("O valor do depósito deve ser maior que zero.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    /* ------------------------------------------------------------- */
    @PostMapping("/transferir")
    @Transactional
    public ResponseEntity transferir(@RequestBody @Valid DadosTransacaoCliente data) {
        Optional<Cliente> remetenteOptional = repository.findById(dadosTransferencia.idRemetente());
        Optional<Cliente> destinatarioOptional = repository.findById(dadosTransferencia.idDestinatario());
 
        if (remetenteOptional.isPresent() && destinatarioOptional.isPresent()) {
            Cliente remetente = remetenteOptional.get();
            Cliente destinatario = destinatarioOptional.get();
            double valorTransferencia = valorTransferencia.getValor();
 
            if (valorTransferencia > 0 && remetente.possuiSaldoSuficiente(valorTransferencia)) {
                remetente.sacar(valorTransferencia);
                destinatario.depositar(valorTransferencia);
                repository.save(remetente);
                repository.save(destinatario);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().body("Transferência não pode ser realizada. Verifique o saldo e o valor da transferência.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    /* ------------------------------------------------------------- */
    @PostMapping("/sacar")
    @Transactional
    public ResponseEntity sacar(@RequestBody @Valid DadosTransacaoCliente data) {
        Optional<Cliente> optionalCliente = repository.findById(dadosSaque.idCliente());
 
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            double valorSaque = dadosSaque.getValor();
 
            if (valorSaque > 0 && cliente.possuiSaldoSuficiente(valorSaque)) {
                cliente.sacar(valorSaque);
                repository.save(cliente);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().body("Saque não pode ser realizado. Verifique o saldo e o valor do saque.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
}
}
