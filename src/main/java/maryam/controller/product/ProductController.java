package maryam.controller.product;

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
public class ProductController {
    @Autowired
    private  ProductService productService;
    @Autowired
    private  VisitService visitService;
    @Autowired
    public LikeService likeService;
    @GetMapping(path="/{page}/{amount}")
    public Page<Product> productList(@PathVariable("page")Integer page,@PathVariable("amount")Integer amount){
        return productService.listOfProducts(page,amount);
    }
    @GetMapping(path="resentvisited/{page}/{amount}")
    public List<Product> resentVisited(@PathVariable("page")Integer page,@PathVariable("amount")Integer amount){
        return productService.getResentVisits(page,amount);
    }
//    @PostMapping(path="",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
//    public Product createProduct(@RequestPart("picture") List<MultipartFile> pictures,
//                                 @RequestPart("product") Product product,
//                                 @RequestPart("size")List<Inventory> inventories,
//                                 @RequestPart("tags")List<Tag> tags,
//                                 @RequestPart("color")Color color){
//        System.out.println("inside post");
//        for(Inventory inventory:inventories){
//            System.out.println(inventory);
//        }
//        return productService.addProduct(product,inventories,color,pictures,tags);
//
//    }
//    @PostMapping(path="/create",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
//    public Product createProductWithCategory(@RequestPart("picture") List<MultipartFile> pictures,
//                                             @RequestPart("product") Product product,
//                                             @RequestPart("inventory")List<InventoryDTO> inventories,
//                                             @RequestPart("tags")List<Tag> tags,
//                                             @RequestPart("color")Color color,
//                                             @RequestPart("category") Category category,
//                                             @RequestPart("gender") ProductGender productGender,
//                                             @RequestPart("dimensions")Dimensions dimensions,
//                                             @RequestPart("discount")Discount discount,
//                                             @RequestPart("sellerArticle")String sellerArticle){
//        return productService.addProduct(product,inventories,color,sellerArticle,pictures,dimensions,tags,category,productGender,discount);
//
//    }
    @PostMapping(path="/create/new",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product createProduct(@RequestPart("product")Product product){
        return  productService.createProduct(product);
    }
    @PostMapping(path="/update/{productId}/{articleId}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product updateProduct(@PathVariable("productId")Long productId,
                                             @PathVariable("articleId")Long articleId,
                                             @RequestPart("picture") List<MultipartFile> pictures,
                                             @RequestPart("oldPictures")List<String> leftoverPictures,
                                             @RequestPart("product") Product product,
                                             @RequestPart("inventory")List<InventoryDTO> inventories,
                                             @RequestPart("tags")List<Tag> tags,
                                             @RequestPart("color")Color color,
                                             @RequestPart("category") Category category,
                                             @RequestPart("gender") ProductGender productGender,
                                             @RequestPart("dimensions")Dimensions dimensions,
                                             @RequestPart("discount")Discount discount,
                                             @RequestPart("sellerArticle")String sellerArticle){
        System.out.println("all the data received");
        //productId articleId category product inventories color sellerArticle pictures leftoverPictures dimensions tags productGender discount
        return productService.updateProduct(productId,articleId,category,product,inventories,color,sellerArticle,pictures,leftoverPictures,dimensions,tags,productGender,discount);
    }
//    @PostMapping(path="/addarticle/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
//    public Product addToProduct(@RequestPart("picture") List<MultipartFile> pictures,
//                                 //@RequestPart("product") Product product,
//                                 @RequestPart("inventory")List<InventoryDTO> inventories,
//                                 //@RequestPart("tags")List<Tag> tags,
//                                 @RequestPart("color")Color color,
//                                @RequestPart("sellerArticle")String sellerArticle,
//                                @PathVariable("id")Long id){
//        System.out.println(id);
//        System.out.println("inside the add article thing");
//
//        return productService.addArticleToProduct(inventories,color,sellerArticle,pictures,id);
//    }
    @PostMapping(path="/article/add/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product addArticleToProduct(@RequestPart("article")Article article,
                                       @RequestPart("pictures")List<MultipartFile> pictures,
                                       @PathVariable("id")Long id){
//        System.out.println("Inside add article");
//        System.out.println(article.getInventory().get(0).getOriginalPrice());
//        System.out.println("THIS IS ORIGINAL PRICE");
        return productService.addArticleWithPicture(id,article,pictures);
    }
    @PostMapping(path="/article/add/picture/none/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product addArticleWithoutPic(@RequestPart("article")Article article,
                                       @PathVariable("id")Long id){
//        System.out.println("inside article add");
//        System.out.println(article.getInventory());
        return productService.addArticleWithoutPicture(id,article);
    }
    @PostMapping(path="/update/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product updateProduct(@RequestPart("product")Product product,
                                 @PathVariable("id")Long id){
//        System.out.println("inside product update 11");
        return productService.updateProduct(id,product);
    }
    @GetMapping(path="/{id}")
    public Optional<Product> retrieveProduct(@PathVariable("id") Long id){
        String user = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if(user!="anonymousUser"){
            System.out.println("hello there . General Kenobe");
//            visitService.addVisit(id);
        }
        return  productService.getProduct(id);
    }
    @GetMapping(path="/search/{name}/{page}/{amount}")
    public List<Product> findProduct(@PathVariable("name") String name,
                                         @PathVariable("page")Integer page,
                                         @PathVariable("amount")Integer amount){
        return productService.findByName(name,page,amount);
    }

    @DeleteMapping(path="/delete/{id}")
    public void deleteProduct(@PathVariable("id")Long id){
        productService.removeProduct(id);
    }

//    @JsonView(View.Detailed.class)
//    @JsonView(ProductSerializer.class)
    @GetMapping(path="/byuser/{page}/{amount}")
    public List<Product> userProducts(@PathVariable("page")Integer page,@PathVariable("amount")Integer amount){
        return productService.productsByUser(page,amount);
    }

    @GetMapping(path = "/bytag/{page}/{amount}")
    public List<Product> productByTag(//@RequestHeader(value="Authorization") String auth,
                                          @PathVariable("page")Integer page,
                                          @PathVariable("amount")Integer amount){
        //log.info((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return productService.productsByTag(page,amount);
    }

    @GetMapping(path="/similar/{id}/{page}/{amount}")
    public List<Product> productsBySimilarity(@PathVariable("id") Long id,
                                                  @PathVariable("page")Integer page,
                                                  @PathVariable("amount")Integer amount){
        return productService.similarProductsByTag(id,page,amount);
    }
    @GetMapping(path="/popular/{page}/{amount}")
    public List<Product> popularProducts(@PathVariable("page")Integer page,@PathVariable("amount")Integer amount){
        return productService.productsByPopularity(page,amount);
    }
    @GetMapping(path="/{name}/{page}/{amount}")
    public List<Product> productsByPage(@PathVariable("name")String name,@PathVariable("page")Integer page,@PathVariable("amount")Integer amount){
        return productService.getByPage(name,page,amount);
    }

}
