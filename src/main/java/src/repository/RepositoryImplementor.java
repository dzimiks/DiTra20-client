package src.repository;

import java.sql.SQLException;
import java.util.List;

public interface RepositoryImplementor {

	/**
	 * @param object
	 * @return returns objectId
	 */
	public long createRecord(Object object);

	/**
	 * @return returns objects for specific table
	 */
	public List<Object> readRecords() throws SQLException;

	/**
	 * @param object which needs to be updated
	 */
	public void updateRecord(Object object);

	/**
	 * @param object which needs to be deleted
	 */
	public void deleteRecord(Object object);

}
