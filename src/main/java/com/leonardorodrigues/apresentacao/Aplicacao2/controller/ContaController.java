package com.leonardorodrigues.apresentacao.Aplicacao2.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.leonardorodrigues.apresentacao.Aplicacao2.model.Conta;
import com.leonardorodrigues.apresentacao.Aplicacao2.service.ContaService;

@RestController
@RequestMapping("api/v1/contas")
public class ContaController {
	
	private final ContaService contaService;
	
	// Coloco um construtor
	public ContaController(ContaService contaService) {
		this.contaService = contaService;
	}
	
	@GetMapping
	public ResponseEntity<Page<Conta>> getAll(Pageable pageable){
		Page<Conta> listaConta = contaService.findAll(pageable);
		return ResponseEntity.ok().body(listaConta);
	}
	
	@GetMapping("/{id}") // Quero pegar a conta pelo "ID", logo passo o parâmetro
	public ResponseEntity<Conta> getById(@PathVariable Long id) { // Passo o "PathVariable" que é a variável do meu parâmetro. Consigo pegar o meu "ID" e passar pro meu processo lá no "ContaService"
		Conta conta = contaService.getById(id);
		return ResponseEntity.ok(conta);
	}
	
	@PostMapping //  Funciona como um atalho para arquivos @RequestMapping
	public ResponseEntity<Conta> create(@RequestBody Conta conta){ // @RequestMapping permite o mapeamento fácil de parâmetros de URL 
		// Pega o objeto pessoa por parâmetro e vai passar pro "ContaService", na linha "public Pessoa create (Pesoa pessoa)" e depois passo o valor de "getNameOwner" pra requisição
	    Conta p = contaService.create(conta);
	    // Processo usado pra passar pro Header na API
	    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	    		.buildAndExpand(p.getId()).toUri();	    	    
	    return ResponseEntity.created(location).body(p); // Fazendo isso, lá no "Header" no postman aparece a URL pra achar esse cadastro
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Conta> update(@RequestBody Conta conta, @PathVariable Long id){
		conta = contaService.update(conta, id); 
		return ResponseEntity.noContent().build();
		//return ResponseEntity.ok().body(conta);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Conta> delete(@PathVariable Long id){
		contaService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}

// @PathVariable pode ser usada para manipular variáveis ​​de modelo no mapeamento de URI de solicitação e defini-las como parâmetros de método.
// ResponseEntity representa toda a resposta HTTP: código de status, cabeçalhos e corpo
// Data Transfer Object (DTO) => para o transporte de dados entre diferentes componentes de um sistema, diferentes instâncias ou processos de um sistema distribuído ou diferentes sistemas via serialização.
// ServletUriComponentsBuilder => Extrai informações do HttpServletRequest


/* RESUMO ATÉ O MOMENTO 05:11:30: 
 * 1° foi criado um modelo "Pessoa.java" no package "apresentacao.model" nele aplicamos a notação "@generatedValue" pra gerar o ID automático no caso de inserção de dados 
 * 2° Depois criamos o "PessoaRepository.java" no package "apresentacao.repository" que é a minha interface para comunicação com o banco de dados e ele faz isso verificando o "Pessoa" como parâmetro
 * 3° Em "PessoaService.java" no package "apresentacao.service" chamamos o "pessoaRepository" e passamos o método "findById", ou seja, ele vai lá no processo da "tabela pessoa", verifica se o ID foi encontrado, se sim ele passa como retorno, senão ele dá uma exceção de "Pessoa não encontrada". 
 * 4° Depois criamos o "PessoaController" no package "apresentacao.controller" que é o responsável por mandar os dados pro servidor e você conseguir busca pessoa pelo ID que está sendo passada no parâmetro. 
 */


