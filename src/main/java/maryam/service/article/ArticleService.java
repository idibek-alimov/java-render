package maryam.service.article;

import lombok.RequiredArgsConstructor;
import maryam.data.picture.PictureRepository;
import maryam.data.product.ArticleRepository;
import maryam.data.product.ProductRepository;
import maryam.data.user.UserRepository;
import maryam.models.inventory.Inventory;
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
}
