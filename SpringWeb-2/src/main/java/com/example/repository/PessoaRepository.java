package com.example.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	List<Pessoa> findPessoasByEnderecosId(Long enderecoId);
	
	List<Pessoa> findByDataNasc(Date dataNasc);

	List<Pessoa> findByNameContaining(String nome);
	  
	  
}
