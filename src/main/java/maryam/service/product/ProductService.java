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
    private final VisitService visitService;
    private final TagService tagService;
    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final DimentsionsService dimentsionsService;
    private final ProductGenderService productGenderService;
    private final DiscountService discountService;

//    public Product addProduct(Category category, Product product,
//                              List<InventoryDTO> inventories,
//                              Color color,
//                              String sellerArticle,
//                              List<MultipartFile> pictures,
//                              Dimensions dimensions)  {
//        User user = userService.getCurrentUser();
//        product.setUser(user);
//        product = categoryService.addCategoryToProduct(product,category);
//        product = productRepository.save(product);
//        dimensions.setProduct(product);
//        product.setDimensions(dimentsionsService.addDimensions(dimensions));
//        if (color!=null) {
//            product.addArticle(articleService.addArticle(inventories, pictures, color,sellerArticle, product));
//        }
//        else{
//            product.addArticle(articleService.addArticle(inventories,pictures,sellerArticle,product));
//        }
//        return product;
//    }
//    public Product addProduct(Product product,
//                              List<InventoryDTO> inventories,
//                              Color color,
//                              String sellerArticle,
//                              List<MultipartFile> pictures,
//                              Dimensions dimensions,
//                              List<Tag> tags,
//                              Category category,
//                              ProductGender productGender,
//                              Discount discount){
//        product = addProduct(category,product,inventories,color,sellerArticle,pictures,dimensions);
//        product = productRepository.save(product);
//        if(tags!=null || tags.size()!=0){
//            tagService.addTagToProduct(product,tags);
//        }
//
//        if(!discount.getPercentage().equals(0)) {
//            for (Article article : product.getArticles()) {
//                discountService.save(article, discount);
//            }
//        }
//        productGender = productGenderService.getProductGender(productGender);
//        if (productGender!=null) {
//            product.setProductGender(productGender);
//            productRepository.save(product);
//        }
//        return product;
//    }
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
        System.out.println("inside product update 22");
        System.out.println(product.getName());
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()){
            Product presentProduct = optionalProduct.get();
            presentProduct.setName(product.getName());
            presentProduct.setDescription(product.getDescription());
            presentProduct.setBrand(product.getBrand());
            productGenderService.setGenderToProduct(presentProduct,product.getProductGender());
            tagService.updateProductTags(presentProduct,product.getTags());
            presentProduct.setDimensions(dimentsionsService.updateDimensions(product.getDimensions(),presentProduct));
            return presentProduct;
        }
        else {
            return product;
        }

    }
    public Product addArticleWithPicture(Long id,Article article,List<MultipartFile> pictures){
        Optional<Product> optionalProduct =  productRepository.findById(id);
        if(optionalProduct.isPresent() && optionalProduct.get().getUser().getId() == userService.getCurrentUser().getId()){
            Product product = optionalProduct.get();
            product.addArticle(articleService.createArticleWithPicture(article,pictures,product));
            return product;
        }else {
            return null;
        }
    }
    public Product addArticleWithoutPicture(Long id,Article article){
        Optional<Product> optionalProduct =  productRepository.findById(id);
        if(optionalProduct.isPresent() && optionalProduct.get().getUser().getId() == userService.getCurrentUser().getId()){
            Product product = optionalProduct.get();
            product.addArticle(articleService.createArticleWithoutPicture(article,product));
            return product;
        }else {
            return null;
        }
    }


    public Product updateProduct(Long productId,
                                    Long articleId,
                                    Category category,
                                 Product product,
                              List<InventoryDTO> inventories,
                              Color color,
                              String sellerArticle,
                              List<MultipartFile> pictures,
                                 List<String> leftoverPictures,
                              Dimensions dimensions)  {
        Product oldProduct = productRepository.getById(productId);
        oldProduct.setName(product.getName());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setBrand(product.getBrand());
        product = oldProduct;
        product = categoryService.updateCategoryProduct(product,category);
        product = productRepository.save(product);
        dimensions.setProduct(product);
        product.setDimensions(dimentsionsService.updateDimensions(dimensions,product));
        if (color!=null) {
            articleService.updateArticle(articleId,inventories, pictures,leftoverPictures, color,sellerArticle, product);
        }
        else{
            articleService.updateArticle(articleId,inventories,pictures,leftoverPictures,sellerArticle,product);
        }
        return product;
    }
    public Product updateProduct(Long productId,
                                 Long articleId,
                                 Category category,
                                 Product product,
                                 List<InventoryDTO> inventories,
                                 Color color,
                                 String sellerArticle,
                                 List<MultipartFile> pictures,
                                 List<String> leftoverPictures,
                                 Dimensions dimensions,
                                 List<Tag> tags,
                                 ProductGender productGender,
                                 Discount discount){
        System.out.println("product update");
        product = updateProduct(productId,articleId,category,product,inventories,color,sellerArticle,pictures,leftoverPictures,dimensions);
        product = productRepository.save(product);
        if(tags!=null || tags.size()!=0){
            tagService.updateTagProduct(product,tags);
        }
//        if(!discount.getPercentage().equals(0)) {
//            for (Article article : product.getArticles()) {
//                discountService.
//            }
//        }
        productGender = productGenderService.getProductGender(productGender);
        if (productGender!=null) {
            product.setProductGender(productGender);
            productRepository.save(product);
        }
        return product;
    }

//    public Product addArticleToProduct(List<InventoryDTO> inventories,
//                                       Color color,
//                                       String sellerArticle,
//                                       List<MultipartFile> pictures,
//                                       Long id){
////        System.out.println(id);
//        User user = userService.getCurrentUser();
//        Optional<Product> optionalProduct = productRepository.findById(id);
//
//        if(!optionalProduct.isPresent()){
//            throw new RuntimeException("this product does not exist");
//        }
//        if(!optionalProduct.get().getUser().equals(user)){
//            throw new RuntimeException("You are not the owner of this product");
//        }
//        optionalProduct.get().addArticle(articleService.addArticle(inventories,pictures,color,sellerArticle,optionalProduct.get()));
//        return optionalProduct.get();
//    }


    //@Override
    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }
    public List<Product> findByName(String name,Integer page,Integer amount){
        return productRepository.findByNameSimilar(name,PageRequest.of(page,amount));
    }


    public Page<Product> listOfProducts(Integer page,Integer amount) {
        return productRepository.findAll(PageRequest.of(page,amount));
    }

    //@Override
    public void removeProduct(Long id) {
        productRepository.deleteById(id);
    }

    //@Override
    public Product editProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> productsByUser(Integer page,Integer amount){
        User user = userService.getCurrentUser();
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

        User user = userService.getCurrentUser();
        return productRepository.getByResentVisit(user.getId(),PageRequest.of(page,amount));
    }
    public List<Product> productsByTag(Integer page,Integer amount){
        List<Visit> visits = visitService.getAllVisits();
        Set<Long> tag_Ids = new HashSet<>();
        Set<Long> visitedProductsId = new HashSet<>();
        for (Visit visit:visits){
            visitedProductsId.add(visit.getArticle().getId());
            for(Tag tag:visit.getArticle().getProduct().getTags()) {
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
}
