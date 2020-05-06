package src.repository;

import src.models.Record;

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
    public List<Record> readRecords() throws SQLException;

    /**
     * @param oldRecord which needs to be updated
     * @param newRecord record which replaces oldRecord
     */
    public void updateRecord(Object newRecord, Object oldRecord);

    /**
     * @param object which needs to be deleted
     */
    public void deleteRecord(Object object);

}
