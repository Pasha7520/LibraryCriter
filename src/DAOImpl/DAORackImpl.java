package DAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.mysql.jdbc.PreparedStatement;

import entity.Author;
import entity.Book;
import entity.Rack;
import util.DataBaseUtil2;
import util.HibernateUtil;
import DAO.BaseDAO;
import DAO.RackDAO;

public class DAORackImpl implements BaseDAO<Rack>,RackDAO {

	@Override
	public Rack getById(int Id) throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Rack> list = null;
		
		try{
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Rack.class);
			criteria.add(Restrictions.eq("id", Id));
			list = criteria.list();
			session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		return list.get(0);
		
		
	}

	@Override
	public boolean add(Rack t) throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			
			session.beginTransaction();
			session.save(t);
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
	public boolean delete(Rack t) throws SQLException {
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
	public List<Rack> getAll() throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Rack> list = null;
	
		try{
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Rack.class);
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


	@Override
	public int bookBelongRack(int rackId) throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Book> list = null;
	
		try{
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Book.class,"b");
		criteria.createCriteria("b.rack","r");
		criteria.add(Restrictions.eq("r.id", rackId));
		list = criteria.list();
		
		
		session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally{
			session.close();
			
		}
		return list.size();
		
	}

	

}
