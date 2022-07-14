package br.com.cadastro.curso.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.cadastro.curso.entities.Curso;
import br.com.cadastro.curso.repositories.CursoRepository;

@Service
public class CursoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CursoService.class);

	@PersistenceContext
	EntityManager em;
	
	@PersistenceContext
	EntityManager entity;

	@Autowired
	CursoRepository cursoRepository;

	@Transactional
	public void cadastrar(Curso curso) {
		LOGGER.info("Cadastrado");
		validaData(curso);
		validacao(curso);
		validacao1(curso);
		ValidarCurso(curso);
		cursoRepository.save(curso);
	}
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Transactional
	public void editar(Curso IdCurso) {
		LOGGER.info("Editado");
		validaData(IdCurso);
		validacao(IdCurso);
		validacaoEdita(IdCurso);
		ValidarCurso(IdCurso);
		cursoRepository.save(IdCurso);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Transactional
    public List<Curso> listar() {
        LOGGER.info("Lista de cursos retornada");
        return cursoRepository.findAll();
    }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void validacao(Curso curso) {
        if (curso.getDataAbertura().isBefore(LocalDate.now())) {
            LOGGER.info("Data de inicio não pode ser menor que a data atual!");
            throw new RuntimeException("Data de início não pode ser menor que a data atual!");

        }
    }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<Curso> listar(String descricao, LocalDate dataAbertura, LocalDate dataFechamento) {
		System.out.println(dataAbertura);
        CriteriaBuilder criteria = em.getCriteriaBuilder();
        CriteriaQuery<Curso> criteriaQuery = criteria.createQuery(Curso.class);

        Root<Curso> curso = criteriaQuery.from(Curso.class);
        List<Predicate> predList = new ArrayList<>();

        if (descricao != "") {
            Predicate descricaoPredicate = criteria.equal(curso.get("descricao"), descricao);
            predList.add(descricaoPredicate);
        }

        if (dataAbertura != null) {
            Predicate dataAberturaPredicate = criteria.greaterThanOrEqualTo(curso.get("dataAbertura"), dataAbertura);
            predList.add(dataAberturaPredicate);
        }

        if (dataFechamento != null) {
            Predicate dataFechamentoPredicate = criteria.lessThanOrEqualTo(curso.get("dataFechamento"), dataFechamento);
            predList.add(dataFechamentoPredicate);
        }

        Predicate[] predicateArray = new Predicate[predList.size()];

        predList.toArray(predicateArray);

        criteriaQuery.where(predicateArray);

        TypedQuery<Curso> query = em.createQuery(criteriaQuery);

        return query.getResultList();
    }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void validaData(Curso curso) {
		if (curso.getDataAbertura().isAfter(curso.getDataFechamento())) {
			LOGGER.info("Data permitida. Abertura maior que o fechamento.");
			throw new RuntimeException("Não permitido. Data abertura é maior que o fechamento.");
		}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	}
	
	private ResponseEntity<String> validacao1(Curso curso){
        Optional<Curso> cursos = cursoRepository.cadastrarCurso(curso.getDataAbertura(), curso.getDataFechamento());
        if (curso != null && !cursos.isEmpty()) {
            throw new RuntimeException("Existe(m) Curso(s) Planejados(s) Dentro do Período Informado");
        }

        return null;
    }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ResponseEntity<String> validacaoEdita (Curso curso){
        Optional<Curso> cursos = cursoRepository.editarCurso(curso.getDataAbertura(), curso.getDataFechamento(),curso.getIdCurso());
        if (curso != null && !cursos.isEmpty()) {
            LOGGER.info("Mensagem de log: data não validada");
            throw new RuntimeException("Existe(m) Curso(s) Planejados(s) Dentro do Período Informado");
        }

        return null;
    }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Curso recuperarCurso(Integer idCurso) {

		return cursoRepository.findById(idCurso).get();
	}
	
	public void validaDelete(Integer idCurso) {
		Optional<Curso> curso = cursoRepository.findById(idCurso);
		Curso c = curso.get();
		LOGGER.info("Não é permitido excluir cursos já realizados!");
		if (c.getDataAbertura().isBefore(LocalDate.now())) {
			throw new RuntimeException("Não é permitido excluir de cursos já realizados!");
		}
		cursoRepository.deleteById(idCurso);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void ValidarCurso(Curso curso) {

        for (Curso aux : cursoRepository.findAll()) {
            if (aux.getDescricao().equals(curso.getDescricao())) {
                throw new RuntimeException("Este curso já existe.");
            }
        }

    }

}
