package DAOImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mysql.jdbc.PreparedStatement;

import serviceImpl.RackServiceImpl;
import util.DataBaseUtil2;
import util.HibernateUtil;
import DAO.AuthorDAO;
import DAO.BaseDAO;
import entity.Author;
import entity.Customer;
import entity.Rack;

public class DAOAuthorImpl implements BaseDAO<Author>,AuthorDAO {

	@Override
	public Author getById(int Id) throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Author> list = null;
		
		try{
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Author.class,"o");
			criteria.add(Restrictions.eq("o.id", Id));
			list = criteria.list();
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return list.get(0);
		
	}

	@Override
	public boolean add(Author a) throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			
			session.beginTransaction();
			session.save(a);
			
			session.getTransaction().commit();
			}catch(Exception e){
				session.getTransaction().rollback();
				e.printStackTrace();
			}
			finally{
				session.close();
			}
			return true;
	}

	@Override
	public boolean delete(Author t) throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			
			session.beginTransaction();
			session.delete(t);
			
			session.getTransaction().commit();
			}catch(Exception e){
				session.getTransaction().rollback();
				e.printStackTrace();
			}
			finally{
				session.close();
			}
			return true;
		
	}

	@Override
	public List<Author> getAll() throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Author> list = null;
	
		try{
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Author.class,"o");
		criteria.addOrder(Order.asc("id"));
		list = criteria.list();
		
		session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		return list;

	}

	

}
