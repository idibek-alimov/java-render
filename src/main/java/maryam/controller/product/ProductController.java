package maryam.controller.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maryam.dto.inventory.InventoryDTO;
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

@Slf4j
@RestController
@RequestMapping(path="/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping(path="/seller/create",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product createProduct(@RequestPart("product")Product product){
        return  productService.createProduct(product);
    }
    @PostMapping(path="/seller/update/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product updateProduct(@RequestPart("product")Product product,
                                 @PathVariable("id")Long id){
        return productService.updateProduct(id,product);
    }
    @DeleteMapping(path="/seller/delete/{id}")
    public void deleteProduct(@PathVariable("id")Long id){
        productService.removeProduct(id);
    }

}
