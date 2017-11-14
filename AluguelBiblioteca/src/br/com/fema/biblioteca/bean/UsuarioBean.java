package br.com.fema.biblioteca.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.fema.biblioteca.dao.UsuarioDao;
import br.com.fema.biblioteca.model.Usuario;
import br.com.fema.biblioteca.util.ValidaUsuario;

@ManagedBean
@ViewScoped
public class UsuarioBean {

	private Usuario usuario = new Usuario();

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void novo(){
		usuario = new Usuario();
	}
	
	@PostConstruct
	public List<Usuario> listarUsuarios(){
		
		UsuarioDao dao = new UsuarioDao();
		return dao.listarTodos();
	}
	
	public void salvarUsuario(){
		
		UsuarioDao dao = new UsuarioDao();
		dao.adicionar(usuario);
		
		String msg = "Usuario " + usuario.getNome() + " salvo com sucesso";
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		
		novo();
		listarUsuarios();
		
	}
	
	public void excluirUsuario(Usuario usuario){
	
		UsuarioDao dao = new UsuarioDao();
		dao.remover(usuario);
		
		String msg = "Usuario " + usuario.getNome() + " removido com sucesso";
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		
		listarUsuarios();
	
	}
	
	public void selecionaUsuario(ActionEvent evento){
		
		usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
	}
	
	public void editarUsuario(){
		
		UsuarioDao dao = new UsuarioDao();
		dao.alterar(usuario);
		
		String msg = "Usuario " + usuario.getNome() + " alterado com sucesso";
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		
		listarUsuarios();
	}
	
	public String logar(){
		
		boolean existe = new ValidaUsuario().existe(this.usuario);
		FacesContext context = FacesContext.getCurrentInstance();
		if(existe) {
			
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return "home?faces-redirect=true";
		
		}
		context.getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().addMessage("usuario", new FacesMessage("Login ou senha incorretos."));
		return "login?faces-redirect=true";
	}
	
	public String logout(){
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		return "login?faces-redirect=true";
	}
	
}
