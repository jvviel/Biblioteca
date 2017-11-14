package br.com.fema.biblioteca.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.com.fema.biblioteca.model.Livro;
import br.com.fema.biblioteca.util.JPAUtil;

public class LivroDao {

public void adicionar(Livro livro){
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		
		em.getTransaction().begin();
		em.persist(livro);
		em.getTransaction().commit();
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao salvar Livro.");
			
		}finally {
		em.close();
		}
		
	}
	
	public void remover(Livro livro){
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
		em.getTransaction().begin();
		livro = em.find(Livro.class, livro.getId());
		em.remove(livro);
		em.getTransaction().commit();
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao remover Livro.");
			
		}finally {
		em.close();
		}
	}
	
	public void alterar(Livro Livro){
		
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
		
		em.getTransaction().begin();
		em.find(Livro.class, Livro.getId());
		em.merge(Livro);
		em.getTransaction().commit();
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao Atualizar Livro.");
			
		}finally {
		em.close();
		}
	}
	
	public List<Livro> listarTodos(){
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		CriteriaQuery<Livro> query = em.getCriteriaBuilder().createQuery(Livro.class);
		query.select(query.from(Livro.class));

		List<Livro> lista = em.createQuery(query).getResultList();
		
		return lista;
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao listar Livroes.");
			
		}finally {
		em.close();
		}
		
	}
	
	public Livro buscaPorId(Integer id) {
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		Livro Livro =  em.find(Livro.class, id);
		return Livro;
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao buscar o Id do Livro.");
			
		}finally {
		em.close();
		}
		
	}
}
