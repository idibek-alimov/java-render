package maryam.service.article;

import lombok.RequiredArgsConstructor;
import maryam.data.product.ArticleRepository;

import maryam.data.product.ProductRepository;
import maryam.data.user.UserRepository;
import maryam.models.inventory.Inventory;
import maryam.models.product.*;
import maryam.models.user.User;
import maryam.service.color.ColorService;
import maryam.service.inventory.InventoryService;
import maryam.service.picture.PictureService;
import maryam.service.product.BrandService;
import maryam.service.product.ProductService;
import maryam.service.search.SearchItemService;
import maryam.service.user.UserService;
import maryam.service.visit.VisitService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor @Transactional
public class ArticleService {
    private final ProductRepository productRepository;
    private final ArticleRepository articleRepository;
    private final InventoryService inventoryService;
    private final PictureService pictureService;
    private final ColorService colorService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final VisitService visitService;
    private final DiscountService discountService;
    private final ProductService productService;
    private final BrandService brandService;


    public Boolean filterAvailable(Article article){
        boolean available = false;
        for(Inventory inventory:article.getInventory()){
            if (inventory.getInStock()) {
                available = true;
                break;
            }
        }
        return available;
    }
    public  Page<Article> getArticlesByPage(Integer page,Integer amount){
        return new PageImpl<Article>( articleRepository.findAll(PageRequest.of(page,amount, Sort.by("createdAt").descending()))
                .stream()
                .filter(this::filterAvailable).collect(Collectors.toList()));
    }
    public Product addArticleWithPicture(Long id,Article article,List<MultipartFile> pictures){
        Optional<Product> optionalProduct =  productRepository.findById(id);
        if(optionalProduct.isPresent() && optionalProduct.get().getUser().getId() == userService.getCurrentUser().getId()){
            Product product = optionalProduct.get();
            product.addArticle(createArticleWithPicture(article,pictures,product));
            return product;
        }else {
            return null;
        }
    }
    public Product addArticleWithoutPicture(Long id,Article article){
        Product product = productService.getProductIfOwner(id);
        if(product!=null){
            product.addArticle(createArticleWithoutPicture(article,product));
            return product;
        }else {
            return null;
        }
    }
    public Article createArticleWithoutPicture(Article article, Product product){
        Article newArticle = articleRepository.save(new Article()
                .builder()
                        .product(product)
                        .sellerArticle(article.getSellerArticle())
                .build());
        newArticle.setInventory(inventoryService.createInventories(article.getInventory(),newArticle));
        if(article.getDiscounts()!=null && article.getDiscounts().size()!=0 && article.getDiscounts().get(0).getPercentage()!=0){
           discountService.addDiscount(newArticle,article.getDiscounts().get(0).getPercentage());
        }
        colorService.addColor(article.getColor(),newArticle);
        newArticle = articleRepository.save(newArticle);
        newArticle.setStatus(Article.Status.NoPicture);
        return newArticle;
    }
    public Article createArticleWithPicture(Article article, List<MultipartFile> pictures, Product product){
        Article createdArticle = createArticleWithoutPicture(article,product);
        createdArticle = articleRepository.save(createdArticle);
        createdArticle.setPictures(pictureService.addPictures(pictures,createdArticle));
        createdArticle.setStatus(Article.Status.Active);
        return createdArticle;
    }

    public Article updateArticleWithoutPicture(Long articleId,Article article,List<Long> picIdList){
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if(optionalArticle.isPresent()) {
            Article presentArticle = optionalArticle.get();
            presentArticle.setSellerArticle(article.getSellerArticle());
            if(article.getDiscounts()!=null && article.getDiscounts().get(0).getPercentage()!=0){
                discountService.addDiscount(presentArticle,article.getDiscounts().get(article.getDiscounts().size()-1).getPercentage());
            }
            if (presentArticle.getColor()!= null && presentArticle.getColor().getName()!=article.getColor().getName())
                colorService.addColor(article.getColor(),presentArticle);
            pictureService.removePicturesFromArticle(picIdList,presentArticle);
            inventoryService.updateInventories(article.getInventory(),presentArticle);
            return presentArticle;
        }
        else {
            return article;
        }
    }
    public Article updateArticle(Long articleId,Article article,List<Long> picIdList,List<MultipartFile> pictures){
        Article updatedArticle = updateArticleWithoutPicture(articleId,article,picIdList);
        pictureService.addPictures(pictures,updatedArticle);
        updatedArticle.setStatus(Article.Status.Active);
        return  updatedArticle;
    }

    public Optional<Article> getArticle(Long id){
        Article article = articleRepository.findById(id).get();
        visitService.addVisit(article);
        return Optional.of(article);
    }
    public Article getArticleById(Long id){
        return articleRepository.findById(id).get();
    }
//    public Article getArticleDto(Long id){
//        return getArticle(id).get();
//    }
    public List<Article> getArticlesInProduct(Long id){
        return articleRepository.findByProductId(id);
    }
    public List<Article> getArticlesInProductByCategory(Long id)throws Exception{
        List<Article> articlesByProductCategory = articleRepository.findArticlesByProductCategory(productService.getProduct(id).getCategory().getId());
        articlesByProductCategory = articlesByProductCategory.stream().filter(article -> article.getProduct().getId() !=id).collect(Collectors.toList());
        return articlesByProductCategory;
    }
    public List<Article> getArticlesByBrand(String name){
        Brand brand = brandService.findBrand(name);
        return articleRepository.getArticlesByBrand(brand.getId());
    }
    private final SearchItemService searchItemService;
    public List<Article> searchByName(String searchText,Integer page,Integer amount){
        searchItemService.addSearchItem(searchText);
        return articleRepository.findBySimilarName(searchText,PageRequest.of(page,amount));
    }
    public List<Article> searchSpecificName(String name,Integer page,Integer amount){
        return articleRepository.getByName(name,PageRequest.of(page,amount));
    }
    public List<Article> getByUser(){
        return articleRepository.getByUserAndPresentableTrue(userService.getCurrentUser().getId());
    }
    public List<Article> getByUserNoPicture(){
        return articleRepository.getByUserAndPresentableFalse(userService.getCurrentUser().getId());
    }
    public List<Article> getByUserRemovableTrue(){
        return articleRepository.getByUserAndRemovableTrue(userService.getCurrentUser().getId());
    }

    public void deleteArticle(Long id) throws Exception {
        try {
            Article article = articleRepository.getById(id);
            article.setStatus(Article.Status.Removable);
        } catch (Exception exception) {
            throw new RuntimeException("fuck you " + exception);
        }
    }
    public void recoverArticle(Long id) throws Exception {
        try {
            Article article = articleRepository.getById(id);
            if (article.getPictures().size()==0){
                article.setStatus(Article.Status.NoPicture);
            }
            else {
                article.setStatus(Article.Status.Active);
            }
        } catch (Exception exception) {
            throw new RuntimeException("fuck you" + exception);
        }
    }
    public List<Article> getLikedArticle() throws Exception{
        try {
            User user = userService.getCurrentUser();
            return articleRepository.getLikedArticles(user.getId());
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
