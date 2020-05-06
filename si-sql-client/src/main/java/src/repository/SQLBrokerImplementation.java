package src.repository;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import src.constants.Constants;
import src.models.Entity;
import src.models.Record;
import src.models.tree.Node;
import src.view.table.TabbedView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLBrokerImplementation implements RepositoryImplementor {

    @Override
    public long createRecord(Object object) {
        Record newRecord = (Record) object;
        Entity newRecordEntity = newRecord.getEntity();

        List<Node> newRecordAttributes = newRecordEntity.getChildren();
        List<String> newRecordTextFields = newRecord.getTextFields();

        Map<String, String> value = new HashMap<>();
        List<String> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(newRecordEntity.getName()).append(" (");

        for (Node node : newRecordAttributes) {
            sb.append(node.getName().toUpperCase()).append(", ");
            keys.add(node.getName().toUpperCase());
        }

        sb.delete(sb.length() - 2, sb.length());
        sb.append(") VALUES (");

        // TODO check if text is type of string (not sure if it's possible to be string)
        for (int i = 0; i < newRecord.getTextFields().size(); i++) {
            System.out.println("TEXTFIELD TEXT: " + newRecordTextFields.get(i));
            if (newRecordTextFields.get(i).equals("")) {
                sb.append("'null'").append(",");
            } else {
                sb.append("'").append(newRecordTextFields.get(i)).append("',");
                values.add(newRecordTextFields.get(i));
            }
        }

        sb.delete(sb.length() - 1, sb.length());
        sb.append(")");

        System.out.println("QUERY: " + sb);

        for (int i = 0; i < keys.size(); i++) {
            value.put(keys.get(i), values.get(i));
        }

        System.out.println("VALUE MAP: " + value);
        sendSQLInsertRequest(newRecordEntity.getName(), value);

        try {
            readRecords();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1;
    }

    @Override
    public List<Record> readRecords() throws SQLException {
        System.out.println("Fetching DB data...");
        TabbedView.activePanel.clearTable();
        Entity entity = TabbedView.activePanel.getEntity();
        System.out.println("ENTITY: " + entity.getName());
        String query = "SELECT * FROM " + entity.getName();
        List<Record> records = new ArrayList<>();

        System.out.println("\n==========");
        System.out.println(query);
        System.out.println("==========\n");

        String data = sendSQLSelectRequest(entity.getName());

        return null;
    }

    @Override
    public void updateRecord(Object newRecord, Object oldRecord) {
        Record updatedRecord = (Record) newRecord;
        Record recordToUpdate = (Record) oldRecord;

        Entity newRecordEntity = updatedRecord.getEntity();
        Entity oldRecordEntity = recordToUpdate.getEntity();

        List<Node> newRecordAttributes = newRecordEntity.getChildren();
        List<String> newRecordTextFields = updatedRecord.getTextFields();

        List<Node> oldRecordAttributes = oldRecordEntity.getChildren();
        List<String> oldRecordTextFields = recordToUpdate.getTextFields();

        Map<String, String> value = new HashMap<>();
        Map<String, String> condition = new HashMap<>();
        int i = 0;

        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(newRecordEntity.getName()).append(" SET ");

        for (Node node : newRecordAttributes) {
            sb.append(node.getName().toUpperCase()).append(" = '").append(newRecordTextFields.get(i)).append("', ");
            value.put(node.getName().toUpperCase(), newRecordTextFields.get(i));
            i++;
        }

        sb.delete(sb.length() - 2, sb.length());
        sb.append(" WHERE ");
        int j = 0;

        for (Node node : oldRecordAttributes) {
            sb.append(node.getName().toUpperCase()).append(" = '").append(oldRecordTextFields.get(j)).append("' AND ");
            condition.put(node.getName().toUpperCase(), oldRecordTextFields.get(j));
            j++;
        }

        sb.delete(sb.length() - 4, sb.length());
        System.out.println("Query: " + sb);

        System.out.println("VALUE MAP: " + value);
        System.out.println("CONDITION MAP: " + condition);
        sendSQLUpdateRequest(newRecordEntity.getName(), value, condition);

        try {
            readRecords();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRecord(Object object) {
        Record recordToDelete = (Record) object;
        Entity recordToDeleteEntity = recordToDelete.getEntity();
        List<Node> recordToDeleteAttributes = recordToDeleteEntity.getChildren();
        List<String> recordToDeleteTextFields = recordToDelete.getTextFields();
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ").append(recordToDeleteEntity.getName());

        sb.append(" WHERE ");

        int i = 0;
        Map<String, String> value = new HashMap<>();

        for (Node node : recordToDeleteAttributes) {
            if (recordToDeleteTextFields.get(i).equals("null")) {
                sb.append(node.getName().toUpperCase()).append(" LIKE '").append("' AND ");
            } else {
                sb.append(node.getName().toUpperCase()).append(" LIKE '").append(recordToDeleteTextFields.get(i)).append("%").append("' AND ");
                value.put(node.getName().toUpperCase(), recordToDeleteTextFields.get(i));
            }

            i++;
        }

        sb.delete(sb.length() - 5, sb.length());

        System.out.println("QUERY: " + sb);

        sendSQLDeleteRequest(recordToDeleteEntity.getName(), value);

        try {
            readRecords();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String sendSQLSelectRequest(String tableName) {
        HttpURLConnection connection = null;

        String token = sendLoginRequest();
        System.out.println("TOKEN: " + token);

        if (token == null) {
            System.err.println("[sendSQLSelectRequest]: Token is null!!!");
            return null;
        }

        try {
            URL url = new URL(Constants.BROKER_URL + "/services/sql-service/select");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setDoOutput(true);

            // TODO
            JsonObject json = new JsonObject();
            json.addProperty("tableName", tableName);

            String jsonInput = json.toString();
            System.out.println("JSON input: " + jsonInput);

            OutputStream os = connection.getOutputStream();
            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String responseLine;

            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            System.out.println("Broker response " + response.toString());
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }

    private String sendSQLDeleteRequest(String tableName, Map<String, String> value) {
        HttpURLConnection connection = null;

        String token = sendLoginRequest();
        System.out.println("TOKEN: " + token);

        if (token == null) {
            System.err.println("[sendSQLDeleteRequest]: Token is null!!!");
            return null;
        }

        try {
            URL url = new URL(Constants.BROKER_URL + "/services/sql-service/delete");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setDoOutput(true);

            // TODO
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(value);
            JsonObject json = new JsonObject();
            json.addProperty("tableName", tableName);
            json.add("value", jsonElement);

            String jsonInput = json.toString();
            System.out.println("JSON input: " + jsonInput);

            OutputStream os = connection.getOutputStream();
            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String responseLine;

            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            System.out.println("Broker response " + response.toString());
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }

    private String sendSQLInsertRequest(String tableName, Map<String, String> value) {
        HttpURLConnection connection = null;

        String token = sendLoginRequest();
        System.out.println("TOKEN: " + token);

        if (token == null) {
            System.err.println("[sendSQLInsertRequest]: Token is null!!!");
            return null;
        }

        try {
            URL url = new URL(Constants.BROKER_URL + "/services/sql-service/insert");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setDoOutput(true);

            // TODO
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(value);
            JsonObject json = new JsonObject();
            json.addProperty("tableName", tableName);
            json.add("value", jsonElement);

            String jsonInput = json.toString();
            System.out.println("JSON input: " + jsonInput);

            OutputStream os = connection.getOutputStream();
            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String responseLine;

            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            System.out.println("Broker response " + response.toString());
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }

    private String sendSQLUpdateRequest(String tableName, Map<String, String> value, Map<String, String> condition) {
        HttpURLConnection connection = null;

        String token = sendLoginRequest();
        System.out.println("TOKEN: " + token);

        if (token == null) {
            System.err.println("[sendSQLUpdateRequest]: Token is null!!!");
            return null;
        }

        try {
            URL url = new URL(Constants.BROKER_URL + "/services/sql-service/update");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setDoOutput(true);

            // TODO
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(value);
            JsonElement conditionJsonElement = gson.toJsonTree(condition);
            JsonObject json = new JsonObject();
            json.addProperty("tableName", tableName);
            json.add("value", jsonElement);
            json.add("condition", conditionJsonElement);

            String jsonInput = json.toString();
            System.out.println("JSON input: " + jsonInput);

            OutputStream os = connection.getOutputStream();
            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String responseLine;

            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            System.out.println("Broker response " + response.toString());
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }

    private String sendLoginRequest() {
        String username = "miki";
        String password = "milan";
        HttpURLConnection connection = null;

        try {
            URL url = new URL(Constants.BROKER_URL + "/login");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            JsonObject json = new JsonObject();
            json.addProperty("username", username);
            json.addProperty("password", password);
            String jsonInput = json.toString();
            System.out.println("JSON input: " + jsonInput);

            OutputStream os = connection.getOutputStream();
            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String responseLine;

            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            System.out.println("RESPONSE FROM BROKER: " + response.toString());

            if (response.toString().equals("")) {
                System.err.println("Error: Response from broker is empty!");
                return null;
            }

            JsonObject resp = JsonParser.parseString(response.toString()).getAsJsonObject();
            System.out.println("GOT JSON: " + resp);
            return resp.get("token").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
