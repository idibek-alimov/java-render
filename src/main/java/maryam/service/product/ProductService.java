package maryam.service.product;

import lombok.RequiredArgsConstructor;
import maryam.data.product.ProductRepository;
import maryam.dto.inventory.InventoryDTO;
import maryam.models.category.Category;
import maryam.models.product.*;
import maryam.models.tag.Tag;
import maryam.models.user.User;
import maryam.models.uservisit.Visit;
import maryam.service.article.ArticleService;
import maryam.service.article.DiscountService;
import maryam.service.category.CategoryService;
import maryam.service.tag.TagService;
import maryam.service.user.UserService;
import maryam.service.visit.VisitService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.transaction.Transactional;
import java.util.*;

@Service @RequiredArgsConstructor @Transactional
public class ProductService implements ProductServiceInterface{
    private final ProductRepository productRepository;
    private final UserService userService;
    private final TagService tagService;
    private final CategoryService categoryService;
    private final DimentsionsService dimentsionsService;
    private final ProductGenderService productGenderService;

    public Product createProduct(Product productInfo){
        User user = userService.getCurrentUser();
        Product product = new Product(productInfo.getName(),productInfo.getDescription(),productInfo.getBrand(),user);
        product = categoryService.addCategoryToProduct(product,productInfo.getCategory());
        product = productRepository.save(product);
        product.setDimensions(dimentsionsService.addDimensionsToProduct(productInfo.getDimensions(),product));
        //product.addArticle(articleService.createArticleWithPicture(article,pictures,product));
        if(productInfo.getTags()!=null || productInfo.getTags().size()!=0){
            tagService.addTagToProduct(product,productInfo.getTags());
        }
        productGenderService.setGenderToProduct(product,productInfo.getProductGender());
        productRepository.save(product);
        return product;
    }
    public Product updateProduct(Long id,Product product){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()){
            Product presentProduct = optionalProduct.get().builder()
                                    .name(product.getName())
                                    .description(product.getDescription())
                                    .brand(product.getBrand())
                                    .build();
            productGenderService.setGenderToProduct(presentProduct,product.getProductGender());
            tagService.updateProductTags(presentProduct,product.getTags());
            presentProduct.setDimensions(dimentsionsService.updateDimensions(product.getDimensions(),presentProduct));
            return presentProduct;
        }
        else {
            return product;
        }

    }

    public void removeProduct(Long id) {
        productRepository.deleteById(id);
    }
}
