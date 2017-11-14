package br.com.fema.biblioteca.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.com.fema.biblioteca.model.Cliente;
import br.com.fema.biblioteca.util.JPAUtil;

public class ClienteDao {

public void adicionar(Cliente cliente){
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		
		em.getTransaction().begin();
		em.persist(cliente);
		em.getTransaction().commit();
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao salvar Cliente.");
			
		}finally {
		em.close();
		}
		
	}
	
	public void remover(Cliente cliente){
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
		em.getTransaction().begin();
		cliente = em.find(Cliente.class, cliente.getId());
		em.remove(cliente);
		em.getTransaction().commit();
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao remover Cliente.");
			
		}finally {
		em.close();
		}
	}
	
	public void alterar(Cliente cliente){
		
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
		
		em.getTransaction().begin();
		em.find(Cliente.class, cliente.getId());
		em.merge(cliente);
		em.getTransaction().commit();
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao Atualizar Cliente.");
			
		}finally {
		em.close();
		}
	}
	
	public List<Cliente> listarTodos(){
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		CriteriaQuery<Cliente> query = em.getCriteriaBuilder().createQuery(Cliente.class);
		query.select(query.from(Cliente.class));

		List<Cliente> lista = em.createQuery(query).getResultList();
		
		return lista;
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao listar Clientees.");
			
		}finally {
		em.close();
		}
		
	}
	
	public Cliente buscaPorId(Integer id) {
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		Cliente cliente =  em.find(Cliente.class, id);
		return cliente;
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao buscar o Id do Cliente.");
			
		}finally {
		em.close();
		}
		
	}
}
