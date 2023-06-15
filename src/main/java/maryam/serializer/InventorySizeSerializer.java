package maryam.serializer;

//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.fasterxml.jackson.databind.ser.std.StdSerializer;
//import maryam.models.inventory.InventorySize;
//
//
//import java.io.IOException;
//
//public class InventorySizeSerializer extends StdSerializer<InventorySize> {
//    public InventorySizeSerializer(){
//        this(null);
//    }
//
//
//    public InventorySizeSerializer(Class<InventorySize> t){
//        super(t);
//    }
//
//    @Override
//    public void serialize(InventorySize inventorySize, JsonGenerator jgen, SerializerProvider provider)
//            throws IOException, JsonProcessingException {
//        jgen.writeStartObject();
//        jgen.writeNumberField("id",inventorySize.getId());
//        jgen.writeStringField("size",inventorySize.getSize());
//        jgen.writeEndObject();
//    }
//}
