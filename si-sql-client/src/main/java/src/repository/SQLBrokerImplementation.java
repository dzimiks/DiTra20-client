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
        return 0;
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
