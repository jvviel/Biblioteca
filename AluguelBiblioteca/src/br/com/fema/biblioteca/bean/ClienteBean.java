package br.com.fema.biblioteca.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.com.fema.biblioteca.dao.ClienteDao;
import br.com.fema.biblioteca.model.Cep;
import br.com.fema.biblioteca.model.Cliente;

@ManagedBean
@ViewScoped
public class ClienteBean {

	private Cliente cliente;

	public Cliente getCliente() {
		return cliente;
	}
	
	public void novo(){
		
		cliente = new Cliente();
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	@PostConstruct
	public List<Cliente> listarClientes(){
		
		ClienteDao dao = new ClienteDao();
		return dao.listarTodos();
	}
	
	public void salvarCliente(){
		
		ClienteDao dao = new ClienteDao();
		dao.adicionar(cliente);
		
		String msg = "Cliente " + cliente.getNome() + " salvo com sucesso.";
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		
		cliente = new Cliente();
		listarClientes();
		
	}
	
	public void excluirCliente(Cliente cliente){
		
		ClienteDao dao = new ClienteDao();
		dao.remover(cliente);
		String msg = "Cliente " + cliente.getNome() + " removido com sucesso.";
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		
		listarClientes();
	}


	public void selecionaCliente(ActionEvent evento){
		
		cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");
	}
	
	public void editarCliente(){
		
		ClienteDao dao = new ClienteDao();
		dao.alterar(cliente);
		
        String msg = "Cliente " + cliente.getNome() + " alterado com sucesso.";
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		
		listarClientes();
	}
	
	public void buscaCep() {
		String cep = cliente.getCep().replaceAll("-", "");
		Client c = Client.create();
		WebResource wr = c.resource("https://viacep.com.br/ws/" + cep + "/json/");
		Cep json = wr.get(Cep.class);
		try {
			if (json.getLogradouro().isEmpty())
				;
			else {
				this.cliente.setBairro(json.getBairro());
				this.cliente.setEndereco(json.getLogradouro());
				this.cliente.setCidade(json.getLocalidade());
				this.cliente.setEstado(json.getUf());
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("Cep",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cep inválido", "Cep inválido"));

		}

	}
}
