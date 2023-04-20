package maryam.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import maryam.models.category.Category;

import java.io.IOException;

public class CategorySerializer extends StdSerializer<Category> {
    public CategorySerializer(){
        this(null);
    }


    public CategorySerializer(Class<Category> t){
        super(t);
    }

    @Override
    public void serialize(Category category, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id",category.getId());
        jgen.writeStringField("name",category.getName());
        if(category.getCategoryProperties()!=null){
            if(category.getCategoryProperties().getSize()!=null) {
//                System.out.println("has size");
                jgen.writeBooleanField("size", category.getCategoryProperties().getSize());
            }
            if(category.getCategoryProperties().getColor()!=null) {
//                System.out.println("has color");
                jgen.writeBooleanField("color", category.getCategoryProperties().getColor());
            }
            //jgen.writeObjectField("properties",category.getCategoryProperties());
        }
        jgen.writeEndObject();
    }
}
