package src.repository;

import net.sourceforge.jtds.jdbc.ClobImpl;
import src.config.SQLConfig;
import src.models.Attribute;
import src.models.Entity;
import src.models.Record;
import src.models.tree.Node;
import src.view.table.TabbedView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
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
	public List<Record> readRecords() throws SQLException {
		System.out.println("Fetching DB data...");

		Entity entity = TabbedView.activePanel.getEntity();
		String query = "SELECT * FROM " + entity.getName();
		List<Record> records = new ArrayList<>();

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
			Record record = new Record();

			for (Node node : entity.getChildren()) {
				if (node instanceof Attribute) {
					Object value = resultSet.getObject(node.getName());

					if (value instanceof ClobImpl) {
						record.addObject(clobToString((Clob) value));
					} else {
						record.addObject(value);
					}
				}
			}

			TabbedView.activePanel.getTableModel().addRow(record.getData().toArray());
			records.add(record);
		}

		System.out.println("RECORDS: " + records);
		resultSet.close();
		statement.close();
		return records;
	}

	@Override
	public void updateRecord(Object object) {

	}

	@Override
	public void deleteRecord(Object object) {

	}

	private String clobToString(Clob data) {
		StringBuilder sb = new StringBuilder();

		try {
			Reader reader = data.getCharacterStream();
			BufferedReader br = new BufferedReader(reader);

			String line;

			while (null != (line = br.readLine())) {
				sb.append(line);
			}

			br.close();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}
}
