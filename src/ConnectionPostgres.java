import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConnectionPostgres {

    public static Connection getConnection()
            throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        String filePath = "config.json";
        JsonNode jsonNode = objectMapper.readTree(new File(filePath));

        String host = jsonNode.get("db").get("host").asText();
        String port = jsonNode.get("db").get("port").asText();
        String database = jsonNode.get("db").get("database").asText();
        String user = jsonNode.get("db").get("user").asText();
        String password = jsonNode.get("db").get("password").asText();

        String url = "jdbc:postgresql://%s:%s/%s".formatted(host, port, database);

        return DriverManager.getConnection(url, user, password);
    }
}
