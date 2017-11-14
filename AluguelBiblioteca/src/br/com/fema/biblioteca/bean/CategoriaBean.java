package br.com.fema.biblioteca.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.fema.biblioteca.dao.CategoriaDao;
import br.com.fema.biblioteca.model.Categoria;

@ManagedBean
@ViewScoped
public class CategoriaBean {

	private Categoria categoria;

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public void novo(){
		categoria = new Categoria();
	}
	
	@PostConstruct
	public List<Categoria> listarCategorias(){
		
		CategoriaDao dao = new CategoriaDao();
		return dao.listarTodos();
	}
	
	public void salvarCategoria(){
		
		CategoriaDao dao = new CategoriaDao();
		dao.adicionar(categoria);
		
		String msg = "Categoria " + categoria.getDescricao() + " salvo com sucesso.";
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		
		novo();
		listarCategorias();
	}
	
	public void excluirCategoria(Categoria categoria){
		
		CategoriaDao dao = new CategoriaDao();
		dao.remover(categoria);
		
		String msg = "Categoria " + categoria.getDescricao() + " removido com sucesso";
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		
		listarCategorias();
	}
	
	public void selecionaCategoria(ActionEvent evento){
		categoria = (Categoria) evento.getComponent().getAttributes().get("categoriaSelecionado");
	}
	
	public void editarAutor(){
		
		CategoriaDao dao = new CategoriaDao();
		dao.alterar(categoria);
		
		listarCategorias();
	}
}
