package br.com.cadastro.curso.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cadastro.curso.entities.Curso;

@Repository("CursoRepository")
public interface CursoRepository  extends JpaRepository<Curso, Integer> {
	
	@Query("SELECT c from Curso c  where c.dataAbertura <= :data_abertura AND c.dataFechamento >= :data_fechamento") 
    Optional<Curso> cadastrarCurso(@Param("data_abertura") LocalDate data_inicio, @Param("data_fechamento") LocalDate data_fim);

    @Query("SELECT c from Curso c  where c.dataAbertura <= :data_abertura AND c.dataFechamento >= :data_fechamento AND idcurso != :idcurso") 
    Optional<Curso> editarCurso(@Param("data_abertura") LocalDate data_inicio, @Param("data_fechamento") LocalDate data_fim, @Param("idcurso") Integer idcurso);

}
