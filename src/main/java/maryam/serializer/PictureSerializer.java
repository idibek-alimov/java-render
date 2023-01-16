package maryam.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import maryam.models.picture.Picture;
import maryam.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PictureSerializer extends StdSerializer<Picture> {
    public PictureSerializer(){this(null);}

    @Autowired
    public FileStorageService fileStorageService;
    public PictureSerializer(Class<Picture> t){super(t);}

    @Override
    public void serialize(Picture picture, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException{
        jgen.writeStartObject();
        jgen.writeNumberField("id",picture.getId());
        jgen.writeObjectField("src",fileStorageService.load(picture.getName()).getURL());
        jgen.writeEndObject();
    }
}
