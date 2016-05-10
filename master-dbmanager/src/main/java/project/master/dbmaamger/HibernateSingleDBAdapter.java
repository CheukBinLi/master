package project.master.dbmaamger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/***
 * *
 * 
 * Copyright 2016 CHEUK.BIN.LI Individual All
 * 
 * ALL RIGHT RESERVED
 * 
 * CREATE ON 2016年4月28日
 * 
 * EMAIL:20796698@QQ.COM
 * 
 * 
 * @author CHEUK.BIN.LI
 * 
 * @see 注意：必须注入:
 *      <p>
 *      SessionFactory sessionFactory
 *      <p>
 *      QueryFactory queryFactory;
 *
 */
public class HibernateSingleDBAdapter extends AbstractHibernateDBAdapter {

	private SessionFactory sessionFactory;
	// private QueryFactory queryFactory;

	public HibernateSingleDBAdapter setSessionFactory(String name) {
		return this;
	}

	@Override
	public Session getSession() {
		// return sessionFactory.openSession();
		return sessionFactory.getCurrentSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public HibernateSingleDBAdapter setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		return this;
	}

}
