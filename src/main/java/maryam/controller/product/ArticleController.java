package maryam.controller.product;

import lombok.RequiredArgsConstructor;
import maryam.dto.article.CustomerArticleDto;
import maryam.models.product.Article;
import maryam.models.product.Product;
import maryam.service.article.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path="/api/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    @GetMapping(path="/{page}/{amount}")
    public List<CustomerArticleDto> getListOfArticlesDto(@PathVariable("page")Integer page,
                                                         @PathVariable("amount")Integer amount){
        return null;//articleService.getListOfArticlesDto(page,amount);
    }
    @GetMapping(path="/colors/{id}")
    public List<Article> getListOfArticlesInProduct(@PathVariable("id")Long id){
        return articleService.getArticlesInProduct(id);
    }
    @GetMapping(path="/{id}")
    public CustomerArticleDto getArticleDto(@PathVariable("id")Long id){
        return articleService.getArticleDto(id);
    }
    @PostMapping(path="/seller/add/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product addArticleToProduct(@RequestPart("article")Article article,
                                       @RequestPart("pictures")List<MultipartFile> pictures,
                                       @PathVariable("id")Long productId){
        return articleService.addArticleWithPicture(productId,article,pictures);
    }
    @PostMapping(path="/seller/add/picture/none/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product addArticleWithoutPic(@RequestPart("article")Article article,
                                        @PathVariable("id")Long id){
        return articleService.addArticleWithoutPicture(id,article);
    }
    @PostMapping(path="/seller/update/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Article updateArticle(@PathVariable("id")Long id,
                                 @RequestPart("article")Article article,
                                 @RequestPart("pictures")List<MultipartFile> pictures,
                                 @RequestPart("oldPics")List<Long> oldPicsId){
        return articleService.updateArticle(id,article,oldPicsId,pictures);
    }
    @PostMapping(path="/seller/update/picture/without/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Article updateArticleWithoutPicture(@PathVariable("id")Long id,
                                 @RequestPart("article")Article article,
                                 @RequestPart("oldPics")List<Long> oldPicsId){
        return articleService.updateArticleWithoutPicture(id,article,oldPicsId);
    }
    @GetMapping(path="/search/{searchText}/{page}/{amount}")
    public List<Article> searchArticle(@PathVariable("searchText")String searchText,
                                       @PathVariable("page")Integer page,
                                       @PathVariable("amount")Integer amount){
            try {
                List<Article> articleList = new ArrayList<>();
                Long article_id = Long.parseLong(searchText);
                articleList.add(articleService.getArticle(article_id).get());
                return articleList;
            }
            catch (Exception nfe){
                return articleService.searchByName(searchText,page,amount);
            }
    }
    @GetMapping(path="/name/{name}/{page}/{amount}")
    public List<Article> getListByName(@PathVariable("name")String name,
                                                  @PathVariable("page")Integer page,
                                                  @PathVariable("amount")Integer amount){
        return articleService.searchSpecificName(name,page,amount);
    }
    @GetMapping(path="/seller/presentable/true")
    public List<Article> getListByUser(){
        return articleService.getByUser();
    }
    @GetMapping(path="/seller/presentable/false")
    public List<Article> getListByUserNoPicture(){
        return articleService.getByUserNoPicture();
    }

    @GetMapping(path="/seller/removable/true")
    public List<Article> getListByUserRemovable(){
    return articleService.getByUserRemovableTrue();
}

    @GetMapping(path = "/seller/delete/{id}")
    public void deleteArticle(@PathVariable("id")Long id) throws Exception{
        articleService.deleteArticle(id);
    }
    @DeleteMapping(path="/seller/terminate/{id}")
    public void terminateArticle(@PathVariable("id")Long id) throws Exception{
        articleService.deleteArticle(id);
    }
    @DeleteMapping(path = "/seller/recover/{id}")
    public void recoverArticle(@PathVariable("id")Long id) throws Exception{
        articleService.recoverArticle(id);
    }
    @GetMapping(path="/liked")
    public List<Article> listOfLikedArticles() throws  Exception{
        return articleService.getLikedArticle();
    }
}
