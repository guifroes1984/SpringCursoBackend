package br.com.cadastro.curso.request;

import java.time.LocalDate;



import br.com.cadastro.curso.entities.Categoria;

public class CursoPutRequest {
	
	
	private Integer IdCurso;
	private String descricao;
	private String qtdAluno;
	private LocalDate dataAbertura;
	private LocalDate dataFechamento;
	
	private Categoria categoria;

	public Integer getIdCurso() {
		return IdCurso;
	}

	public void setIdCurso(Integer idCurso) {
		IdCurso = idCurso;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getQtdAluno() {
		return qtdAluno;
	}

	public void setQtdAluno(String qtdAluno) {
		this.qtdAluno = qtdAluno;
	}

	public LocalDate getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDate dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public LocalDate getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(LocalDate dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
	

}
