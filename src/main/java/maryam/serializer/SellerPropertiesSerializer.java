package maryam.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import maryam.data.user.SellerPropertiesRepository;
import maryam.models.product.Color;
import maryam.models.user.SellerProperties;

import java.io.IOException;

public class SellerPropertiesSerializer extends StdSerializer<SellerProperties> {
    public SellerPropertiesSerializer(){
        this(null);
    }


    public SellerPropertiesSerializer(Class<SellerProperties> t){
        super(t);
    }

    @Override
    public void serialize(SellerProperties sellerProperties, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("balance",sellerProperties.getBalance());
        jgen.writeNumberField("products_in_storage",sellerProperties.getProductsInStorage());
        jgen.writeNumberField("products_sold",sellerProperties.getProductsSold());
        jgen.writeEndObject();
    }
}
