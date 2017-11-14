package br.com.fema.biblioteca.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.fema.biblioteca.dao.AutorDao;
import br.com.fema.biblioteca.dao.CategoriaDao;
import br.com.fema.biblioteca.dao.LivroDao;
import br.com.fema.biblioteca.dao.StatusLivroDao;
import br.com.fema.biblioteca.model.Autor;
import br.com.fema.biblioteca.model.Categoria;
import br.com.fema.biblioteca.model.Livro;
import br.com.fema.biblioteca.model.StatusLivro;


@ManagedBean
@ViewScoped
public class LivroBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Livro livro;
	private Integer idAutor;
	private Integer idCategoria;
	private Integer idStatus;
	private List<Livro> livros;

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Integer getIdAutor() {
		return idAutor;
	}

	public void setIdAutor(Integer idAutor) {
		this.idAutor = idAutor;
	}
	
	public Integer getIdCategoria() {
		return idCategoria;
	}
	
	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}
	
	public Integer getIdStatus() {
		return idStatus;
	}
	
	public void setIdStatus(Integer idStatus) {
		this.idStatus = idStatus;
	}

	public List<Livro> getLivros() {
		if (this.livros == null) {
			this.livros = listarLivro();
		}
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	public void novo() {

		livro = new Livro();
	}

	@PostConstruct
	public List<Livro> listarLivro() {
		LivroDao dao = new LivroDao();
		livros = dao.listarTodos();
		return livros;
	}

	public List<Autor> listarAutores() {
		AutorDao dao = new AutorDao();
		return dao.listarTodos();
	}
	
	public List<Categoria> listarCategorias(){
		CategoriaDao dao = new CategoriaDao();
		return dao.listarTodos();
	}
	
	public List<StatusLivro> listarStatus(){
		StatusLivroDao dao = new StatusLivroDao();
		return dao.listarTodos();
	}

	public void salvarLivro() {
	
			LivroDao dao = new LivroDao();
			
			Categoria cat = new Categoria();
			CategoriaDao catDao = new CategoriaDao();
			cat = catDao.buscaPorId(idCategoria);

			Autor autor = new Autor();
			AutorDao autorDao = new AutorDao();
			autor = autorDao.buscaPorId(idAutor);
			
			StatusLivro status = new StatusLivro();
			StatusLivroDao statusDao = new StatusLivroDao();
			status = statusDao.buscaPorId(idStatus);
			
			this.livro.setAutor(autor);
			this.livro.setCategoria(cat);
			this.livro.setStatus(status);
			dao.adicionar(livro);
			livros = listarLivro();
			

			String msg = "Livro " + livro.getLivro() + " salvo com sucesso";
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);

			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, message);
			livro = new Livro();
		
	}


	public void excluir(Livro livro) {

			LivroDao dao = new LivroDao();
			dao.remover(livro);
			String msg = "Livro " + livro.getLivro() + " Removido com sucesso";
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);

			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, message);

			listarLivro();

	}

	public void selecionaLivro(ActionEvent evento) {

		livro = (Livro) evento.getComponent().getAttributes().get("livroSelecionado");
	}
	
	public void editarLivro(){
		
		LivroDao dao = new LivroDao();
		
		Categoria cat = new Categoria();
		CategoriaDao catDao = new CategoriaDao();
		cat = catDao.buscaPorId(idCategoria);

		Autor autor = new Autor();
		AutorDao autorDao = new AutorDao();
		autor = autorDao.buscaPorId(idAutor);
		
		StatusLivro status = new StatusLivro();
		StatusLivroDao statusDao = new StatusLivroDao();
		status = statusDao.buscaPorId(idStatus);
		
		this.livro.setAutor(autor);
		this.livro.setCategoria(cat);
		this.livro.setStatus(status);
		dao.alterar(livro);
		livros = listarLivro();
	}
}
