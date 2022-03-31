package com.leonardorodrigues.apresentacao.Aplicacao2.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.leonardorodrigues.apresentacao.Aplicacao2.model.Conta;
import com.leonardorodrigues.apresentacao.Aplicacao2.repository.ContaRepository;
import com.leonardorodrigues.apresentacao.Aplicacao2.service.exception.ContaException;
import com.leonardorodrigues.apresentacao.Aplicacao2.service.exception.RecursoNaoEncontradoException;

// Aqui criamos as regras de negócios

@Service // Para indicar que ele está mantendo a lógica de negócios. Não há outra especialidade
public class ContaService {
	
	private ContaRepository contaRepository;
	
	public ContaService(ContaRepository contaRepository) {
		this.contaRepository = contaRepository;		
	}
	
	// Paginado, processo mais adequado
	// "Pageable" coloca quantidade de registro por página, ordenação 
	public Page<Conta> findAll(Pageable pageable){
		Page<Conta> list = contaRepository.findAll(pageable);
		return list;
	}
	
	
	// Crio um método
	public Conta getById(Long id) {
		Optional<Conta> conta = contaRepository.findById(id); // Encontrar por "ID"
		Conta p = conta.orElseThrow(() -> new RecursoNaoEncontradoException("Conta não encontrada"));
		return p;
		
		/* Aqui ele está pegando a "Conta" de interface e está usando um métodp
		 * que é encontrar por ID, então passo o "id" no parâmetro
		 * Pode acontecer de procurarmos um ID no banco e ele não encontrar esse ID
		 * Então ele pede para usarmos o "Optional<Conta>", ele que valida se tem ou não algo dentro dele 
		*/
		
		// Preciso criar uma classe que pegue esses valores e passe pra um get especifico, logo crio uma classe "ContaController" no package "controller"
	}
	
	// Nesse cenário pego os dados passados por parâmetro "conta" que são encaminhados da API e passar esses dados pra "conta" instanciada
	public Conta create (Conta conta) {
		try {
			Conta p = new Conta();
			p.setNameOwner(conta.getNameOwner());
			p.setLastNameOwner(conta.getLastNameOwner());
			p.setAgencyCode(conta.getAgencyCode());
			p.setNumberAccount(conta.getNumberAccount());
			p.setDigitVerification(conta.getDigitVerification());
			p.setRegisterID(conta.getRegisterID());
			
			contaRepository.save(p); // Método "save" que faz a persistência do banco que salva uma conta, seria como um "INSERT"		   
			return p;
		}catch(DataIntegrityViolationException e){
			throw new ContaException("Conta: "+conta.getNameOwner()+" já existe na base de dados");
		}

	}
	
	// Pode ser que o ID que você procura não está na base, então vamos tratar
	// Pegamos nesse caso um ID pra encontrar a conta
	@Transactional // Usado pra INSERT, UPDATE e DELETE. trabalha dentro do escopo de uma transação no banco de dados
	public Conta update (Conta conta, Long id) {
		try {
			Optional<Conta> p = contaRepository.findById(id); // getOne(id) pega através de um ID, uma conta 
			p.get().setNameOwner(conta.getNameOwner());
			p.get().setLastNameOwner(conta.getLastNameOwner());
			
			contaRepository.save(p.get());
			return p.get();
			
		}catch(EntityNotFoundException e){
			throw new RecursoNaoEncontradoException("Id: "+id+" não foi encontrado");
		}catch(NoSuchElementException e){
			throw new RecursoNaoEncontradoException("Id: "+id+" não foi encontrado");
		}	
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			contaRepository.deleteById(id);
			
		}catch(EmptyResultDataAccessException e) {
			throw new RecursoNaoEncontradoException("Id: "+id+" não foi encontrado");
		}catch(DataIntegrityViolationException e) {
			throw new ContaException("Integridade violada");
		}
	}

}

// Pegamos a interface para trabalhar com os serviços lá no controller
// Todas caracteristas da "interface" com a extensão JpaRepository eu passo para o "ContaService"
