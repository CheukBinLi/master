package project.master.test.dbmanager.dao_service_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.master.dbmaamger.dao.BaseDao;
import project.master.dbmaamger.service.AbstractService;

@Component
public class UserServiceImpl extends AbstractService<User, Integer> implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public BaseDao<User, Integer> getService() {
		return userDao;
	}

}
