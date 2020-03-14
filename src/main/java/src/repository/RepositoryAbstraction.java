package src.repository;

public interface RepositoryAbstraction {

    //TODO check what object are creating in database, unpack it and send it to db
    //TODO maybe put observer here so the table can be refreshed
    /**
     *
     * @param object
     * @return returns objectId
     */
    public String createRecord(Object object);

    //TODO  maybe put observer here so the table can be refreshed after reading records
    /**
     *
     * @param object
     * @return returns object for specific table
     */
    public Object readRecords(Object object);

    //TODO maybe update record based on record id(could be string between brackets instead of object)
    //TODO  maybe put observer here so the table can be refreshed after reading records
    /**
     *
     * @param object which needs to be updated
     */
    public void updateRecord(Object object);

    //TODO maybe update record based on record id(could be string between brackets instead of object)
    //TODO  maybe put observer here so the table can be refreshed after reading records
    /**
     *
     * @param object which needs to be deleted
     */
    public void deleteRecord(Object object);
}
