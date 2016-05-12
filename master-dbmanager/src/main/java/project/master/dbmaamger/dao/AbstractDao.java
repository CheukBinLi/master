package project.master.dbmaamger.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import project.master.dbmaamger.DBAdapter;

public abstract class AbstractDao<entity, ID extends Serializable> implements BaseDao<entity, ID> {

	public abstract Class<entity> getEntityClass();

	public abstract DBAdapter getDBAdapter();

	public entity get(ID id) throws Throwable {
		return getDBAdapter().get(getEntityClass(), id);
	}

	public List<entity> getList(int page, int size) throws Throwable {
		return getDBAdapter().getList(getEntityClass(), page, size);
	}

	public List<entity> getList(Map<String, Object> params, int page, int size) throws Throwable {
		return getDBAdapter().getListByXqlQueryName(getDBAdapter().queryNameFormat(getEntityClass(), "list"), true, true, params, page, size);
	}

	public List<Object[]> getList(String queryName, Map<String, Object> params, boolean isFromat, int page, int size) throws Throwable {
		return getDBAdapter().getListByXqlQueryName(getDBAdapter().queryNameFormat(getEntityClass(), queryName), true, isFromat, params, page, size);
		// return getDBAdapter().getListByXqlQueryName(getDBAdapter().queryNameFormat(getEntityClass(), queryName), true, isFromat, params, page, size);
	}

	public List<Object[]> getListCustomQueryName(String queryName, Map<String, Object> params, boolean isFromat, int page, int size) throws Throwable {
		// return getDBAdapter().getListByXqlQueryName(queryName.toLowerCase(), true, isFromat, params, page, size);
		return getDBAdapter().getListByXqlQueryName(queryName.toLowerCase(), true, isFromat, params, page, size);
	}

	public List<entity> getListEntity(String queryName, Map<String, Object> params, boolean isFromat, int page, int size) throws Throwable {
		return getDBAdapter().getListByXqlQueryName(getDBAdapter().queryNameFormat(getEntityClass(), queryName), true, isFromat, params, page, size);
		// return getDBAdapter().getListByXqlQueryName(getDBAdapter().queryNameFormat(getEntityClass(), queryName), true, isFromat, params, page, size);
	}

	public List<entity> getListEntityCustomQueryName(String queryName, Map<String, Object> params, boolean isFromat, int page, int size) throws Throwable {
		// return getDBAdapter().getListByXqlQueryName(queryName.toLowerCase(), true, isFromat, params, page, size);
		return getDBAdapter().getListByXqlQueryName(queryName.toLowerCase(), true, isFromat, params, page, size);
	}

	public entity save(entity o) throws Throwable {
		return getDBAdapter().save(o);
	}

	public void saveOrUpeate(entity o) throws Throwable {
		getDBAdapter().saveOrUpdate(o);
	}

	public void update(entity o) throws Throwable {
		getDBAdapter().update(o);
	}

	public void delete(entity o) throws Throwable {
		getDBAdapter().delete(o);
	}

	public Object uniqueResult(String queryName, boolean isFormat, Map<String, Object> params) throws Throwable {
		return getDBAdapter().uniqueResult(getDBAdapter().queryNameFormat(getEntityClass(), queryName), true, isFormat, params);
	}

	public Object uniqueResultCustomQueryName(String queryName, boolean isFormat, Map<String, Object> params) throws Throwable {
		return getDBAdapter().uniqueResult(queryName.toLowerCase(), true, isFormat, params);
	}

	public int executeUpdate(String queryName, Map<String, Object> params, boolean isHql, boolean isFromat) throws Throwable {
		return getDBAdapter().executeUpdate(getDBAdapter().queryNameFormat(getEntityClass(), queryName), params, isHql, isFromat);
	}

	public int executeUpdateCustomQueryName(String queryName, Map<String, Object> params, boolean isHql, boolean isFromat) throws Throwable {
		return getDBAdapter().executeUpdate(queryName.toLowerCase(), params, isHql, isFromat);
	}

}
