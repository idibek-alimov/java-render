package maryam.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import maryam.models.uservisit.Visit;

import java.io.IOException;

public class VisitSerializer extends StdSerializer<Visit> {
    public VisitSerializer(){
        this(null);
    }
    public VisitSerializer(Class<Visit> t){
        super(t);
    }

    @Override
    public void serialize(Visit visit, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonProcessingException{
        jgen.writeStartObject();
        jgen.writeNumberField("id",visit.getId());
        jgen.writeNumberField("product",visit.getProduct().getId());
        jgen.writeNumberField("user",visit.getUser().getId());
        jgen.writeObjectField("createdAt",visit.getCreatedAt());
        jgen.writeEndObject();
    }
}
