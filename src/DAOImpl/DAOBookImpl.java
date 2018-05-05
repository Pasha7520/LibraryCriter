package DAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mysql.jdbc.PreparedStatement;

import entity.Author;
import entity.Book;
import entity.Customer;
import entity.Order;
import entity.Person;
import service.OrderService;
import serviceImpl.AuthorServiceImpl;
import serviceImpl.OrderServiceImpl;
import serviceImpl.RackServiceImpl;
import util.DataBaseUtil2;
import util.HibernateUtil;
import util.MathUtil;
import DAO.BaseDAO;
import DAO.BookDAO;

public class DAOBookImpl implements BaseDAO<Book>,BookDAO {

	@Override
	public Book getById(int Id) throws SQLException {
	Session session = HibernateUtil.getSessionFactory().openSession();
	List<Book> list = null;
	
	try{
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Book.class,"o");
		criteria.add(Restrictions.eq("o.id", Id));
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
	public boolean add(Book b) throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			
			AuthorServiceImpl authorServiceImpl = new AuthorServiceImpl();
			authorServiceImpl.checkingOrWritingNewAuthor(b.getListAuthor());
			AuthorServiceImpl authorService = new AuthorServiceImpl();
			b.setListAuthor(authorServiceImpl.findAuthorsId(b.getListAuthor()));
			session.beginTransaction();
			b.setId(1);
			RackServiceImpl rackService = new RackServiceImpl();
			b.getRack().setId(rackService.findfreeRac());
			b.setAvaileble(true);
			
			session.save(b);
			
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
	public boolean delete(Book t) throws SQLException {
		
			Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
			DAOBookImpl b = new DAOBookImpl();
			Book bb = b.getById(t.getId());
			
			session.beginTransaction();
			session.delete(bb);

			
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
	public List<Book> getAll() throws SQLException {	
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Book> list = null;
	
		try{
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Book.class);
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


	public int getMaxBookId(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Integer> list = null;
		try{
			
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Book.class,"p");
			criteria.setProjection(Projections.max("p.id"));
			list = criteria.list();
			
			
			session.getTransaction().commit();
			}catch(Exception e){
				session.getTransaction().rollback();
				e.printStackTrace();
			}
			finally{
				session.close();
			}
		return (int)list.get(0);
	}
	
	@Override
	public void changeAvailebleBook(int id,boolean b) throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			
			session.beginTransaction();
			DAOBookImpl dao =new  DAOBookImpl();
			Book book = dao.getById(id);
			book.setAvaileble(b);
			session.update(book);
			
			
			session.getTransaction().commit();
			}catch(Exception e){
				session.getTransaction().rollback();
				e.printStackTrace();
			}
			finally{
				session.close();
				
			}
			
	}

	@Override
	public int bookBelongDepartment(int bookId) throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Book> list = null;
		
		try{
			
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Book.class,"o");
			criteria.add(Restrictions.eq("o.id", bookId));
			list = criteria.list();
			
			session.getTransaction().commit();
			}catch(Exception e){
				session.getTransaction().rollback();
				e.printStackTrace();
			}
			finally{
				session.close();
			}
		
			return (int)list.get(0).getRack().getDepartment().getId();
		
	
	}


	@Override
	public List<Book> getByName(String name)throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Book> list = null;
		
		try{
			
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Book.class,"o");
			criteria.add(Restrictions.eq("o.name", name));
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
