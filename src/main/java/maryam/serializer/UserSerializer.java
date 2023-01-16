package maryam.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import maryam.models.product.Product;
import maryam.models.user.User;

import java.io.IOException;

public class UserSerializer extends StdSerializer<User> {
    private static final long serialVersionUID = 1L;
    public UserSerializer(){
        this(null);
    }
    public UserSerializer(Class<User> t){
        super(t);
    }

    @Override
    public void serialize(User user, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id",user.getId());
        jgen.writeStringField("username",user.getUsername());
        jgen.writeStringField("password",user.getPassword());
        jgen.writeStringField("name",user.getName());
        jgen.writeEndObject();
    }

}
