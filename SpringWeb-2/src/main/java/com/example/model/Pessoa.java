package com.example.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;



import jakarta.persistence.*;

@Entity
@Table(name = "pessoas")
public class Pessoa {
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private long id;
	  
	  @Column(name = "nome")
	  private String nome;
	  
	  @Column(name = "data_nasc")
	  private Date dataNasc;
	  
	  @ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		          CascadeType.PERSIST,
		          CascadeType.MERGE
		      })
		  @JoinTable(name = "pessoa_enderecos",
		        joinColumns = { @JoinColumn(name = "pessoa_id") },
		        inverseJoinColumns = { @JoinColumn(name = "endereco_id") })
	  private Set<Endereco> enderecos = new HashSet<>();
	  
	  public Pessoa() {

	  }
	  
	  public Pessoa(String nome, Date dataNasc) {
		    this.nome = nome;
		    this.dataNasc = dataNasc;
		   
	  }
	  
	  // getters and setters
	  
	  
	  public long getId() {
		    return id;
		  }

		  public String getNome() {
		    return nome;
		  }

		  public void setNome(String nome) {
		    this.nome = nome;
		  }
		  
		  
		  public Date getDataNasc() {
			    return dataNasc;
			  }

			  public void setDataNasc(Date dataNasc) {
			    this.dataNasc = dataNasc;
			  }
			  
			  
			  public Set<Endereco> getEnderecos() {
				    return enderecos;
				  }

				  public void setTags(Set<Endereco> enderecos) {
				    this.enderecos = enderecos;
				  }

	  
	  public void addEndereco(Endereco endereco) {
		    this.enderecos.add(endereco);
		    endereco.getPessoas().add(this);
		  }
		  
		  public void removeEndereco(long enderecoId) {
		    Endereco endereco = this.enderecos.stream().filter(t -> t.getId() == enderecoId).findFirst().orElse(null);
		    if (endereco != null) {
		      this.enderecos.remove(endereco);
		      endereco.getPessoas().remove(this);
		    }
		  }

	  
}
