package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class SchemaValidator {
    private static final ObjectMapper mapper = new ObjectMapper(); //create mapper
    private static final JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);

    public static Set<ValidationMessage>  validateJsonSchema(String responseBody, String pathToSchema)
            throws IOException {

        JsonNode json = mapper.readTree(responseBody);                  //reading json tree from response body
        JsonNode schemaNode = mapper.readTree(new File(pathToSchema));  //reading schema tree from file
        JsonSchema jsonSchema = factory.getSchema(schemaNode);          //creating object json schema

        return jsonSchema.validate(json);                               //validating
    }
}