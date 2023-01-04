package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.model.Pessoa;
import com.example.model.Endereco;
import com.example.repository.PessoaRepository;
import com.example.repository.EnderecoRepository;
import com.example.exception.ResourceNotFoundException;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class EnderecoController {
	
	@Autowired
	  private PessoaRepository pessoaRepository;

	  @Autowired
	  private EnderecoRepository enderecoRepository;

	  @GetMapping("/enderecos")
	  public ResponseEntity<List<Endereco>> getAllEnderecos() {
	    List<Endereco> enderecos = new ArrayList<Endereco>();

	    enderecoRepository.findAll().forEach(enderecos::add);

	    if (enderecos.isEmpty()) {
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

	    return new ResponseEntity<>(enderecos, HttpStatus.OK);
	  }
	  
	  @GetMapping("/pessoas/{pessoaId}/pessoas")
	  public ResponseEntity<List<Endereco>> getAllEnderecosByPessoaId(@PathVariable(value = "pessoaId") Long pessoaId) {
	    if (!pessoaRepository.existsById(pessoaId)) {
	      throw new ResourceNotFoundException("Not found Pessoa with id = " + pessoaId);
	    }

	    List<Endereco> enderecos = enderecoRepository.findEnderecoByPessoasId(pessoaId);
	    return new ResponseEntity<>(enderecos, HttpStatus.OK);
	  }

	  @GetMapping("/enderecos/{id}")
	  public ResponseEntity<Endereco> getEnderecosById(@PathVariable(value = "id") Long id) {
	    Endereco enderecos = enderecoRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found Endereco with id = " + id));

	    return new ResponseEntity<>(enderecos, HttpStatus.OK);
	  }
	  
	  @GetMapping("/enderecos/{enderecoId}/pessoas")
	  public ResponseEntity<List<Pessoa>> getAllPessoasByEnderecoId(@PathVariable(value = "enderecoId") Long enderecoId) {
	    if (!enderecoRepository.existsById(enderecoId)) {
	      throw new ResourceNotFoundException("Not found Endereco  with id = " + enderecoId);
	    }

	    List<Pessoa> pessoas = pessoaRepository.findPessoasByEnderecosId(enderecoId);
	    return new ResponseEntity<>(pessoas, HttpStatus.OK);
	  }

	  @PostMapping("/pessoas/{pessoaId}/enderecos")
	  public ResponseEntity<Endereco> addEndereco(@PathVariable(value = "pessoaId") Long pessoaId, @RequestBody Endereco enderecoRequest) {
	    Endereco endereco = pessoaRepository.findById(pessoaId).map(pessoa -> {
	      long enderecoId = enderecoRequest.getId();
	      
	      // endereco is existed
	      if (enderecoId != 0L) {
	        Endereco _endereco = enderecoRepository.findById(enderecoId)
	            .orElseThrow(() -> new ResourceNotFoundException("Not found Endereco with id = " + enderecoId));
	        pessoa.addEndereco(_endereco);
	        pessoaRepository.save(pessoa);
	        return _endereco;
	      }
	      
	      // add and create new Tag
	      pessoa.addEndereco(enderecoRequest);
	      return enderecoRepository.save(enderecoRequest);
	    }).orElseThrow(() -> new ResourceNotFoundException("Not found Pessoa with id = " + pessoaId));

	    return new ResponseEntity<>(endereco, HttpStatus.CREATED);
	  }

	  @PutMapping("/enderecos/{id}")
	  public ResponseEntity<Endereco> updateEndereco(@PathVariable("id") long id, @RequestBody Endereco enderecoRequest) {
	    Endereco endereco = enderecoRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("EnderecoId " + id + "not found"));

	    endereco.setLogradouro(enderecoRequest.getLogradouro());

	    return new ResponseEntity<>(enderecoRepository.save(endereco), HttpStatus.OK);
	  }
	 
	  @DeleteMapping("/pessoas/{pessoaId}/enderecos/{enderecoId}")
	  public ResponseEntity<HttpStatus> deleteEnderecoFromPessoa(@PathVariable(value = "pessoaId") Long pessoaId, @PathVariable(value = "enderecoId") Long enderecoId) {
	    Pessoa pessoa = pessoaRepository.findById(pessoaId)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found Pessoa with id = " + pessoaId));
	    
	    pessoa.removeEndereco(enderecoId);
	    pessoaRepository.save(pessoa);
	    
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
	  
	  @DeleteMapping("/enderecos/{id}")
	  public ResponseEntity<HttpStatus> deleteEndereco(@PathVariable("id") long id) {
	    enderecoRepository.deleteById(id);

	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
	

}
