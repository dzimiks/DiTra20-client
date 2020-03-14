package src.repository;

public interface RepositoryImplementor {

    /**
     *
     * @param object
     * @return returns objectId
     */
    public long createRecord(Object object);

    /**
     *
     * @param object
     * @return returns object for specific table
     */
    public Object readRecords(Object object);

    /**
     *
     * @param object which needs to be updated
     */
    public void updateRecord(Object object);

    /**
     *
     * @param object which needs to be deleted
     */
    public void deleteRecord(Object object);

}
