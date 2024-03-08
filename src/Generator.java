import java.sql.Connection;
import java.util.List;

public class Generator {

    public static void model() {
        Util.createFolder(Util.getPackagePath() + "/model");

        try (Connection connection = ConnectionPostgres.getConnection()) {
            List<Entity> entities = Database.getEntities(connection);
            for (Entity entity : entities) {
                StringBuilder contentModelBuilder = new StringBuilder();

                contentModelBuilder
                        .append(ModelTemplate.PACKAGE_NAME.replace("[PACKAGE_NAME]",
                                Util.getPackageName()));
                contentModelBuilder.append(ModelTemplate.IMPORTS);
                contentModelBuilder.append(Util.arrayToString(entity.getImportsJpa()));
                contentModelBuilder.append(Util.arrayToString(entity.getImportsType()));
                contentModelBuilder.append(ModelTemplate.TABLE_NAME.replace("[TABLE_NAME]",
                        entity.getTableName()));
                contentModelBuilder.append(ModelTemplate.ENTITY_NAME.replace("[ENTITY_NAME]",
                        entity.getEntityName()));

                StringBuilder contentFieldsBuilder = new StringBuilder();
                for (EntityField field : entity.getFields()) {

                    if (field.isPrimary()) {
                        contentFieldsBuilder
                                .append(ModelTemplate.FIELD_PRIMARY.replace("[COLUMN_NAME]",
                                        field.getColumnName())
                                        .replace("[FIELD_TYPE]", field.getFieldType())
                                        .replace("[FIELD_NAME]", field.getFieldName()));

                    } else if (field.isForeign()) {
                        contentFieldsBuilder
                                .append(ModelTemplate.FIELD_FOREIGN.replace("[COLUMN_NAME]",
                                        field.getColumnName())
                                        .replace("[FIELD_TYPE]", field.getFieldType())
                                        .replace("[FIELD_NAME]", field.getFieldName()));

                    } else {
                        contentFieldsBuilder
                                .append(ModelTemplate.FIELD.replace("[COLUMN_NAME]", field.getColumnName())
                                        .replace("[FIELD_TYPE]", field.getFieldType())
                                        .replace("[FIELD_NAME]", field.getFieldName()));
                    }
                }
                contentModelBuilder.append(contentFieldsBuilder);
                contentModelBuilder.append("}");

                String filePath = Util.getPackagePath() + "/model/" + entity.getEntityName()
                        + ".java";
                Util.createFile(filePath, contentModelBuilder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void repository() {
        Util.createFolder(Util.getPackagePath() + "/repository");

        try (Connection connection = ConnectionPostgres.getConnection()) {
            List<Entity> entities = Database.getEntities(connection);
            for (Entity entity : entities) {
                StringBuilder contentRepositoryBuilder = new StringBuilder();

                contentRepositoryBuilder.append(RepositoryTemplate.PACKAGE_NAME);
                contentRepositoryBuilder.append(RepositoryTemplate.IMPORTS);
                contentRepositoryBuilder.append(RepositoryTemplate.REPOSITORY_NAME);

                String filePath = Util.getPackagePath() + "/repository/" + entity.getEntityName() + "Repository"
                        + ".java";
                Util.createFile(filePath,
                        contentRepositoryBuilder.toString().replace("[PACKAGE_NAME]", Util.getPackageName())
                                .replace("[ENTITY_NAME]", entity.getEntityName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void service() {

    }

    public static void controller() {

    }

}
