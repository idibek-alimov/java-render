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
    private final DiscountRepository discountRepository;
    public Discount addDiscount(Article article,Integer percentage){
        System.out.println(2);
        Discount discount = new Discount(article,percentage);
        discount = discountRepository.save(discount);
        System.out.println(3);
        List<Discount> discounts = article.getDiscounts();
        System.out.println(4);
        discounts.add(discount);
        System.out.println(5);
        article.setDiscounts(discounts);
        System.out.println(6);
        return discountRepository.save(discount);
    }
}
