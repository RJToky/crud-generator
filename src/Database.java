import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static List<String> getTablesNames(Connection connection) {
        List<String> tablesNames = new ArrayList<String>();
        try (Statement statement = connection.createStatement()) {
            String query = """
                    SELECT tablename AS table_name
                    FROM pg_catalog.pg_tables
                    WHERE schemaname = 'public'
                        """;
            ResultSet res = statement.executeQuery(query);
            while (res.next()) {
                tablesNames.add(res.getString("table_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tablesNames;
    }

    public static List<Entity> getEntities(Connection connection) {
        List<Entity> entities = new ArrayList<Entity>();
        List<String> tablesNames = Database.getTablesNames(connection);
        for (String tableName : tablesNames) {
            entities.add(new Entity(connection, tableName));
        }
        return entities;
    }

}
