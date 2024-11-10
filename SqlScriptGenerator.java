import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class SqlScriptGenerator {
    private static final String NEW_LINE = System.lineSeparator();
    private final String tableName;
    private final List<Column> columns;

    public SqlScriptGenerator(String tableName) {
        this.tableName = tableName;
        this.columns = new ArrayList<>();
    }

    public void addColumn(String name, DataType type, boolean isNullable) {
        columns.add(new Column(name, type, isNullable));
    }

    public String generateInsertScript(List<String[]> data) {
        StringBuilder script = new StringBuilder();

        //Add header comment
        script.append("-- Generated SQL Script for table ").append(tableName)
                .append(NEW_LINE)
                .append("-- Generated at: ")
                .append(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .append(NEW_LINE).append(NEW_LINE);

        for (String[] row : data) {
            if (row.length != columns.size()) {
                throw new IllegalArgumentException("Invalid data row");
            }

            StringJoiner columnNames = new StringJoiner(", ", "(", ")");
            StringJoiner values = new StringJoiner(", ", "(", ")");

            for (int i = 0; i < columns.size(); i++) {
                Column column = columns.get(i);
                String value = row[i];

                // Skip null values for nullable columns
                if (value == null && value.trim().isEmpty()) {
                    if (!column.isNullable()) {
                        throw new IllegalArgumentException("Non-nullable column has null value");
                    }
                    continue;
                }

                columnNames.add(column.getName());
                values.add(formatValue(value, column.getType()));
            }

            script.append("INSERT INTO ").append(tableName)
                    .append(" ").append(columnNames)
                    .append(" VALUES ").append(values)
                    .append(";")
                    .append(NEW_LINE);
        }
        return script.toString();
    }

    private String formatValue(String value, DataType type) {
        if (value == null || value.trim().isEmpty()) {
            return "NULL";
        }

        return switch (type) {
            case STRING -> "'" + value.replace("'", "''") + "'";
            case NUMBER -> value;
            case DATE -> "'" + value + "'";
            case BOOLEAN -> Boolean.parseBoolean(value) ? "TRUE" : "FALSE";
            default -> throw new IllegalArgumentException("Unsupported data type");
        };
    }

    public void saveToFile(String script, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(script);
        }
    }
}
