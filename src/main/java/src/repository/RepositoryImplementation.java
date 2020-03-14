package src.repository;

import java.util.List;

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
	public List<Object> readRecords() {
		return null;
	}

	@Override
	public void updateRecord(Object object) {

	}

	@Override
	public void deleteRecord(Object object) {

	}
}
