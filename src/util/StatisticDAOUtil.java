package util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Subquery;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;




import org.hibernate.type.IntegerType;
import org.hibernate.type.Type;

import com.mysql.jdbc.PreparedStatement;

import serviceImpl.OrderServiceImpl;
import DAOImpl.DAOAuthorImpl;
import DAOImpl.DAOBookImpl;
import DAOImpl.DAOCustomerImpl;


import entity.Author;
import entity.Book;
import entity.Customer;
import entity.Order;

public class StatisticDAOUtil {
	public List<Book> getCustomerAllBook(Customer customer) throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Book> list = null;
	
		try{
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Book.class,"b");
		criteria.createCriteria("b.listOrder","o");
		criteria.createCriteria("o.customer","c");
		criteria.add(Restrictions.eq("c.id", customer.getId()));
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
	
	public Customer getByInitials(String name, String sername, String nickname) throws SQLException {
		Customer customer = new Customer();
		DAOCustomerImpl daoCustomer = new DAOCustomerImpl();
		for(Customer c :daoCustomer.getAll()){
			if(c.getName().equals(name) && c.getNickname().equals(nickname) && c.getSername().equals(sername)){
				customer = c;
			}
		}
		
		return customer;
	}
	public List<Book> getCustomerBooksOnHands(Customer customer) throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Book> list = null;
	
		try{
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Book.class,"b");
		criteria.createCriteria("b.listOrder","o");
		criteria.createCriteria("o.customer","c");
		criteria.add(Restrictions.eq("c.id", customer.getId()));
		criteria.add(Restrictions.isNull("o.actualDate"));
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

	public Date getStartTimeUseLibrary(Customer customer) throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Date> list = null;
	
		try{
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Order.class,"o");
		criteria.createCriteria("o.customer","c");
		criteria.add(Restrictions.eq("c.id", customer.getId()));
		criteria.setProjection(Projections.min("o.startDate"));
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

	public Period getDuringCustomerUseLibrary(Customer customer) throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Date> list = null;
	
		try{
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Order.class,"o");
		criteria.createCriteria("o.customer","c");
		criteria.add(Restrictions.eq("c.id", customer.getId()));
		criteria.setProjection(Projections.min("o.startDate"));
		list = criteria.list();
		

		
		session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally{
			session.close();
			
		}
		
		Date date = new Date();

		LocalDate l1 = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate l2 = list.get(0).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Period period = Period.between(l2, l1);

		
		
		return period;
	}

	public List<Book> getBookCustomerEverOrdered(Customer customer) throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Book> list = null;
	
		try{
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Book.class,"b");
		criteria.createCriteria("b.listOrder","o");
		criteria.createCriteria("o.customer","c");
		criteria.add(Restrictions.eq("c.id", customer.getId()));
		criteria.add(Restrictions.isNotNull("o.actualDate"));
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
	
	public int avarageTimeReading(Book book) throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Order> list1 = null;
		try{
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Order.class,"o");
		criteria.createCriteria("o.bookB","b");
		criteria.add(Restrictions.eq("b.id", book.getId()));
		criteria.add(Restrictions.isNotNull("o.actualDate"));
		list1 = criteria.list();
		
		
		
		session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally{
			session.close();
			
		}
		MathUtil mathUtil = new MathUtil();
		int res = 0;
		res = mathUtil.AVGTimeReadingBook(list1, list1.size());
		
		return res;
	}

	public int getBooksByIdHowMenyTimesTake(Book book)throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Long> list = null;
	
		try{
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Order.class,"o");
		criteria.createCriteria("o.bookB","b");
		criteria.add(Restrictions.eq("b.id", book.getId()));
		criteria.setProjection(Projections.count("b.id"));
		list = criteria.list();

		
		

		
		session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally{
			session.close();
			
		}
		
		
		
		return list.get(0).intValue();
	}
	
	public List<Book> getAllBookEverOrdered() throws SQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Book> list = null;
	
		try{
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Book.class,"b");
		criteria.createCriteria("b.listOrder","o");
		
		criteria.addOrder(org.hibernate.criterion.Order.asc("id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
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

	public void printMostPopularBook() throws SQLException{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Integer> list = null;
		List<Integer> list2 = null;
		List<Integer> list1 = null;
		int max =0;
		try{
		
		session.beginTransaction();
		
		DetachedCriteria subquery = DetachedCriteria.forClass(Book.class,"b");
				subquery.createCriteria("b.listOrder");
				subquery.setProjection(Projections.sqlGroupProjection("count({alias}.id) as rowCount",
				"this_.id",new String[]{"rowCount"},new Type[] { IntegerType.INSTANCE}));
				list2 = subquery.getExecutableCriteria(session).list();

				for(Integer i :list2){
					if(i >max){
						max=i;
					}
				}
		
				
		DetachedCriteria criteria = DetachedCriteria.forClass(Book.class, "b");
			criteria.createCriteria("b.listOrder");
			criteria.setProjection(Projections.sqlGroupProjection("{alias}.id as id",
									"this_.id having count({alias}.id) >="+max,new String[]{"id"},new Type[] { IntegerType.INSTANCE}));



			list = criteria.getExecutableCriteria(session).list();

		
		
		session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally{
			session.close();
			
		}
		DAOBookImpl daoBookImpl = new DAOBookImpl();
		for(Integer i:list){
			System.out.println(daoBookImpl.getById(i));

		}

		System.out.println("The book is taken is "+ max+" times!!");
	}
	
	public boolean printMostUnpopularBookNeverTaken() throws SQLException{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Book> list = null;
	
		try{
		
		session.beginTransaction();
		DetachedCriteria subquery = DetachedCriteria.forClass(Book.class,"b");
		subquery.createCriteria("b.listOrder");
		subquery.setProjection(Projections.distinct(Projections.property("b.id")));
		subquery.addOrder(org.hibernate.criterion.Order.asc("b.id"));
		
		Criteria criteria = session.createCriteria(Book.class,"b");
		criteria.add(Subqueries.propertyNotIn("id", subquery));
		criteria.addOrder(org.hibernate.criterion.Order.asc("b.id"));
		
		list =criteria.list();
		
		
		session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally{
			session.close();
			
		}
		
		if(list.isEmpty()){
			return false;
		}
		else{
		System.out.print("This book never taken!!! - ");
		for(Book b:list){
			System.out.println(b);
		}
		
		return true;
		}
	}
	
	public boolean printMostUnpopularBook() throws SQLException{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Book> list = null;
		List<Integer> list1 = null;
		List<Integer> list2 = null;
		int min =1000000;
		try{
		
		session.beginTransaction();
		
		DetachedCriteria subquery = DetachedCriteria.forClass(Book.class,"b");
		subquery.createCriteria("b.listOrder");
		subquery.setProjection(Projections.sqlGroupProjection("count({alias}.id) as rowCount",
		"this_.id",new String[]{"rowCount"},new Type[] { IntegerType.INSTANCE}));
		list1 = subquery.getExecutableCriteria(session).list();

		for(Integer i :list1){
			if(i < min){
				min=i;
			}
		}

		DetachedCriteria criteria = DetachedCriteria.forClass(Book.class, "b");
		criteria.createCriteria("b.listOrder");
		criteria.setProjection(Projections.sqlGroupProjection("{alias}.id as id",
							"this_.id having count({alias}.id) <="+min,new String[]{"id"},new Type[] { IntegerType.INSTANCE}));
		list2 = criteria.getExecutableCriteria(session).list();


		Criteria criteria1 = session.createCriteria(Book.class,"b");
		criteria1.add(Restrictions.in("b.id", list2));
		criteria1.addOrder(org.hibernate.criterion.Order.asc("b.id"));
		list =criteria1.list();

		
		session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally{
			session.close();
			
		}
		System.out.println(list.isEmpty());
		boolean bool = false;
		int count = 0;
		DAOBookImpl daoBookImpl = new DAOBookImpl();
		for(Book i:list){
			System.out.println(i);
			bool = true;
			count++;
		}
		
		if(count>1)System.out.println("The books have been taken "+min+" times!!");
		else if(count==1) System.out.println("The book has been taken "+min+" time!!");
		return bool;
		
	}
	
	public void printDebtor() throws SQLException{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Order> list = null;
	
		try{
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Order.class,"o");
		criteria.createCriteria("o.customer","c");
		criteria.add(Restrictions.isNull("o.actualDate"));
		criteria.addOrder(org.hibernate.criterion.Order.asc("c.id"));
		list =criteria.list();

		
		session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally{
			session.close();
			
		}
		Order order  = new Order();
		order.getCustomer().setId(0);
		for(Order c:list){
			if(order.getCustomer().getId() == c.getCustomer().getId()){
				System.out.println("Borow-------"+c.getBookB());
			}
			else{
				System.out.println(c.getCustomer());
				System.out.println("Borow-------"+c.getBookB());
			}
			order = c;
		}
		
		
	}
	
	public void printAVGCustomersInfo() throws SQLException{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Double> list = null;
		List<BigDecimal> list1 = null;
	
		try{
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Order.class,"o");
		criteria.createCriteria("o.customer","c");
		criteria.setProjection(Projections.avg("c.age"));
		
		list =criteria.list();
		
		
		
		SQLQuery query1 = session.createSQLQuery("SELECT AVG(DATEDIFF(o.actual_date,o.start_date)) AS AVG_DATE FROM "
				+ "orders o JOIN customers ON customers.id = o.customer_id WHERE o.actual_date IS NOT NULL;");

		list1 = query1.list();
		session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally{
			session.close();
			
		}
		System.out.println("Middle age customers is :"
				+list.get(0).intValue()+", and average time of "
						+ "reading is :"+list1.get(0).intValue()+" days");
		
		
	}
	
	public void printAVGCustomersByBook(int bookId) throws SQLException{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Double> list = null;

		try{
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Order.class,"o");
		criteria.createCriteria("o.customer","c");
		criteria.createCriteria("o.bookB","b");
		criteria.setProjection(Projections.avg("c.age"));
		criteria.add(Restrictions.eq("b.id", bookId));
		list =criteria.list();
	
		session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally{
			session.close();
			
		}
		DAOBookImpl daoBook = new DAOBookImpl();
		System.out.println("By the book :"+ daoBook.getById(bookId));
		System.out.println("Middle age customers is :" + list.get(0).intValue()+"!!");
		
	}

	public void printAVGCustomersByAuthor(int authorId) throws SQLException{
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Double> list = null;

		try{
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Order.class,"o");
		criteria.createCriteria("o.customer","c");
		criteria.createCriteria("o.bookB","b");
		criteria.createCriteria("b.listAuthor","a");
		criteria.setProjection(Projections.avg("c.age"));
		criteria.add(Restrictions.eq("a.id", authorId));
		list =criteria.list();
		
	
		session.getTransaction().commit();
		}catch(Exception e){
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally{
			session.close();
			
		}
		DAOAuthorImpl daoAuthor = new DAOAuthorImpl();
		System.out.println("By the author :"+ daoAuthor.getById(authorId));
		System.out.println("Middle age customers is :" + list.get(0).intValue()+"!!");
		
	}
	
	
}
