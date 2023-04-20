package maryam.dto.inventory;

import lombok.Data;
import maryam.models.order.Item;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;


public interface SellerItemDTO {
    @Value("#{target.created_at}")
    Date getCreatedAt();
    Long getInventorySizeId();

    @Value("#{target.seller_article}")
    String getSellerArticle();
    String getName();
    String getBrand();
    @Value("#{target.original_price}")
    Double getOriginalPrice();

    Item.Status getStatus();

}
