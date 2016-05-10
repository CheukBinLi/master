package project.master.test.dbmanager.dao_service_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.master.dbmaamger.DBAdapter;
import project.master.dbmaamger.dao.AbstractDao;

@Component
public class UserDaoImpl extends AbstractDao<User, Integer> implements UserDao {
	@Autowired
	private DBAdapter dBAdapter;

	@Override
	public Class<User> getEntityClass() {
		return null;
	}

	@Override
	public DBAdapter getDBAdapter() {
		return dBAdapter;
	}

}
