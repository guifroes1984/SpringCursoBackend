package br.com.cadastro.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cadastro.curso.entities.Categoria;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {
	

}
