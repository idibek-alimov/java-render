package maryam.controller.product;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maryam.data.user.UserRepository;
import maryam.models.inventory.Inventory;
import maryam.models.product.Color;
import maryam.models.product.Product;
import maryam.models.tag.Tag;
import maryam.models.user.User;
import maryam.models.uservisit.Visit;
import maryam.serializer.NewProductSerializer;
import maryam.serializer.ProductSerializer;
import maryam.serializer.View;
import maryam.service.like.LikeService;
import maryam.service.product.ProductService;
import maryam.service.visit.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
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
    @PostMapping(path="",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product createProduct(@RequestPart("picture") List<MultipartFile> pictures,
                                 @RequestPart("product") Product product,
                                 @RequestPart("size")List<Inventory> inventories,
                                 @RequestPart("tags")List<Tag> tags,
                                 @RequestPart("color")Color color){
        System.out.println("inside post");
        for(Inventory inventory:inventories){
            System.out.println(inventory);
        }
        return productService.addProduct(product,inventories,color,pictures,tags);

    }
    @PostMapping(path="/create",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_OCTET_STREAM_VALUE,MediaType.IMAGE_JPEG_VALUE})
    public Product newCreateProduct(@RequestPart("product") Product product,
                                 @RequestPart("tags")List<Tag> tags,
                                 @RequestPart("article")List<CreateArticleHolder> articleHolders,
                                    @RequestPart("pictures")List<ArrayList<MultipartFile>> pic){
        System.out.println("got this far");
        System.out.println(pic.size());
        System.out.println(pic.get(0));
        System.out.println(pic.get(0));
        System.out.println(pic.get(0).getClass());
        System.out.println(pic.get(0));

        return null;

                                 //   @RequestPart("pictures")List<PictureHolder> pictureHolders){

        //return productService.addProduct(product,tags,articleHolders,pic);


    }
    @PostMapping(path="/addarticle/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product addToProduct(@RequestPart("picture") List<MultipartFile> pictures,
                                 //@RequestPart("product") Product product,
                                 @RequestPart("size")List<Inventory> inventories,
                                 //@RequestPart("tags")List<Tag> tags,
                                 @RequestPart("color")Color color,
                                @PathVariable("id")Long id){
        System.out.println("inside the add article thing");

        return productService.addArticleToProduct(inventories,color,pictures,id);

    }
//    @PostMapping(path="/nopic")//,consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
//    public Product createProduct(@RequestPart("product") Product product,
//                                 @RequestPart("size")List<Inventory> inventories){
//        System.out.println("nopic");
//        return productService.addProduct(product,inventories);
//    }
    @GetMapping(path="/{id}")
    public Optional<Product> retrieveProduct(@PathVariable("id") Long id){
        String user = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if(user!="anonymousUser"){
            visitService.addVisit(id);
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
