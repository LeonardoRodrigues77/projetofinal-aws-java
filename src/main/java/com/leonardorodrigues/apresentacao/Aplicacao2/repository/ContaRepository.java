package com.leonardorodrigues.apresentacao.Aplicacao2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leonardorodrigues.apresentacao.Aplicacao2.model.Conta;

// Package "ContaRepository" que usaremos para mapear o banco de dados

@Repository // Identificar essa interface como repositório para fazer a conexão com o banco
public interface ContaRepository extends JpaRepository<Conta, Long>{
	
	/* Essa interface vai ser uma comunicação com o banco de dados 
	 * Esse processo é abstrato, exemplo, o ambiente de dev tem um BD, o de prod tem um BD
	 * O JpaRepository vai fazer independente em qual vai ser o banco de dados
	 * Quem gerencia as bibliotecas e as versões é o próprio Spring com Maven
	 * Passo a classe "Conta" como parâmetro, depois o tipo "Long" da variável
	 */

}

