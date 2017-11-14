package br.com.fema.biblioteca.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.com.fema.biblioteca.model.Aluguel;
import br.com.fema.biblioteca.util.JPAUtil;

public class AluguelDao {

public void adicionar(Aluguel aluguel){
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		
		em.getTransaction().begin();
		em.persist(aluguel);
		em.getTransaction().commit();
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao salvar Aluguel.");
			
		}finally {
		em.close();
		}
		
	}
	
	public void remover(Aluguel aluguel){
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
		em.getTransaction().begin();
		aluguel = em.find(Aluguel.class, aluguel.getId());
		em.remove(aluguel);
		em.getTransaction().commit();
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao remover Aluguel.");
			
		}finally {
		em.close();
		}
	}
	
	public void alterar(Aluguel aluguel){
		
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
		
		em.getTransaction().begin();
		em.find(Aluguel.class, aluguel.getId());
		em.merge(aluguel);
		em.getTransaction().commit();
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao Atualizar Aluguel.");
			
		}finally {
		em.close();
		}
	}
	
	public List<Aluguel> listarTodos(){
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		CriteriaQuery<Aluguel> query = em.getCriteriaBuilder().createQuery(Aluguel.class);
		query.select(query.from(Aluguel.class));

		List<Aluguel> lista = em.createQuery(query).getResultList();
		
		return lista;
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao listar Aluguel.");
			
		}finally {
		em.close();
		}
		
	}
	
	public Aluguel buscaPorId(Integer id) {
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		Aluguel aluguel =  em.find(Aluguel.class, id);
		return aluguel;
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao buscar o Id do Aluguel.");
			
		}finally {
		em.close();
		}
		
	}
}
