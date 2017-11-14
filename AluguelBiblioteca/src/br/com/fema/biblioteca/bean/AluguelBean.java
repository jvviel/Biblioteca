package br.com.fema.biblioteca.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.fema.biblioteca.dao.AluguelDao;
import br.com.fema.biblioteca.dao.ClienteDao;
import br.com.fema.biblioteca.dao.LivroDao;
import br.com.fema.biblioteca.dao.StatusLivroDao;
import br.com.fema.biblioteca.model.Aluguel;
import br.com.fema.biblioteca.model.Cliente;
import br.com.fema.biblioteca.model.Livro;
import br.com.fema.biblioteca.model.StatusLivro;

@ManagedBean
@ViewScoped
public class AluguelBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Aluguel aluguel;
	private Integer idLivro;
	private Integer idCliente;

	public Aluguel getAluguel() {
		return aluguel;
	}
	
	public void novo(){
		
		aluguel = new Aluguel();
	}

	public void setAluguel(Aluguel aluguel) {
		this.aluguel = aluguel;
	}
	
	
	public Integer getIdLivro() {
		return idLivro;
	}

	public void setIdLivro(Integer idLivro) {
		this.idLivro = idLivro;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	@PostConstruct
	public List<Aluguel> listarAluguel(){
		
		AluguelDao dao = new AluguelDao();
		return dao.listarTodos();
	}
	
	public List<Livro> listarLivros(){
		LivroDao dao = new LivroDao();
		return dao.listarTodos();
	}
	
	public List<Cliente> listarClientes(){
		
		ClienteDao dao = new ClienteDao();
		return dao.listarTodos();
	}
	
	public void salvarAluguel(){
		
		//Faz a busca por id do status
		StatusLivro status = new StatusLivro();
		StatusLivroDao statusDao = new StatusLivroDao();
		status = statusDao.buscaPorId(2);
		AluguelDao dao = new AluguelDao();
		
		Cliente cliente = new Cliente();
		ClienteDao clienteDao = new ClienteDao();
		cliente = clienteDao.buscaPorId(idCliente);
		
		Livro livro = new Livro();
		LivroDao livroDao = new LivroDao();
		livro = livroDao.buscaPorId(idLivro);
		
		System.out.println(livro.getStatus());
		System.out.println(status.getStatus());
		
		aluguel.setCliente(cliente);
		aluguel.setLivros(livro);
		
		StatusLivro statusLivro = livro.getStatus();

		
		if(statusLivro.getStatus().equals(status.getStatus())){
			
			String msg = "Esse livro já esta alugado";
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, message);
			
		}else{
			
			livro.setStatus(status);
			livroDao.alterar(livro);
			dao.adicionar(aluguel);
			
			String msg = "Aluguel do livro " + livro.getLivro() + " efetuado com sucesso.";
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, message);
			
			novo();
			listarAluguel();
			
			
		
		}
		
	}
	
	public void excluirAluguel(Aluguel aluguel){
		
		StatusLivro status = new StatusLivro();
		StatusLivroDao statusDao = new StatusLivroDao();
		status = statusDao.buscaPorId(1);
		
		Livro livro = new Livro();
		LivroDao livroDao = new LivroDao();
		
		AluguelDao dao = new AluguelDao();
		livro = aluguel.getLivros();
		
		livro.setStatus(status);
		livroDao.alterar(livro);
		dao.remover(aluguel);
		
		
		String msg = "Aluguel do livro " + livro.getLivro() + " finalizado com sucesso.";
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		
		
	}


	public void selecionaAluguel(ActionEvent evento){
		
		aluguel = (Aluguel) evento.getComponent().getAttributes().get("aluguelSelecionado");
	}
	
	public void editarAluguel(){
		
		AluguelDao dao = new AluguelDao();
		
		Cliente cliente = new Cliente();
		ClienteDao clienteDao = new ClienteDao();
		cliente = clienteDao.buscaPorId(idCliente);
		
		Livro livro = new Livro();
		LivroDao livroDao = new LivroDao();
		livro = livroDao.buscaPorId(idLivro);
		
		aluguel.setCliente(cliente);
		aluguel.setLivros(livro);
		
		dao.alterar(aluguel);
		
        String msg = "Aluguel do livro " + livro.getLivro() + " renovado com sucesso.";
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		
		listarAluguel();
	}
}
