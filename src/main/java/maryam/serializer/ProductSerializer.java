package maryam.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.RequiredArgsConstructor;
import maryam.models.inventory.Inventory;
import maryam.models.picture.Picture;
import maryam.models.product.Article;
import maryam.models.product.Product;
import maryam.service.like.LikeService;
import maryam.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Embeddable;
import java.io.IOException;


@Service
public class ProductSerializer extends StdSerializer<Product> {
    @Autowired
    public LikeService likeService;
    @Autowired
    public FileStorageService fileStorageService;
    private static final long serialVersionUID = 1L;
    public ProductSerializer(){
        this(null);
    }
    public ProductSerializer(Class<Product> t){
        super(t);
    }

    @Override
    public void serialize(Product product, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonProcessingException{
        jgen.writeStartObject();
        jgen.writeNumberField("id",product.getId());
//        jgen.writeObjectField("category",product.getCategory());
        jgen.writeStringField("name",product.getName());
        jgen.writeNumberField("user",product.getUser().getId());
        //jgen.writeNumberField("price",product.getPrice());
//        jgen.writeBooleanField("likes",likeService.check_like(product.getId()));
        jgen.writeStringField("description",product.getDescription());
        jgen.writeArrayFieldStart("articles");
        for(Article article:product.getArticles()){
            jgen.writeObject(article);
        }
        jgen.writeEndArray();
//        jgen.writeObjectField("id",product.getArticles().get(0));
//        jgen.writeArrayFieldStart("articles");
//        for(Article article:product.getArticles()){
//            jgen.writeNumberField("article_id",article.getId());
////            jgen.writeStringField("color",article.getColor().getName());
//        }
//        jgen.writeEndArray();
//        jgen.writeArrayFieldStart("pics");
//        for (Picture pic: product.getArticles()){
//            jgen.writeObject(fileStorageService.load(pic.getName()).getURL());
//        }
//        jgen.writeEndArray();
//        jgen.writeArrayFieldStart("inventory");
//        for(Inventory inventory: product.getInventory()){
//            jgen.writeObject(inventory);
//        }
//        jgen.writeEndArray();
        jgen.writeEndObject();
    }

}
