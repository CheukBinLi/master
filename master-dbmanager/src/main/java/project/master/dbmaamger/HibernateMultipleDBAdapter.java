package project.master.dbmaamger;

import java.util.Map;

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
 * @see 注意：必须注入
 *      <p>
 *      Map<String, SessionFactory> sessionFactorys;
 *      <p>
 *      QueryFactory queryFactory;
 *
 */
public class HibernateMultipleDBAdapter extends AbstractHibernateDBAdapter {

	private Map<String, SessionFactory> sessionFactorys;

	private volatile String dataSource;

	public HibernateMultipleDBAdapter setSessionFactory(String name) {
		this.dataSource = name;
		return this;
	}

	@Override
	public Session getSession() {
		SessionFactory sf = sessionFactorys.get(this.dataSource);
		if (null == sf)
			throw new NullPointerException("sessionFactory is null!");
		return sf.openSession();
	}

	public Map<String, SessionFactory> getSessionFactorys() {
		return sessionFactorys;
	}

	public HibernateMultipleDBAdapter setSessionFactorys(Map<String, SessionFactory> sessionFactorys) {
		this.sessionFactorys = sessionFactorys;
		return this;
	}

}
