package com.leonardorodrigues.apresentacao.Aplicacao2.handle;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.leonardorodrigues.apresentacao.Aplicacao2.model.exception.ErroPadrao;
import com.leonardorodrigues.apresentacao.Aplicacao2.service.exception.ContaException;
import com.leonardorodrigues.apresentacao.Aplicacao2.service.exception.RecursoNaoEncontradoException;

@ControllerAdvice
public class ResourceExceptionHandle {
	
	// Customizando o erro
	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<ErroPadrao> entidadeNaoEncontrada(RecursoNaoEncontradoException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErroPadrao erro = new ErroPadrao();
		erro.setTimestamp(Instant.now());
		erro.setStatus(status.value());
		erro.setError("Recurso não encontrado");
		erro.setMessage(e.getMessage());
		erro.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(erro);
	
	/* Tenho o @ControllerAdvice que pega as exceções 
	 * @ExceptionHandler vai pegar a exceção quando vier com "RecursoNaoEncontradoException" 
	 * e vai disparar pro "PessoaController", na linha "Pessoa pessoa = pessoaService.getById(id) 
	 * vier com algum problema ele vai chegar no "PessoaService na linha "RuntimeException" e vai retornar "Pessoa não encontrada"
	 */
		
	}
	
	@ExceptionHandler(ContaException.class)
	public ResponseEntity<ErroPadrao> contaConflito(ContaException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.CONFLICT;
		ErroPadrao erro = new ErroPadrao();
		erro.setTimestamp(Instant.now());
		erro.setStatus(status.value());
		erro.setError("Essa conta já existe pra outra pessoa"); 
		erro.setMessage(e.getMessage());
		erro.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(erro);		
	}

}

// @ExceptionHandler => Usada para tratar exceções em classes
// HttpServletRequest => Serve pra pegar os dados da requisição como um erro no processo 
