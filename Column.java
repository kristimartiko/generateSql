public class Column {
    private final String name;
    private final DataType type;
    private final boolean isNullable;

    public Column(String name, DataType type, boolean isNullable) {
        this.name = name;
        this.type = type;
        this.isNullable = isNullable;
    }

    public String getName() {
        return name;
    }

    public DataType getType() {
        return type;
    }

    public boolean isNullable() {
        return isNullable;
    }

}
