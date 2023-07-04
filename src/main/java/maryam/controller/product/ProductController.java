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
<<<<<<< HEAD
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

=======
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
>>>>>>> testings
}
