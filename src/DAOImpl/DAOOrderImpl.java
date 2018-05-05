package DAOImpl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.mysql.jdbc.PreparedStatement;

import util.DataBaseUtil2;
import util.HibernateUtil;
import entity.Author;
import entity.Order;
import entity.Person;
import entity.Rack;
import DAO.BaseDAO;
import DAO.OrderDAO;

public class DAOOrderImpl implements BaseDAO<Order>,OrderDAO {

	@Override
	public Order getById(int id) throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Order> list = null;
		
		try{
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class,"o");
			criteria.add(Restrictions.eq("o.id", id));
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
	public boolean add(Order t) throws SQLException {
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
	public boolean delete(Order t) throws SQLException {
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
	public List<Order> getAll() throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Order> list = null;
	
		try{
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Order.class,"o");
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
	public void writeActualDateInOrder(int orderId,Date date) throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
	
		try{
		
		session.beginTransaction();
		Order o = getById(orderId);
		o.setActualDate(date);
		session.update(o);
		
		session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		
	}

	
	
}
