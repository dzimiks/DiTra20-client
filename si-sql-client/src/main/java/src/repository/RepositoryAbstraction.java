package src.repository;

import java.util.List;

public interface RepositoryAbstraction {

	//TODO check what object are creating in database, unpack it and send it to db
	//TODO maybe put observer here so the table can be refreshed

	/**
	 * @param object
	 * @return returns objectId
	 */
	public String createRecord(Object object);

	//TODO  maybe put observer here so the table can be refreshed after reading records

	/**
	 * @return returns object for specific table
	 */
	public List<Object> readRecords();

	//TODO maybe update record based on record id(could be string between brackets instead of object)
	//TODO  maybe put observer here so the table can be refreshed after reading records

	/**
	 * @param oldRecord which needs to be updated
	 * @param newRecord record which replaces oldRecord
	 */
	public void updateRecord(Object newRecord,Object oldRecord);

	//TODO maybe update record based on record id(could be string between brackets instead of object)
	//TODO  maybe put observer here so the table can be refreshed after reading records

	/**
	 * @param object which needs to be deleted
	 */
	public void deleteRecord(Object object);
}
