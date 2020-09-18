package Entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Empresa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String nome;
	private String cnpj;
	
	@OneToMany
	private List<Empregado> empregados = new ArrayList();
	
	@OneToOne
	private Endereço endereco;
	
	@OneToOne
	private Telefone telefone;

	public Empresa(long id, String nome, String cnpj) {
		this.id = id;
		this.nome = nome;
		this.cnpj = cnpj;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public List<Empregado> getEmpregados() {
		return empregados;
	}

	public void AddEmpregado(Empregado empregado) {
		empregados.add(empregado);
	}
	
	
	
	
}
