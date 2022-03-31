package com.leonardorodrigues.apresentacao.Aplicacao2.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // É utilizada para informar que uma classe também é uma entidade. A partir disso, a JPA estabelecerá a ligação entre a entidade e uma tabela de mesmo nome no banco de dados, onde os dados de objetos desse tipo poderão ser persistidos.
@Table(name = "TB_CONTA") // É a maneira mais fácil de definir um nome de tabela SQL personalizado
public class Conta implements Serializable{
	
	    // Se refere a conversões de objetos java para bytes para serem salvos no banco
		// SerialUID é um controle dessas conversões feito pela JVM
		private static final long serialversionUID = 1L;
		
		@Id // É utilizada para informar ao JPA qual campo/atributo de uma entidade estará relacionado à chave primária da respectiva tabela no banco de dados. 
		@GeneratedValue(strategy = GenerationType.IDENTITY) // É utilizada para informar que a geração do valor do identificador único da entidade será gerenciada pelo provedor de persistência
		// Toda vez que eu inserir um dado, a própria biblioteca do spring JPA vai gerar um ID automáticoVai inserir ID 1, ID 2 e assim por diante
		private Long id;
		
		@Column(name="nameOwner", length = 80, unique = true)
	    private String nameOwner;
	    
	    //@Column(nullable = false, length = 4)
	    private String agencyCode;
	    
	    //@Column(nullable = false, length = 8)
	    private String numberAccount; 
	    
	    //@Column(nullable = false, length = 3)
	    private String digitVerification;
	    
	    //@Column(nullable = false, length = 20)
	    private String registerID; 
	    
	    @Column(name="last_Name_Owner", length = 80)
	    private String lastNameOwner; 
	    
	    public Conta() { // Construtor vazio	
	    }
        
	    // Crio meus Getter e Setter
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getNameOwner() {
			return nameOwner;
		}

		public void setNameOwner(String nameOwner) {
			this.nameOwner = nameOwner;
		}

		public String getAgencyCode() {
			return agencyCode;
		}

		public void setAgencyCode(String agencyCode) {
			this.agencyCode = agencyCode;
		}

		public String getNumberAccount() {
			return numberAccount;
		}

		public void setNumberAccount(String numberAccount) {
			this.numberAccount = numberAccount;
		}

		public String getDigitVerification() {
			return digitVerification;
		}

		public void setDigitVerification(String digitVerification) {
			this.digitVerification = digitVerification;
		}

		public String getRegisterID() {
			return registerID;
		}

		public void setRegisterID(String registerID) {
			this.registerID = registerID;
		}

		public String getLastNameOwner() {
			return lastNameOwner;
		}

		public void setLastNameOwner(String lastNameOwner) {
			this.lastNameOwner = lastNameOwner;
		}
        
		// Só pra evitar ao máximo a possibilidade de duplicação de ID
		@Override
		public int hashCode() {
			return Objects.hash(id);
		}
        
		// Só pra evitar ao máximo a possibilidade de duplicação de ID
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Conta other = (Conta) obj;
			return Objects.equals(id, other.id);
		}  
			      	     
}

// CONTINUAR 04:34:00

// Obs.: Criamos a "Model", a classe "Conta" é um modelo
// Obs.: Package "Repository" é pra fazer comunicação com o H2 ou algum SGBD
// Obs.: Package "service" é para criar as regras de negócio
// Obs.: Package "controller" vai pegar as informações do package "model" e "service" e distribui para termos a visão no Postman API (Endpoint)