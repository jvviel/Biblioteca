package br.com.fema.biblioteca.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.com.fema.biblioteca.model.StatusLivro;
import br.com.fema.biblioteca.util.JPAUtil;

public class StatusLivroDao {

public void adicionar(StatusLivro status){
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		
		em.getTransaction().begin();
		em.persist(status);
		em.getTransaction().commit();
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao salvar StatusLivro.");
			
		}finally {
		em.close();
		}
		
	}
	
	public void remover(StatusLivro status){
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
		em.getTransaction().begin();
		status = em.find(StatusLivro.class, status.getId());
		em.remove(status);
		em.getTransaction().commit();
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao remover StatusLivro.");
			
		}finally {
		em.close();
		}
	}
	
	public void alterar(StatusLivro status){
		
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
		
		em.getTransaction().begin();
		em.find(StatusLivro.class, status.getId());
		em.merge(status);
		em.getTransaction().commit();
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao Atualizar StatusLivro.");
			
		}finally {
		em.close();
		}
	}
	
	public List<StatusLivro> listarTodos(){
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		CriteriaQuery<StatusLivro> query = em.getCriteriaBuilder().createQuery(StatusLivro.class);
		query.select(query.from(StatusLivro.class));

		List<StatusLivro> lista = em.createQuery(query).getResultList();
		
		return lista;
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao listar StatusLivroes.");
			
		}finally {
		em.close();
		}
		
	}
	
	public StatusLivro buscaPorId(Integer id) {
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		StatusLivro status =  em.find(StatusLivro.class, id);
		return status;
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao buscar o Id do StatusLivro.");
			
		}finally {
		em.close();
		}
		
	}
}
