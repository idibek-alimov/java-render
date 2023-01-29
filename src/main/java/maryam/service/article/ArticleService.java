package maryam.service.article;

import lombok.RequiredArgsConstructor;
import maryam.data.like.LikeRepository;
import maryam.data.order.ItemRepository;
import maryam.data.picture.PictureRepository;
import maryam.data.product.ArticleRepository;
import maryam.data.product.ProductRepository;
import maryam.data.user.UserRepository;
import maryam.models.inventory.Inventory;
import maryam.models.like.Like;
import maryam.models.product.Article;
import maryam.models.product.Color;
import maryam.models.product.Product;
import maryam.models.tag.Tag;
import maryam.models.user.User;
import maryam.service.color.ColorService;
import maryam.service.inventory.InventoryService;
import maryam.service.picture.PictureService;
import maryam.service.product.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor @Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final InventoryService inventoryService;
    private final PictureService pictureService;
    private final ColorService colorService;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;
    //private final ProductService productService;
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
    }
    public Article addArticle(List<Inventory> inventories, Color color, Product product){
        Article article = articleRepository.save(new Article(product));
        article.setInventory(inventoryService.addInventories(inventories,article));
        article.setColor(colorService.addColor(color,article));
        return article;
    }

    public Page<Article> getListOfArticles(Integer page, Integer amount){

        return articleRepository.findAll(PageRequest.of(page,amount));
    }
    public Page<Article> getListOfSimilarArticles(Long id,Integer page, Integer amount){
        List<Tag> tags = new ArrayList<>();
        Article article = articleRepository.getById(id);
        for(Tag tag:article.getProduct().getTags()){
            tags.add(tag);
        }
        return articleRepository.findSimilarArticles(tags,id,PageRequest.of(page,amount));
    }

    public Optional<Article> getArticle(Long id){
        return articleRepository.findById(id);
    }
    public List<Article> getArticlesInProduct(Long id){
        return articleRepository.findByProductId(id);
    }

    public List<Article> searchByName(String searchText,Integer page,Integer amount){
        return articleRepository.findBySimilarName(searchText,PageRequest.of(page,amount));
    }
    public List<Article> getByUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user= userRepository.findByUsername(username);
        return articleRepository.getByUser(user.getId());
    }
    public List<Article> getByUserLiked(){
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user= userRepository.findByUsername(username);
        return articleRepository.getByUserLiked(user.getId());
    }
    public void deleteArticle(Long id) throws Exception{
        try{
            Article article = articleRepository.getById(id);
            Product product = article.getProduct();
            likeRepository.deleteAll(likeRepository.findByArticle(article));
            itemRepository.deleteAll(itemRepository.findByArticle(articleRepository.getById(id)));
            if(product.getArticles().size() == 1){
                for(int i=0;i<product.getTags().size();i++){
                    product.getTags().get(i).getProducts().remove(product);
                    product.getTags().remove(product.getTags().get(i));
                    System.out.println("123");
                }
                productRepository.delete(product);
                //productService.deleteProduct(product.getId());
            }
            else{
                System.out.println("432");
                System.out.println(id);
                System.out.println(articleRepository.getById(id).getProduct().getName());
                product.getArticles().remove(article);
                articleRepository.delete(article);
                System.out.println("deleted i guess");

            }

                //like.getArticle();


        }
        catch (Exception exception){
            throw new RuntimeException("fuck you "+exception);
        }
    }
}
