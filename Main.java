import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SqlScriptGenerator generator = new SqlScriptGenerator("employees");

        // Define table structure
        generator.addColumn("id", DataType.NUMBER, false);
        generator.addColumn("name", DataType.STRING, false);
        generator.addColumn("hire_date", DataType.DATE, true);
        generator.addColumn("is_active", DataType.BOOLEAN, false);

        // Sample data
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"1", "John Doe", "2024-01-01", "true"});
        data.add(new String[]{"2", "Jane Smith", "2024-02-15", "true"});

        // Generate script
        String script = generator.generateInsertScript(data);

        // Print or save the script
        System.out.println(script);

        try {
            generator.saveToFile(script, "dummy/insert_script.sql");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
