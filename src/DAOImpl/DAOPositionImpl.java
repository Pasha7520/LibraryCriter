package DAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.mysql.jdbc.PreparedStatement;

import entity.Author;
import entity.Customer;
import entity.Position;
import entity.Rack;
import util.DataBaseUtil2;
import util.HibernateUtil;
import DAO.BaseDAO;
import DAO.PositionDAO;

public class DAOPositionImpl implements BaseDAO<Position>,PositionDAO{

	@Override
	public int getNubmerOfPosition(String namePosition) throws SQLException {

		for(Position p : getAll()){
			if(p.getName().equals(namePosition)){
				return p.getId();
			}
		}
		
		return 0;
	}

	@Override
	public Position getById(int Id) throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Position> list = null;
		
		try{
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Position.class);
			criteria.add(Restrictions.eq("id", Id));
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
	public boolean add(Position t) throws SQLException {
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
	public boolean delete(Position t) throws SQLException {
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
	public List<Position> getAll() throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Position> list = null;
	
		try{
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Position.class);
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
