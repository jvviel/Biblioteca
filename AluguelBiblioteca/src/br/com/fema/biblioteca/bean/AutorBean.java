package br.com.fema.biblioteca.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.fema.biblioteca.dao.AutorDao;
import br.com.fema.biblioteca.model.Autor;

@ManagedBean
@ViewScoped
public class AutorBean {

	private Autor autor;

	public Autor getAutor() {
		return autor;
	}
	
	public void novo(){
		
		autor = new Autor();
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	
	@PostConstruct
	public List<Autor> listarAutores(){
		
		AutorDao dao = new AutorDao();
		return dao.listarTodos();
	}
	
	public void salvarAutor(){
		
		AutorDao dao = new AutorDao();
		dao.adicionar(autor);
		
		String msg = "Autor " + autor.getNome() + " salvo com sucesso.";
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		
		autor = new Autor();
		listarAutores();
		
	}
	
	public void excluirAutor(Autor autor){
		
		AutorDao dao = new AutorDao();
		dao.remover(autor);
		String msg = "Autor " + autor.getNome() + " removido com sucesso.";
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		
		listarAutores();
	}


	public void selecionaAutor(ActionEvent evento){
		
		autor = (Autor) evento.getComponent().getAttributes().get("autorSelecionado");
	}
	
	public void editarAutor(){
		
		AutorDao dao = new AutorDao();
		dao.alterar(autor);
		
        String msg = "Autor " + autor.getNome() + " alterado com sucesso.";
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		
		listarAutores();
	}
	
	
	
}
