package maryam.controller.product;

import lombok.RequiredArgsConstructor;
import maryam.dto.article.CustomerArticleDto;
import maryam.models.product.Article;
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
    public Page<Article> getListOfArticles(@PathVariable("page")Integer page,
                                           @PathVariable("amount")Integer amount){
        return articleService.getListOfArticles(page,amount);
    }
    @GetMapping(path="/dto/{page}/{amount}")
    public List<CustomerArticleDto> getListOfArticlesDto(@PathVariable("page")Integer page,
                                                         @PathVariable("amount")Integer amount){
        return articleService.getListOfArticlesDto(page,amount);
    }
    @GetMapping(path="/inproduct/{id}")
    public List<Article> getListOfArticlesInProduct(@PathVariable("id")Long id){
        return articleService.getArticlesInProduct(id);
    }
    @GetMapping(path="/{id}")
    public Optional<Article> getArticle(@PathVariable("id")Long id){
        return articleService.getArticle(id);
    }
    @GetMapping(path="/dto/{id}")
    public CustomerArticleDto getArticleDto(@PathVariable("id")Long id){
        return articleService.getArticleDto(id);
    }
    @PostMapping(path="/update/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Article updateArticle(@PathVariable("id")Long id,
                                 @RequestPart("article")Article article,
                                 @RequestPart("pictures")List<MultipartFile> pictures,
                                 @RequestPart("oldPics")List<Long> oldPicsId){
//        System.out.println("hello jovid");
        return articleService.updateArticle(id,article,oldPicsId,pictures);
    }
    @PostMapping(path="/update/picture/none/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Article updateArticleWithoutPicture(@PathVariable("id")Long id,
                                 @RequestPart("article")Article article,
                                 //@RequestPart("pictures")List<MultipartFile> pictures,
                                 @RequestPart("oldPics")List<Long> oldPicsId){
//        System.out.println("hello jovid");
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
            catch (NumberFormatException nfe){
                return articleService.searchByName(searchText,page,amount);
            }

        //return articleService.searchByName(searchText,page,amount);
    }
    @GetMapping(path="/byname/{name}/{page}/{amount}")
    public List<Article> getListByName(@PathVariable("name")String name,
                                                  @PathVariable("page")Integer page,
                                                  @PathVariable("amount")Integer amount){
        //return articleService.getListOfSimilarArticles(id,page,amount);
        return articleService.searchSpecificName(name,page,amount);
    }

    @GetMapping(path="/similar/{id}/{page}/{amount}")
    public Page<Article> getListOfSimilarArticles(@PathVariable("id")Long id,
            @PathVariable("page")Integer page,
                                           @PathVariable("amount")Integer amount){
        return articleService.getListOfSimilarArticles(id,page,amount);
    }
    @GetMapping(path="/user/presentable/true")
    public List<Article> getListByUser(){
        return articleService.getByUser();
    }
    @GetMapping(path="/user/presentable/false")
    public List<Article> getListByUserNoPicture(){
        return articleService.getByUserNoPicture();
    }
//    @GetMapping(path="/liked")
//    public List<Article> getListByUserLiked(){
//        return articleService.getByUserLiked();
//    }
    @GetMapping(path="/user/removable/true")
    public List<Article> getListByUserRemovable(){
    return articleService.getByUserRemovableTrue();
}
    @GetMapping(path="/topvisit/{limit}")
    public List<Article> getMostVisitedArticles(@PathVariable("limit") Integer limit){
        return articleService.getMostVisitedArticles(limit);
    }
    @GetMapping(path="/topvisit/recent/{limit}")
    public List<Article> getMostRecentVisitedArticles(@PathVariable("limit") Integer limit){
        return articleService.getMostRecentVisitedArticles(limit);
    }


    @GetMapping(path = "/delete/{id}")
    public void deleteArticle(@PathVariable("id")Long id) throws Exception{
        articleService.deleteArticle(id);
    }
    @DeleteMapping(path="/terminate/{id}")
    public void terminateArticle(@PathVariable("id")Long id) throws Exception{
        articleService.deleteArticle(id);
    }
    @DeleteMapping(path = "/recover/{id}")
    public void recoverArticle(@PathVariable("id")Long id) throws Exception{
        articleService.recoverArticle(id);
    }
    @GetMapping(path="/liked")
    public List<Article> listOfLikedArticles() throws  Exception{
        return articleService.getLikedArticle();
    }
//    @GetMapping(path ="/create/alot/{n}")
//    public void createALotOfArticles(@PathVariable("n")Integer n){
//        articleService.createNArticles(n);
//    }
}
