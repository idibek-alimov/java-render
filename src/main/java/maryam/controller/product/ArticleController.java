package maryam.controller.product;

import lombok.RequiredArgsConstructor;
import maryam.models.product.Article;
import maryam.service.article.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(path="/inproduct/{id}")
    public List<Article> getListOfArticlesInProduct(@PathVariable("id")Long id){
        return articleService.getArticlesInProduct(id);
    }
    @GetMapping(path="/{id}")
    public Optional<Article> getArticle(@PathVariable("id")Long id){
        return articleService.getArticle(id);
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
    @GetMapping(path="/byuser")
    public List<Article> getListByUser(){
        return articleService.getByUser();
    }
//    @GetMapping(path="/liked")
//    public List<Article> getListByUserLiked(){
//        return articleService.getByUserLiked();
//    }
    @GetMapping(path="/topvisit/{limit}")
    public Set<Article> getMostVisitedArticles(@PathVariable("limit") Integer limit){
        return articleService.getMostVisitedArticles(limit);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteArticle(@PathVariable("id")Long id) throws Exception{
        articleService.deleteArticle(id);
    }
    @GetMapping(path="/liked")
    public List<Article> listOfLikedArticles() throws  Exception{
        return articleService.getLikedArticle();
    }
}
