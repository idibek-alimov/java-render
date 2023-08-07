package maryam.service.article;

import lombok.RequiredArgsConstructor;
import maryam.data.product.DiscountRepository;
import maryam.models.product.Article;
import maryam.models.product.Discount;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DiscountService {
    private DiscountRepository discountRepository;
    public Discount addDiscount(Article article,Integer percentage){
        Discount discount = new Discount(article,percentage);
        discount = discountRepository.save(discount);
        List<Discount> discounts = article.getDiscounts();
        discounts.add(discount);
        article.setDiscounts(discounts);
        return discountRepository.save(discount);
    }
}
