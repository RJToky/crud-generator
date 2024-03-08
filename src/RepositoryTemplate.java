public class RepositoryTemplate {

        public static String PACKAGE_NAME = """
                        package [PACKAGE_NAME].repository;
                        """;

        public static String IMPORTS = """
                        \nimport org.springframework.data.jpa.repository.JpaRepository;
                        import org.springframework.stereotype.Repository;
                        import [PACKAGE_NAME].model.[ENTITY_NAME];
                        """;

        public static String REPOSITORY_NAME = """
                        \n@Repository
                        public interface [ENTITY_NAME]Repository extends JpaRepository<[ENTITY_NAME], Long> {
                        }
                        """;
}
