package com.example.model;


import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "enderecos")
public class Endereco {
	@Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private long id;
	
	  @Column(name = "logradouro")
	  private String logradouro;
	
	  @Column(name = "cep")
	  private String cep;
	  
	  @Column(name = "numero")
	  private String numero;
	
	  @Column(name = "cidade")
	  private String cidade;
	  
	  @ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		          CascadeType.PERSIST,
		          CascadeType.MERGE
		      },
		      mappedBy = "tags")
		  @JsonIgnore
		  private Set<Pessoa> pessoas = new HashSet<>();
	  
	  
	  public Endereco() {

	  }
	  
	  public long getId() {
		    return id;
	  }
	  
	  
	  public String getLogradouro() {
		    return logradouro;
	  }
	  
	  public void setLogradouro(String logradouro) {
		    this.logradouro = logradouro;
	  }
	  
	  
	  public String getCep() {
		    return cep;
	  }
	  
	  public void setCep(String cep) {
		    this.cep = cep;
	  }
	  
	  
	  public String getNumero() {
		    return numero;
	  }
	  
	  public void setNumero(String numero) {
		    this.numero = numero;
	  }
	  
	  
	  public String getCidade() {
		    return cidade;
	  }
	  
	  public void setCidade(String cidade) {
		    this.cidade = cidade;
	  }
	  
	  public Set<Pessoa> getPessoas() {
		    return pessoas;
		  }

		  public void setPessoas(Set<Pessoa> pessoas) {
		    this.pessoas = pessoas;
		  }  
	  
}
