package maryam.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import maryam.models.order.Item;
import maryam.models.uservisit.Visit;
import maryam.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


public class ItemSerializer extends StdSerializer<Item> {
    public ItemSerializer(){
        this(null);
    }


    public ItemSerializer(Class<Item> t){
        super(t);
    }

    @Override
    public void serialize(Item item, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id",item.getId());
        jgen.writeObjectField("article",item.getArticle());
        jgen.writeNumberField("amount",item.getAmount());
        jgen.writeEndObject();
    }
}