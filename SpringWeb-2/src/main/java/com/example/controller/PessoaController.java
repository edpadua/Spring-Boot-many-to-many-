package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Pessoa;
import com.example.repository.PessoaRepository;

public class PessoaController {
	 @Autowired
	 PessoaRepository pessoaRepository;

	 
	 @GetMapping("/pessoas")
	  public ResponseEntity<List<Pessoa>> getAllPessoas(@RequestParam(required = false) String nome) {
	    List<Pessoa> pessoas = new ArrayList<Pessoa>();

	    if (nome == null)
	      pessoaRepository.findAll().forEach(pessoas::add);
	    else
	    	pessoaRepository.findByNameContaining(nome).forEach(pessoas::add);

	    if (pessoas.isEmpty()) {
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

	    return new ResponseEntity<>(pessoas, HttpStatus.OK);
	  }
	 
	 @GetMapping("/pessoas/{id}")
	  public ResponseEntity<Pessoa> getPessoaById(@PathVariable("id") long id) {
	    Pessoa pessoa = pessoaRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found pessoa with id = " + id));

	    return new ResponseEntity<>(pessoa, HttpStatus.OK);
	  }
	 
	 @PostMapping("/pessoas")
	  public ResponseEntity<Pessoa> createTutorial(@RequestBody Pessoa pessoa) {
	    Pessoa _pessoa = pessoaRepository.save(new Pessoa(pessoa.getNome(), pessoa.getDataNasc()));
	    return new ResponseEntity<>(_pessoa, HttpStatus.CREATED);
	  }
	 
	 
	 @PutMapping("/pessoas/{id}")
	  public ResponseEntity<Pessoa> updateTutorial(@PathVariable("id") long id, @RequestBody Pessoa pessoa) {
	    Pessoa _pessoa = pessoaRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found Pessoa with id = " + id));

	    _pessoa.setNome(pessoa.getNome());
	    _pessoa.setDataNasc(pessoa.getDataNasc());
	  
	    
	    return new ResponseEntity<>(pessoaRepository.save(_pessoa), HttpStatus.OK);
	  }
	 
	 
	 @DeleteMapping("/pessoas/{id}")
	  public ResponseEntity<HttpStatus> deletePessoa(@PathVariable("id") long id) {
	    pessoaRepository.deleteById(id);
	    
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }

	  @DeleteMapping("/pessoas")
	  public ResponseEntity<HttpStatus> deleteAllPessoas() {
	    pessoaRepository.deleteAll();
	    
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
	 
}
