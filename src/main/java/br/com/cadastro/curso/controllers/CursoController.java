package br.com.cadastro.curso.controllers;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cadastro.curso.CursoApplication;
import br.com.cadastro.curso.entities.Curso;
import br.com.cadastro.curso.services.CursoService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/cursos")
public class CursoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CursoApplication.class);

	@Autowired
	private CursoService service;

	@ApiOperation("Serviço para cadastrar novo curso")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> cadastrar(@RequestBody Curso curso) {
		try {
			service.cadastrar(curso);
			LOGGER.info("Inicio da Api de cadastro");
			return ResponseEntity.status(HttpStatus.CREATED).body("Curso cadastrado com sucesso");
		} catch (Exception e) {
			LOGGER.error("Erro da Api de cadastro");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("erro:" + e.getMessage());
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@ApiOperation("Serviço de atualização dos dados dos Cursos")
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody Curso curso) {
		try {
			service.editar(curso);
			LOGGER.info("Atualização da Api de cadastro");
			return ResponseEntity.status(HttpStatus.OK).body("Curso editado");
		} catch (Exception e) {
			LOGGER.error("Erro ao atualizar a Api de cadastro");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("erro:" + e.getMessage());
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@ApiOperation("Serviço para deletar curso por ID")
	@DeleteMapping(value = "/{IdCurso}")
	public ResponseEntity<String> deletar(@PathVariable("IdCurso") Integer IdCurso) {
		try {
			service.validaDelete(IdCurso);
			LOGGER.info("Deletar na Api de cadastro");
			return ResponseEntity.status(HttpStatus.OK).body("Curso deletado com sucesso");

		} catch (Exception e) {
			LOGGER.error("Erro ao deletar a Api de cadastro");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@ApiOperation("Serviço para consulta de Cursos, por descriçao e data!")
	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<Curso>> listarTudo(@RequestParam(required = false) String descricao,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataAbertura,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFechamento) {
		List<Curso> curso = service.listar(descricao, dataAbertura, dataFechamento);

		return ResponseEntity.ok().body(curso);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@ApiOperation("Serviço para consulta/edição de Cursos")
	@GetMapping(value = "/{IdCurso}")
	public ResponseEntity<Curso> getCurso(@PathVariable("IdCurso") Integer IdCurso) {
		try {

			LOGGER.info("Edição na Api de cadastro");
			return ResponseEntity.ok(service.recuperarCurso(IdCurso));

		} catch (Exception e) {
			LOGGER.error("Erro ao editar a Api de cadastro");
			return null;
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
