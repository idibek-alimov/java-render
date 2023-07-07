package maryam.service.product;

import lombok.RequiredArgsConstructor;
import maryam.data.product.ProductRepository;
import maryam.dto.inventory.InventoryDTO;
import maryam.dto.product.ProductCreateDTO;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.transaction.Transactional;
import java.util.*;

@Service @RequiredArgsConstructor @Transactional
public class ProductService implements ProductServiceInterface{
    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final DimentsionsService dimentsionsService;
    private final BrandService brandService;

    public Product getProductById(Long id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return product.get();
        }
        else {
            return null;
        }
    }
    public Product getProductIfOwner(Long id){
        Product product = getProductById(id);
        if (product!=null && product.getUser().getId().equals(userService.getCurrentUser().getId())){
            return product;
        }
        else {
            return null;
        }
    }
    public Long createProduct(ProductCreateDTO productCreateDTO){
        User user = userService.getCurrentUser();
        Product product = new Product()
                .builder()
                .name(productCreateDTO.getName())
                .description(productCreateDTO.getDescription())
                .brand(brandService.findBrand(productCreateDTO.getBrand()))
                .user(user)
                .build();
        product = categoryService.addCategoryToProduct(product,productCreateDTO.getCategory());
        product = productRepository.save(product);
        product.setDimensions(dimentsionsService.addDimensionsToProduct(new Dimensions()
                .builder()
                        .width(productCreateDTO.getDimensions().getWidth())
                        .height(productCreateDTO.getDimensions().getHeight())
                        .length(productCreateDTO.getDimensions().getLength())
                .build(),product));
        productRepository.save(product);
        return product.getId();
    }
    public Long updateProduct(Long id,ProductCreateDTO product) throws Error{
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()){
            Product presentProduct = optionalProduct.get();
            presentProduct.setName(product.getName());
            presentProduct.setDescription(product.getDescription());
            presentProduct.setBrand(brandService.findBrand(product.getBrand()));
            presentProduct.setDimensions(dimentsionsService.updateDimensions(new Dimensions()
                    .builder()
                    .width(product.getDimensions().getWidth())
                    .height(product.getDimensions().getHeight())
                    .length(product.getDimensions().getLength())
                    .build(),presentProduct));
            return presentProduct.getId();
        }
        else {
            throw new Error("Product with id = "+id+"does not exist nigga;");
        }

    }
    public Product getProduct(Long id) throws Exception{
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()){
            return optionalProduct.get();
        }
        else {
            throw new Exception("Product with the given id does not exist");
        }
    }
    public void removeProduct(Long id) {
        productRepository.deleteById(id);
    }
}
