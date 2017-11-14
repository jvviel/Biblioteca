package br.com.fema.biblioteca.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.com.fema.biblioteca.model.Categoria;
import br.com.fema.biblioteca.util.JPAUtil;

public class CategoriaDao {

public void adicionar(Categoria categoria){
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		
		em.getTransaction().begin();
		em.persist(categoria);
		em.getTransaction().commit();
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao salvar Categoria.");
			
		}finally {
		em.close();
		}
		
	}
	
	public void remover(Categoria categoria){
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
		em.getTransaction().begin();
		categoria = em.find(Categoria.class, categoria.getId());
		em.remove(categoria);
		em.getTransaction().commit();
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao remover Categoria.");
			
		}finally {
		em.close();
		}
	}
	
	public void alterar(Categoria categoria){
		
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
		
		em.getTransaction().begin();
		em.find(Categoria.class, categoria.getId());
		em.merge(categoria);
		em.getTransaction().commit();
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao Atualizar Categoria.");
			
		}finally {
		em.close();
		}
	}
	
	public List<Categoria> listarTodos(){
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		CriteriaQuery<Categoria> query = em.getCriteriaBuilder().createQuery(Categoria.class);
		query.select(query.from(Categoria.class));

		List<Categoria> lista = em.createQuery(query).getResultList();
		
		return lista;
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao listar Categoriaes.");
			
		}finally {
		em.close();
		}
		
	}
	
	public Categoria buscaPorId(Integer id) {
		EntityManager em = new JPAUtil().getEntityManager();
		
		try{
			
		Categoria categoria =  em.find(Categoria.class, id);
		return categoria;
		
		}catch(RuntimeException e){
			throw new RuntimeException("Erro ao buscar o Id do Categoria.");
			
		}finally {
		em.close();
		}
		
	}
}
