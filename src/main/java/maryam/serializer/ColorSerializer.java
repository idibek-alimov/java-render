package maryam.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import maryam.models.order.Item;
import maryam.models.product.Color;

import java.io.IOException;

public class ColorSerializer extends StdSerializer<Color> {
    public ColorSerializer(){
        this(null);
    }


    public ColorSerializer(Class<Color> t){
        super(t);
    }

    @Override
    public void serialize(Color color, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id",color.getId());
        jgen.writeStringField("name",color.getName());
        jgen.writeEndObject();
    }
}
