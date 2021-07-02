package io.swagger.properties;

import io.swagger.models.ArrayModel;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Response;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.Property;
import io.swagger.util.Yaml;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class ArrayPropertyDeserializerTest {
  private static final String yaml = "      produces:\n" +
          "        - application/yaml\n" +
          "      responses:\n" +
          "        200:\n" +
          "          description: OK\n" +
          "          schema:\n" +
          "            type: array\n" +
          "            minItems: 3\n" +
          "            maxItems: 100\n" +
          "            items:\n" +
          "              type: string\n" +
          "            example:\n" +
          "              - Array example\n" +
          "              - with two items";

  @Test(description = "it should includes the example in the arrayproperty")
  public void testArrayDeserialization () throws Exception {

      Operation operation = Yaml.mapper().readValue(yaml, Operation.class);
      Response response = operation.getResponsesObject().get("200");
      assertNotNull(response);
      
      Model responseSchema = response.getResponseSchema();
      assertNotNull(responseSchema);
      assertTrue(responseSchema instanceof ArrayModel);

      ArrayModel arrayModel = (ArrayModel) responseSchema;
      assertNotNull( arrayModel.getExample() );
      assertEquals(arrayModel.getMinItems(), new Integer(3));
      assertEquals(arrayModel.getMaxItems(), new Integer(100));
  }
}