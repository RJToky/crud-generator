import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntityField {
    private String columnName;
    private String fieldName;
    private String fieldType;
    private String foreignTableName;
    private String foreignColumnName;
    private boolean isPrimary;
    private boolean isForeign;
}
