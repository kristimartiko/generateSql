public enum DataType {
    STRING("VARCHAR"),
    NUMBER("NUMERIC"),
    DATE("TIMESTAMP"),
    BOOLEAN("BOOLEAN");

    private final String sqlType;

    private DataType(String sqlType) {
        this.sqlType = sqlType;
    }
}
