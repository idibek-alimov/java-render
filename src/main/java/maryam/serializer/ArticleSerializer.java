package maryam.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import maryam.models.inventory.Inventory;
import maryam.models.picture.Picture;
import maryam.models.product.Article;
import maryam.models.product.Discount;
import maryam.models.product.Product;
import maryam.service.like.LikeService;
import maryam.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ArticleSerializer extends StdSerializer<Article> {
    @Autowired
    public LikeService likeService;
    @Autowired
    public FileStorageService fileStorageService;
    private static final long serialVersionUID = 1L;
    public ArticleSerializer(){
        this(null);
    }
    public ArticleSerializer(Class<Article> t){
        super(t);
    }

    @Override
    public void serialize(Article article, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id",article.getId());
        jgen.writeBooleanField("likes",likeService.check_like(article.getId()));
        if (article.getColor()!=null) {
            jgen.writeObjectField("color", article.getColor());
        }
        jgen.writeStringField("sellerArticle",article.getSellerArticle());
        jgen.writeArrayFieldStart("inventory");
        for(Inventory inventory:article.getInventory()){
            jgen.writeObject(inventory);
        }
        jgen.writeEndArray();
        jgen.writeArrayFieldStart("discounts");
        for(Discount discount:article.getDiscounts()){
            jgen.writeObject(discount);
        }
        jgen.writeEndArray();
        jgen.writeArrayFieldStart("pictures");
        for(Picture picture:article.getPictures()){
            jgen.writeObject(picture);
        }
        jgen.writeEndArray();
        jgen.writeNumberField("product_id",article.getProduct().getId());
        jgen.writeStringField("name",article.getProduct().getName());
        jgen.writeStringField("brand",article.getProduct().getBrand().getName());
        if (article.getProduct().getProductGender()!=null){
            jgen.writeStringField("gender",article.getProduct().getProductGender().getName());
        }
        jgen.writeStringField("category",article.getProduct().getCategory().getName());
        jgen.writeStringField("description",article.getProduct().getDescription());
        jgen.writeObjectField("dimensions",article.getProduct().getDimensions());
        jgen.writeEndObject();
    }

}