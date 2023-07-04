package maryam.service.article;

import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import maryam.data.like.LikeRepository;
import maryam.data.order.ItemRepository;
import maryam.data.picture.PictureRepository;
=======
>>>>>>> testings
import maryam.data.product.ArticleRepository;

import maryam.data.product.ProductRepository;
import maryam.data.user.UserRepository;
<<<<<<< HEAD
import maryam.models.inventory.Inventory;
=======
import maryam.dto.article.CustomerArticleDto;
import maryam.dto.inventory.InventoryDTO;
import maryam.models.picture.Picture;
>>>>>>> testings
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
import maryam.service.product.ProductService;
import maryam.service.user.UserService;
import maryam.service.visit.VisitService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

<<<<<<< HEAD
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
=======
import jakarta.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
>>>>>>> testings

@Service
@RequiredArgsConstructor @Transactional
public class ArticleService {
    private final ProductRepository productRepository;
    private final ArticleRepository articleRepository;
    private final InventoryService inventoryService;
    private final PictureService pictureService;
    private final ColorService colorService;
    private final UserRepository userRepository;
<<<<<<< HEAD
    private final LikeRepository likeRepository;
    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;
    public Article addArticle(List<Inventory> inventories, List<MultipartFile> pictures, Color color, Product product){
        System.out.println("article");
        Article article = articleRepository.save(new Article(product));
        System.out.println("setpics");
        article.setPictures(pictureService.addPictures(pictures,article));
        System.out.println("setinventory");
        article.setInventory(inventoryService.addInventories(inventories,article));
        System.out.println("before color thing");
        article.setColor(colorService.addColor(color,article));
        System.out.println("before retuning article 112232");
        return article;
=======
    private final UserService userService;
    private final VisitService visitService;
    private final DiscountService discountService;
    private final ProductService productService;

    public  Page<Article> getArticlesByPage(Integer page,Integer amount){
        return articleRepository.findAll(PageRequest.of(page,amount, Sort.by("createdAt").descending()));
>>>>>>> testings
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

    public List<Article> searchByName(String searchText,Integer page,Integer amount){
        return articleRepository.findBySimilarName(searchText,PageRequest.of(page,amount));
    }
<<<<<<< HEAD
    public List<Article> getByUser(){
        System.out.println("2");
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        System.out.println("3");
        User user= userRepository.findByUsername(username);
        System.out.println("4");
        return articleRepository.getByUser(user.getId());
    }
=======
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
>>>>>>> testings

    public void deleteArticle(Long id) throws Exception {
        try {
            Article article = articleRepository.getById(id);
<<<<<<< HEAD
            Product product = article.getProduct();
            likeRepository.deleteAll(likeRepository.findByArticle(article));
            itemRepository.deleteAll(itemRepository.findByArticle(articleRepository.getById(id)));
            if (product.getArticles().size() == 1) {
                for (int i = 0; i < product.getTags().size(); i++) {
                    product.getTags().get(i).getProducts().remove(product);
                    product.getTags().remove(product.getTags().get(i));
                    System.out.println("123");
                }
                productRepository.delete(product);
                //productService.deleteProduct(product.getId());
            } else {
                System.out.println("432");
                System.out.println(id);
                System.out.println(articleRepository.getById(id).getProduct().getName());
                product.getArticles().remove(article);
                articleRepository.delete(article);
                System.out.println("deleted i guess");

            }

            //like.getArticle();


        } catch (Exception exception) {
            throw new RuntimeException("fuck you " + exception);
=======
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
>>>>>>> testings
        }
    }
}
