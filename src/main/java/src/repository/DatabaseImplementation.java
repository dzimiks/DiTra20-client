package src.repository;

import net.sourceforge.jtds.jdbc.ClobImpl;
import src.config.SQLConfig;
import src.models.Attribute;
import src.models.Entity;
import src.models.Record;
import src.models.datatypes.CharType;
import src.models.datatypes.DateType;
import src.models.datatypes.VarCharType;
import src.models.tree.Node;
import src.view.table.TabbedView;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * MYSQL database implementation
 */
public class DatabaseImplementation implements RepositoryImplementor {

    @Override
    public long createRecord(Object object) {
        Record newRecord = (Record) object;


        Entity newRecordEntity = newRecord.getEntity();

        ArrayList<Node> newRecordAttributes = (ArrayList) newRecordEntity.getChildren();
        ArrayList<String> newRecordTextFields = (ArrayList) newRecord.getTextFields();

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(newRecordEntity.getName()).append(" (");

        for (Node node : newRecordAttributes) {
            sb.append(node.getName().toUpperCase()).append(", ");
        }

        sb.delete(sb.length() - 2, sb.length());
        sb.append(") VALUES ('");

        //TODO check if text is tipe of string(not sure if its posible to be string)
        for (int i = 0; i < newRecord.getTextFields().size(); i++) {
            sb.append(newRecord.getTextFields().get(i)).append("', '");
        }

        sb.delete(sb.length() - 4, sb.length());
        sb.append("')");

        System.out.println("QUERY: " + sb);

        try {
            PreparedStatement statement = SQLConfig.getInstance().getDbConnection().prepareStatement(sb.toString());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public List<Record> readRecords() throws SQLException {
        System.out.println("Fetching DB data...");

        Entity entity = TabbedView.activePanel.getEntity();
        String query = "SELECT * FROM " + entity.getName();
        List<Record> records = new ArrayList<>();

//		System.out.println("\n==========");
//		System.out.println(query);
//		System.out.println("==========\n");

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

//		System.out.println("RECORDS: " + records);
        resultSet.close();
        statement.close();
        return records;
    }

    @Override
    public void updateRecord(Object newRecord, Object oldRecord) {
        Record updatedRecord = (Record) newRecord;
        Record recordToUpdate = (Record) oldRecord;

        Entity newRecordEntity = updatedRecord.getEntity();
        Entity oldRecordEntity = recordToUpdate.getEntity();

        ArrayList<Node> newRecordAttributes = (ArrayList) newRecordEntity.getChildren();
        ArrayList<String> newRecordTextFields = (ArrayList) updatedRecord.getTextFields();

        ArrayList<Node> oldRecordAttributes = (ArrayList) oldRecordEntity.getChildren();
        ArrayList<String> oldRecordTextFields = (ArrayList) recordToUpdate.getTextFields();

        int i = 0;

        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(newRecordEntity.getName()).append(" SET ");

        for (Node node : newRecordAttributes) {
            sb.append(node.getName().toUpperCase()).append(" = '").append(newRecordTextFields.get(i)).append("', ");
            i++;
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(" WHERE ");
        int j = 0;

        for (Node node : oldRecordAttributes) {
            sb.append(node.getName().toUpperCase()).append(" = '").append(oldRecordTextFields.get(j)).append("' AND ");
            j++;
        }
        sb.delete(sb.length() - 4, sb.length());

//		System.out.println("Query: "+ sb);

        try {
            PreparedStatement statement = SQLConfig.getInstance().getDbConnection().prepareStatement(sb.toString());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRecord(Object object) {
        System.out.println("delete record");
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
