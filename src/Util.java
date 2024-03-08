import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

    public static String toCamelCase(String str) {
        String newString = "";
        boolean snakeTrail = false;

        for (int i = 0; i < str.toCharArray().length; ++i) {
            String c = String.valueOf(str.charAt(i));
            if (snakeTrail) {
                c = c.toUpperCase();
                snakeTrail = false;
            }
            if (c.equals("_")) {
                c = "";
                snakeTrail = true;
            }
            newString += c;
        }
        return newString;
    }

    public static String toCapitalizeCase(String str) {
        return str.replaceFirst(String.valueOf(str.charAt(0)), String.valueOf(str.charAt(0)).toUpperCase());
    }

    public static String formatToEntityName(String str) {
        String newString = Util.toCamelCase(str);
        newString = Util.toCapitalizeCase(newString);
        if (newString.charAt(newString.length() - 1) == 's') {
            newString = newString.substring(0, newString.length() - 1);
        }
        return newString;
    }

    public static String formatToFieldName(String str) {
        String newString = Util.toCamelCase(str);
        if (newString.charAt(newString.length() - 1) == 's') {
            newString = newString.substring(0, newString.length() - 1);
        }
        return newString;
    }

    public static String getFieldType(String dataType) {
        String fieldType = "";
        switch (dataType) {
            case "integer":
            case "bigint":
                fieldType = "int";
                break;

            case "numeric":
            case "double precision":
            case "real":
                fieldType = "double";
                break;

            case "character varying":
            case "character":
            case "text":
                fieldType = "String";
                break;

            case "date":
                fieldType = "LocalDate";
                break;

            case "time without time zone":
                fieldType = "LocalTime";
                break;

            case "timestamp without time zone":
                fieldType = "LocalDateTime";
                break;

            case "boolean":
                fieldType = "boolean";
                break;

            default:
                fieldType = "String";
                break;
        }
        return fieldType;
    }

    public static String getPackagePath() {
        String packagePath = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String filePath = "config.json";
            JsonNode jsonNode = objectMapper.readTree(new File(filePath));
            packagePath = jsonNode.get("packagePath").asText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packagePath;
    }

    public static String getPackageName() {
        String packageName = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String filePath = "config.json";
            JsonNode jsonNode = objectMapper.readTree(new File(filePath));
            packageName = jsonNode.get("packageName").asText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packageName;
    }

    public static String getContentFile(String path) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static void createFile(String path, String content) {
        try {
            if (!Files.exists(Paths.get(path))) {
                Files.write(Paths.get(path), content.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFolder(String path) {
        try {
            if (!Files.exists(Paths.get(path))) {
                Files.createDirectory(Paths.get(path));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String arrayToString(List<String> array) {
        String newString = "";
        for (String string : array) {
            newString += string + "\n";
        }
        return newString;
    }

}