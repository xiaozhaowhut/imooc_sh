package service.impl;



import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;



import db.MyHibernateSessionFactory;

import entity.Users;
import service.UsersDAO;

public class UsersDAOImpl implements UsersDAO{
	public boolean usersLogin(Users u) {
		//创建事物对象
		Transaction tx=null;
		String hql="";
		try {
			Session session=MyHibernateSessionFactory.getSessionFactory().getCurrentSession();
			tx=session.beginTransaction();
			hql="from Users where username=? and password=?";
			Query query=session.createQuery(hql);
			query.setParameter(0, u.getUsername());
			query.setParameter(1, u.getPassword());
			List list=query.list();
			tx.commit();//提交事务一定要在return之前，捕获异常之后再返回的话就是false了
			//判断是否查到用户
			if(list.size()>0){
				return true;
			}else{
				return false;
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if(tx!=null){
				tx=null;
			}
		}
	}
}
