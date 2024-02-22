package utils;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonSchemaValidator {
        public static String validateJsonAgainstSchema(String jsonResponse, String schemaRoute) {
        try {
            String pathSchema = "src/test/java/resources/" + schemaRoute;
            String schemaContent = new String(Files.readAllBytes(Paths.get(pathSchema)));
            JSONObject rawSchema = new JSONObject(new JSONTokener(schemaContent));

            Schema schema = SchemaLoader.load(rawSchema);

            schema.validate(new JSONObject(jsonResponse));
            
            return "";
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.everit.json.schema.ValidationException e) {
            System.out.println("Validation failed: " + e.getMessage());
            return "Validation failed: " + e.getMessage();
        }

        return "Please check the schema Path";
    }
}
