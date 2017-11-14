package br.com.fema.biblioteca.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.com.fema.biblioteca.model.Autor;
import br.com.fema.biblioteca.util.JPAUtil;

public class AutorDao {

	public void adicionar(Autor autor){
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		
		em.getTransaction().begin();
		em.persist(autor);
		em.getTransaction().commit();
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao salvar Autor.");
			
		}finally {
		em.close();
		}
		
	}
	
	public void remover(Autor autor){
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		em.getTransaction().begin();
		autor = em.find(Autor.class, autor.getId());
		em.remove(autor);
		em.getTransaction().commit();
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao remover Autor.");
			
		}finally {
		em.close();
		}
	}
	
	public void alterar(Autor autor){
		
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
		
		em.getTransaction().begin();
		em.find(Autor.class, autor.getId());
		em.merge(autor);
		em.getTransaction().commit();
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao Atualizar Autor.");
			
		}finally {
		em.close();
		}
	}
	
	public List<Autor> listarTodos(){
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		CriteriaQuery<Autor> query = em.getCriteriaBuilder().createQuery(Autor.class);
		query.select(query.from(Autor.class));

		List<Autor> lista = em.createQuery(query).getResultList();
		
		return lista;
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao listar Autores.");
			
		}finally {
		em.close();
		}
		
	}
	
	public Autor buscaPorId(Integer id) {
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		Autor autor =  em.find(Autor.class, id);
		return autor;
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao buscar o Id do Autor.");
			
		}finally {
		em.close();
		}
		
	}
	
	
}
