package br.com.fema.biblioteca.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.com.fema.biblioteca.model.Usuario;
import br.com.fema.biblioteca.util.JPAUtil;

public class UsuarioDao {

public void adicionar(Usuario usuario){
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao salvar Usuario.");
			
		}finally {
		em.close();
		}
		
	}
	
	public void remover(Usuario usuario){
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
		em.getTransaction().begin();
		usuario = em.find(Usuario.class, usuario.getId());
		em.remove(usuario);
		em.getTransaction().commit();
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao remover Usuario.");
			
		}finally {
		em.close();
		}
	}
	
	public void alterar(Usuario usuario){
		
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
		
		em.getTransaction().begin();
		em.find(Usuario.class, usuario.getId());
		em.merge(usuario);
		em.getTransaction().commit();
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao Atualizar Usuario.");
			
		}finally {
		em.close();
		}
	}
	
	public List<Usuario> listarTodos(){
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		CriteriaQuery<Usuario> query = em.getCriteriaBuilder().createQuery(Usuario.class);
		query.select(query.from(Usuario.class));

		List<Usuario> lista = em.createQuery(query).getResultList();
		
		return lista;
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao listar Usuarioes.");
			
		}finally {
		em.close();
		}
		
	}
	
	public Usuario buscaPorId(Integer id) {
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		Usuario usuario =  em.find(Usuario.class, id);
		return usuario;
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao buscar o Id do Usuario.");
			
		}finally {
		em.close();
		}
		
	}
}
