package src.repository;

import java.sql.SQLException;
import java.util.List;

public class RepositoryImplementation implements RepositoryAbstraction {

	private RepositoryImplementor implementor;

	public RepositoryImplementation(RepositoryImplementor implementor) {
		this.implementor = implementor;
	}

	@Override
	public String createRecord(Object object) {
		return String.valueOf(implementor.createRecord(object));
	}

	@Override
	public List<Object> readRecords() {
//		try {
//			return implementor.readRecords();
//		} catch (SQLException e) {
//			e.printStackTrace();
		return null;
//		}
	}

	@Override
	public void updateRecord(Object newRecord,Object oldRecord ) {
		implementor.updateRecord(newRecord,oldRecord);
	}

	@Override
	public void deleteRecord(Object object) {
		implementor.deleteRecord(object);
	}
}
