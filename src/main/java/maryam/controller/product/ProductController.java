package maryam.controller.product;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import maryam.dto.inventory.InventoryDTO;
import maryam.dto.product.ProductCreateDTO;
import maryam.dto.product.ProductUpdateDTO;
import maryam.models.category.Category;
import maryam.models.product.*;
import maryam.models.tag.Tag;
import maryam.service.like.LikeService;
import maryam.service.product.ProductService;
import maryam.service.visit.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping(path="/seller/create",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long createProduct(
            @RequestPart("product") ProductCreateDTO product
            ){
        System.out.println("product create");
        return  productService.createProduct(product);
    }
    @PostMapping(path="/seller/update/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long updateProduct(
            @RequestPart("product") ProductCreateDTO product,
                                 @PathVariable("id")Long id) throws Error{
        return productService.updateProduct(id,product);
    }
    @GetMapping(path = "/seller/{id}")
    public ProductUpdateDTO getProduct(@PathVariable("id") Long id) throws Exception{
        return productToDto(productService.getProduct(id));
    }
    @DeleteMapping(path="/seller/delete/{id}")
    public void deleteProduct(@PathVariable("id")Long id){
        productService.removeProduct(id);
    }

    public ProductUpdateDTO productToDto(Product product){
        return new ProductUpdateDTO()
                .builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .brand(product.getBrand())
                .tags(product.getTags().stream().map(tag -> tag.getName()).collect(Collectors.toList()))
                .category(product.getCategory())
                .dimensions(product.getDimensions())
//                .articles(product.getArticles().stream().map(article -> new ArticleCreateDTO()
//                        .builder()
//                                .color(article.getColor())
//                                .sellerArticle(article.getSellerArticle())
//                                .inventory(article.getInventory())
//                                .pictures(article.getPictures())
//                        .build())
//                        .collect(Collectors.toList()))
                .build();
        //return null;
    }
}
