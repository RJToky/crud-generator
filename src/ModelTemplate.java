public class ModelTemplate {

        public static String PACKAGE_NAME = """
                        package [PACKAGE_NAME].model;
                        """;

        public static String IMPORTS = """
                        \nimport lombok.Data;
                        import jakarta.persistence.Entity;
                        import jakarta.persistence.Table;
                        import jakarta.persistence.Id;
                        import jakarta.persistence.GeneratedValue;
                        import jakarta.persistence.GenerationType;
                        import jakarta.persistence.Column;
                        """;

        public static String TABLE_NAME = """
                        \n@Data
                        @Entity
                        @Table(name = "[TABLE_NAME]")
                        """;

        public static String ENTITY_NAME = """
                        public class [ENTITY_NAME] {
                        """;

        public static String FIELD_PRIMARY = """
                        \t@Id
                        \t@GeneratedValue(strategy = GenerationType.IDENTITY)
                        \t@Column(name = "[COLUMN_NAME]")
                        \tprivate [FIELD_TYPE] [FIELD_NAME];\n
                        """;

        public static String FIELD_FOREIGN = """
                        \t@ManyToOne
                        \t@JoinColumn(name = "[COLUMN_NAME]")
                        \tprivate [FIELD_TYPE] [FIELD_NAME];\n
                        """;

        public static String FIELD = """
                        \t@Column(name = "[COLUMN_NAME]")
                        \tprivate [FIELD_TYPE] [FIELD_NAME];\n
                        """;
}
