package src.repository;

public class RepositoryImplementation implements RepositoryAbstraction {

    private RepositoryImplementor implementor = null;

    public RepositoryImplementation(RepositoryImplementor implementor) {
        this.implementor = implementor;
    }

    @Override
    public String createRecord(Object object) {
        return null;
    }

    @Override
    public Object readRecords(Object object) {
        return null;
    }

    @Override
    public void updateRecord(Object object) {

    }

    @Override
    public void deleteRecord(Object object) {

    }
}
