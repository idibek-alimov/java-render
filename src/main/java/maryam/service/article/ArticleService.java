package maryam.service.article;

import lombok.RequiredArgsConstructor;
import maryam.data.product.ArticleRepository;

import maryam.data.product.ProductRepository;
import maryam.data.user.UserRepository;
import maryam.dto.article.CustomerArticleDto;
import maryam.dto.inventory.InventoryDTO;
import maryam.models.picture.Picture;
import maryam.models.product.Article;
import maryam.models.product.Color;
import maryam.models.product.Discount;
import maryam.models.product.Product;
import maryam.models.tag.Tag;
import maryam.models.user.User;
import maryam.service.color.ColorService;
import maryam.service.inventory.InventoryService;
import maryam.service.like.LikeService;
import maryam.service.picture.PictureService;
import maryam.service.user.UserService;
import maryam.service.visit.VisitService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final LikeService likeService;

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
        Optional<Product> optionalProduct =  productRepository.findById(id);
        if(optionalProduct.isPresent() && optionalProduct.get().getUser().getId() == userService.getCurrentUser().getId()){
            Product product = optionalProduct.get();
            product.addArticle(createArticleWithoutPicture(article,product));
            return product;
        }else {
            return null;
        }
    }
    public Article createArticleWithoutPicture(Article article, Product product){
        Article createdArticle = articleRepository.save((new Article(product,article.getSellerArticle())));
        createdArticle = articleRepository.save(createdArticle);
        createdArticle.setInventory(inventoryService.createInventories(article.getInventory(),createdArticle));
        if(article.getDiscounts()!=null && article.getDiscounts().get(0).getPercentage()!=0){
           discountService.addDiscount(createdArticle,article.getDiscounts().get(0).getPercentage());
        }
        createdArticle = articleRepository.save(createdArticle);
        if(article.getColor()!=null){
            colorService.addColor(article.getColor(),createdArticle);
        }
        createdArticle.setStatus(Article.Status.NoPicture);
        return createdArticle;
    }
    public Article createArticleWithPicture(Article article, List<MultipartFile> pictures, Product product){
        Article createdArticle = createArticleWithoutPicture(article,product);
        createdArticle = articleRepository.save(createdArticle);
        createdArticle.setPictures(pictureService.addPictures(pictures,createdArticle));
        createdArticle.setStatus(Article.Status.Active);
        return createdArticle;
    }

    public Article updateArticle(Long articleId,List<InventoryDTO> inventories, List<MultipartFile> newPictures,List<String> leftoverPictures,String sellerArticle, Product product){
        Article article = articleRepository.getById(articleId);
        article.setSellerArticle(sellerArticle);
        pictureService.removePictures(leftoverPictures,article);
        article.setPictures(pictureService.addPictures(newPictures,article));
        articleRepository.save(article);
        article.setInventory(inventoryService.addInventories(inventories,article));
        return article;
    }
    public Article updateArticleWithoutPicture(Long articleId,Article article,List<Long> picIdList){
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if(optionalArticle.isPresent()) {
            Article presentArticle = optionalArticle.get();
            if(article.getDiscounts()!=null && article.getDiscounts().get(0).getPercentage()!=0){
                discountService.addDiscount(presentArticle,article.getDiscounts().get(article.getDiscounts().size()-1).getPercentage());
            }
            if (presentArticle.getColor().getName()!=article.getColor().getName())
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
        String user = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if(user!="anonymousUser"){
            //System.out.println("hello there . General Kenobe");
            visitService.addVisit(id);
        }
        return articleRepository.findById(id);
    }
    public CustomerArticleDto getArticleDto(Long id){
        return articleToDto(getArticle(id).get());
    }
    public List<Article> getArticlesInProduct(Long id){
        return articleRepository.findByProductId(id);
    }

    public List<Article> searchByName(String searchText,Integer page,Integer amount){
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
//            System.out.println("inside the service");
            User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
//            System.out.println("the fucking user is");
//            System.out.println(user);
            return articleRepository.getLikedArticles(user.getId());
        }
        catch (Exception e){
            throw new RuntimeException("Not authenticated exception ,bitch");
        }
    }
    public CustomerArticleDto articleToDto(Article article){
        CustomerArticleDto articleDto = new CustomerArticleDto()
                .builder()
                .id(article.getId())
                .productId(article.getProduct().getId())
                .likes(likeService.check_like(article.getId()))
                .name(article.getProduct().getName())
                .brand(article.getProduct().getBrand())
                .description(article.getProduct().getDescription())
                .category(article.getProduct().getCategory().getName())
                .build();
        if(article.getColor()!=null){
            articleDto.setColor(article.getColor().getName());
        }
        if(article.getDiscounts().size()!=0) {
            articleDto.setDiscount(article.getDiscounts().get(article.getDiscounts().size() - 1).getPercentage());
            articleDto.setDiscounts(article.getDiscounts().stream().map(Discount::getPercentage).collect(Collectors.toList()));
        }
        if(article.getProduct().getProductGender()!=null){
            articleDto.setGender(article.getProduct().getProductGender().getName());
        }
        if(article.getPictures()!=null && article.getPictures().size()!=0){
            articleDto.setPictures(article.getPictures().stream().map(Picture::getName).collect(Collectors.toList()));
        }
        articleDto.setInventories(article.getInventory().stream().map(inventory -> inventoryService.inventoryToCustomerDto(inventory,articleDto.getDiscount())).collect(Collectors.toList()));
        return articleDto;
    }
}
