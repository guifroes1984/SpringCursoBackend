package br.com.cadastro.curso.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cadastro.curso.entities.Categoria;

import br.com.cadastro.curso.repositories.ICategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private ICategoriaRepository categoriaRepository;

	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}

}
