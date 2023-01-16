package maryam.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import maryam.models.product.Product;

import java.io.IOException;

public class NewProductSerializer extends JsonSerializer<Product> {

    @Override
    public void serialize(Product product, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectFieldStart("product");
        jsonGenerator.writeObjectField("name",product.getName());
        jsonGenerator.writeEndObject();
    }
}
