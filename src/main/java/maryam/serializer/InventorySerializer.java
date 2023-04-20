package maryam.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import maryam.models.inventory.Inventory;

import java.io.IOException;

public class InventorySerializer extends StdSerializer<Inventory> {
    public InventorySerializer(){
        this(null);
    }


    public InventorySerializer(Class<Inventory> t){
        super(t);
    }

    @Override
    public void serialize(Inventory inventory, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id",inventory.getId());
        if (inventory.getInStock()!=null)
        jgen.writeBooleanField("inStock",inventory.getInStock());
        if (inventory.getOriginalPrice()!=null)
        jgen.writeNumberField("original_price",inventory.getOriginalPrice());
        jgen.writeNumberField("quantity",inventory.getQuantity());
        if (inventory.getInventorySize()!=null) {
            jgen.writeObjectField("inventorySize",inventory.getInventorySize());
        }
        jgen.writeEndObject();
    }
}

