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

import util.DataBaseUtil2;


import util.HibernateUtil;
import entity.Author;
import entity.Department;
import entity.Order;
import entity.Rack;
import DAO.BaseDAO;
import DAO.DepartmentDAO;

public class DAODepartmentImpl implements BaseDAO<Department>,DepartmentDAO {

	@Override
	public Department getById(int Id) throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Department> list = null;
		
		try{
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Department.class,"d");
			criteria.add(Restrictions.eq("d.id", Id));
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
	public boolean add(Department t) throws SQLException {
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
	public boolean delete(Department t) throws SQLException {
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
	public List<Department> getAll() throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Department> list = null;
	
		try{
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Department.class);
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
