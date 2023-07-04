package maryam.service.product;

import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import maryam.controller.product.CreateArticleHolder;
import maryam.controller.product.PictureHolder;
import maryam.data.product.ProductRepository;
import maryam.data.user.UserRepository;
import maryam.models.inventory.Inventory;
import maryam.models.picture.Picture;
import maryam.models.product.Color;
import maryam.models.product.Product;
=======
import maryam.data.product.ProductRepository;
import maryam.dto.inventory.InventoryDTO;
import maryam.dto.product.ProductCreateDTO;
import maryam.models.category.Category;
import maryam.models.product.*;
>>>>>>> testings
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
<<<<<<< HEAD
    private final UserRepository userRepository;
    private final PictureService pictureService;
    private final InventoryService inventoryService;
    private final VisitService visitService;
    private final TagService tagService;
    private final ArticleService articleService;
    public Product addProduct(Product product,
                              List<Inventory> inventories,
                              Color color,
                              List<MultipartFile> pictures,
                              List<Tag> tags)  {
        User user = userRepository.findByUsername((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(user == null){
            throw new RuntimeException("the fucking user is not found");
=======
    private final UserService userService;
    private final CategoryService categoryService;
    private final DimentsionsService dimentsionsService;

    public Product getProductById(Long id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return product.get();
        }
        else {
            return null;
>>>>>>> testings
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
                .brand(productCreateDTO.getBrand())
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
            presentProduct.setBrand(product.getBrand());
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
<<<<<<< HEAD

    @Override
    public Product editProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> productsByUser(Integer page,Integer amount){
        User user = userRepository.findByUsername((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return productRepository.getByUser(user,PageRequest.of(page,amount));
    }

    public List<Product> similarProductsByTag(Long id,Integer page,Integer amount){
        Iterable<Tag> tags = productRepository.findById(id).get().getTags();
        Set<Long> tagIds = new HashSet<>();
        for (Tag tag:tags){
            tagIds.add(tag.getId());
        }
        return productRepository.getBySimilarTags(tagIds,PageRequest.of(page,amount));
    }
    public List<Product> getResentVisits(Integer page,Integer amount){

        User user = userRepository.findByUsername((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        //Iterable<Product> products = Iterables.limit(productRepository.getByResentVisit(user.getId()),amount);
        return productRepository.getByResentVisit(user.getId(),PageRequest.of(page,amount));
    }
    public List<Product> productsByTag(Integer page,Integer amount){
        List<Visit> visits = visitService.getAllVisits();
        Set<Long> tag_Ids = new HashSet<>();
        Set<Long> visitedProductsId = new HashSet<>();
        for (Visit visit:visits){
            visitedProductsId.add(visit.getProduct().getId());
            for(Tag tag:visit.getProduct().getTags()) {
                tag_Ids.add(tag.getId());
            }
        }
        return productRepository.getByTags(tag_Ids,visitedProductsId);
    }
    public List<Product> productsByPopularity(Integer page,Integer amount){
        return productRepository.getMostVisited(amount,PageRequest.of(page,amount));
    }
    public List<Product> getByPage(String name,Integer page, Integer amount){
        return productRepository.findProductByname(name,PageRequest.of(page,amount));
    }
=======
>>>>>>> testings
}
