package src.repository;

import src.config.SQLConfig;
import src.models.Attribute;
import src.models.Entity;
import src.models.tree.Node;
import src.view.table.TabbedView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * MYSQL database implementation
 */
public class DatabaseImplementation implements RepositoryImplementor {

	@Override
	public long createRecord(Object object) {
		return 0;
	}

	@Override
	public List<Object> readRecords() throws SQLException {
		System.out.println("Fetching DB data...");

		Entity entity = TabbedView.activePanel.getEntity();
		String query = "SELECT * FROM " + entity.getName();
		List<Object> rows = new ArrayList<>();

		System.out.println("\n==========");
		System.out.println(query);
		System.out.println("==========\n");

		PreparedStatement statement = SQLConfig.getInstance().getDbConnection().prepareStatement(query);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.getMetaData().getColumnCount() != entity.getChildCount()) {
			System.err.println("Database and MS out of sync.");
			return null;
		}

		while (resultSet.next()) {
			for (Node node : entity.getChildren()) {
				if (node instanceof Attribute) {
					Object value = resultSet.getObject(node.getName());
					rows.add(value);
				}
			}
		}

		System.out.println("DATA: " + rows);
		resultSet.close();
		statement.close();
		return rows;
	}

	@Override
	public void updateRecord(Object object) {

	}

	@Override
	public void deleteRecord(Object object) {

	}
}
