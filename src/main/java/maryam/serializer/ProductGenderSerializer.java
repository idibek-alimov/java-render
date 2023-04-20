package maryam.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import maryam.models.product.ProductGender;

import java.io.IOException;


public class ProductGenderSerializer extends StdSerializer<ProductGender> {
    public ProductGenderSerializer(){
        this(null);
    }


    public ProductGenderSerializer(Class<ProductGender> t){
        super(t);
    }

    @Override
    public void serialize(ProductGender productGender, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id",productGender.getId());
        jgen.writeStringField("name",productGender.getName());
        jgen.writeEndObject();
    }
}