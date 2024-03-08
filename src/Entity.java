import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Entity {
    private String tableName;
    private String entityName;
    private List<EntityField> fields;
    private List<String> importsJpa;
    private List<String> importsType;

    public Entity(Connection connection, String tableName) {
        String query = """
                SELECT
                    cols.table_name,
                    cols.column_name,
                    cols.data_type,
                    fk.foreign_table_name,
                    fk.foreign_column_name,
                    COALESCE(fk.is_primary, 'false') AS is_primary,
                    COALESCE(fk.is_foreign, 'false') AS is_foreign
                FROM information_schema.columns AS cols
                LEFT JOIN (
                    SELECT
                        tc.table_name,
                        kcu.column_name,
                        ccu.table_name AS foreign_table_name,
                        ccu.column_name AS foreign_column_name,
                        CASE
                            WHEN tc.constraint_type='PRIMARY KEY' THEN 'true'
                            ELSE 'false'
                        END AS is_primary,
                        CASE
                            WHEN tc.constraint_type='FOREIGN KEY' THEN 'true'
                            ELSE 'false'
                        END AS is_foreign
                    FROM information_schema.table_constraints AS tc
                    JOIN information_schema.key_column_usage AS kcu ON tc.constraint_name = kcu.constraint_name AND tc.table_schema = kcu.table_schema
                    JOIN information_schema.constraint_column_usage AS ccu ON ccu.constraint_name = tc.constraint_name
                    WHERE tc.table_schema = 'public' AND tc.table_name = '[TABLE_NAME]'
                ) AS fk ON cols.column_name = fk.column_name AND cols.table_name = fk.table_name
                WHERE cols.table_name = '[TABLE_NAME]'
                        """;
        query = query.replace("[TABLE_NAME]", tableName);
        this.setTableName(tableName);
        this.setEntityName(Util.formatToEntityName(tableName));

        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery(query);

            List<EntityField> fields = new ArrayList<EntityField>();
            Set<String> importsJpa = new HashSet<String>();
            Set<String> importsType = new HashSet<String>();

            while (res.next()) {
                boolean isPrimary = Boolean.parseBoolean(res.getString("is_primary"));
                boolean isForeign = Boolean.parseBoolean(res.getString("is_foreign"));

                String foreignTableName = res.getString("foreign_table_name");
                String foreignColumnName = res.getString("foreign_column_name");
                String columnName = res.getString("column_name");

                String fieldName = isForeign ? Util.formatToFieldName(foreignTableName)
                        : Util.toCamelCase(res.getString("column_name"));
                String fieldType = isForeign ? Util.formatToEntityName(foreignTableName)
                        : Util.getFieldType(res.getString("data_type"));

                fields.add(EntityField.builder()
                        .columnName(columnName)
                        .fieldName(fieldName)
                        .fieldType(fieldType)
                        .foreignTableName(foreignTableName)
                        .foreignColumnName(foreignColumnName)
                        .isPrimary(isPrimary)
                        .isForeign(isForeign).build());

                if (isForeign) {
                    importsJpa.add("import jakarta.persistence.ManyToOne;");
                    importsJpa.add("import jakarta.persistence.JoinColumn;");
                }

                if (fieldType.equals("LocalDate")) {
                    importsType.add("import java.time.LocalDate;");
                } else if (fieldType.equals("LocalTime")) {
                    importsType.add("import java.time.LocalTime;");
                } else if (fieldType.equals("LocalDateTime")) {
                    importsType.add("import java.time.LocalDateTime;");
                }
            }
            this.setFields(fields);
            this.setImportsJpa(new ArrayList<String>(importsJpa));
            this.setImportsType(new ArrayList<String>(importsType));

            statement.close();
            res.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}