package maryam.controller.product;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import maryam.dto.article.ArticleCreateDTO;
import maryam.dto.article.ArticleUpdateDTO;
import maryam.dto.article.CustomerArticleDto;
import maryam.dto.inventory.CustomerInventoryDto;
import maryam.dto.inventory.InventoryCreateDTO;
import maryam.models.inventory.Inventory;
import maryam.models.product.Article;
import maryam.models.product.Color;
import maryam.models.product.Discount;
import maryam.service.article.ArticleService;
import maryam.service.like.LikeService;
import maryam.storage.FileStorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final LikeService likeService;
    private final FileStorageService fileStorageService;
    @GetMapping(path="/allow/{page}/{amount}")
    public ResponseEntity<List<CustomerArticleDto>> getListOfArticlesDto(@PathVariable("page")Integer page,
                                                                        @PathVariable("amount")Integer amount) {
            return ResponseEntity.ok(articleService.getArticlesByPage(page, amount).stream().map(this::articleToDTO).collect(Collectors.toList()));//(page,amount);
    }
    @GetMapping(path="/allow/colors/{id}")
    public List<CustomerArticleDto> getListOfArticlesInProduct(@PathVariable("id")Long id){
        return articleService.getArticlesInProduct(id).stream().map(this::articleToDTO).collect(Collectors.toList());
    }
    @GetMapping(path = "/allow/category/{id}")
    public List<CustomerArticleDto> getListOfArticlesByCategory(@PathVariable("id")Long id) throws Exception{
        return articleService.getArticlesInProductByCategory(id).stream().map(this::articleToDTO).collect(Collectors.toList());
    }
    @GetMapping(path="/seller/colors/{id}")
    public List<ArticleUpdateDTO> getArticleColors(@PathVariable("id")Long id){
        return articleService.getArticlesInProduct(id).stream().map(this::articleToUpdateDTO).collect(Collectors.toList());
    }
    @GetMapping(path="/allow/{id}")
    public CustomerArticleDto getArticleDto(@PathVariable("id")Long id){
        return articleToDTO(articleService.getArticle(id).get());
    }
    @PostMapping(path="/seller/add/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long addArticleToProduct(@RequestPart("article")ArticleCreateDTO article,
                                       @RequestPart("pictures")List<MultipartFile> pictures,
                                       @PathVariable("id")Long productId){
       return articleService.addArticleWithPicture(productId,DtoToArticle(article),pictures).getId();
    }
    @PostMapping(path="/seller/add/picture/none/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long addArticleWithoutPic(@RequestPart("article") ArticleCreateDTO article,
                                        @PathVariable("id")Long id){
//        System.out.println(article);
        return articleService.addArticleWithoutPicture(id,DtoToArticle(article)).getId();
    }
    @PostMapping(path="/seller/update/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long updateArticle(@PathVariable("id")Long id,
                                 @RequestPart("article")ArticleCreateDTO article,
                                 @RequestPart("pictures")List<MultipartFile> pictures,
                                 @RequestPart("oldPics")List<Long> oldPicsId){
        System.out.println("Update article id="+id);
        return articleService.updateArticle(id,DtoToArticle(article),oldPicsId,pictures).getId();
    }
    @PostMapping(path="/seller/update/picture/none/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long updateArticleWithoutPicture(@PathVariable("id")Long id,
                                 @RequestPart("article")ArticleCreateDTO article,
                                 @RequestPart("oldPics")List<Long> oldPicsId){
        System.out.println("inside article update without picture");
        System.out.println("Update article id="+id);
        return articleService.updateArticleWithoutPicture(id,DtoToArticle(article),oldPicsId).getId();
    }
    @GetMapping(path="/allow/search/{searchText}/{page}/{amount}")
    public List<CustomerArticleDto> searchArticle(@PathVariable("searchText")String searchText,
                                       @PathVariable("page")Integer page,
                                       @PathVariable("amount")Integer amount){
        System.out.println("inside search");
        System.out.println(searchText);
            try {
                List<Article> articleList = new ArrayList<>();
                Long article_id = Long.parseLong(searchText);
                articleList.add(articleService.getArticle(article_id).get());
                return articleList.stream().map(this::articleToDTO).collect(Collectors.toList());
            }
            catch (Exception nfe){
                return articleService.searchByName(searchText,page,amount).stream().map(this::articleToDTO).collect(Collectors.toList());
            }
    }
    @GetMapping(path="/name/{name}/{page}/{amount}")
    public List<CustomerArticleDto> getListByName(@PathVariable("name")String name,
                                                  @PathVariable("page")Integer page,
                                                  @PathVariable("amount")Integer amount){
        return articleService.searchSpecificName(name,page,amount).stream().map(article -> articleToDTO(article)).collect(Collectors.toList());
    }
<<<<<<< HEAD
    @GetMapping(path="/byuser")
    public List<Article> getListByUser(){
        System.out.println("1");
        return articleService.getByUser();
    }
    @DeleteMapping(path = "/delete/{id}")
=======
    @GetMapping(path="/seller/presentable/true")
    public List<CustomerArticleDto> getListByUser(){
        return articleService.getByUser().stream().map(article -> articleToDTO(article)).collect(Collectors.toList());
    }
    @GetMapping(path="/seller/presentable/false")
    public List<CustomerArticleDto> getListByUserNoPicture(){
        return articleService.getByUserNoPicture().stream().map(article -> articleToDTO(article)).collect(Collectors.toList());
    }

    @GetMapping(path="/seller/removable/true")
    public List<CustomerArticleDto> getListByUserRemovable(){
    return articleService.getByUserRemovableTrue().stream().map(article -> articleToDTO(article)).collect(Collectors.toList());
}

    @GetMapping(path = "/seller/delete/{id}")
>>>>>>> testings
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
    @GetMapping(path="/user/liked")
    public List<CustomerArticleDto> listOfLikedArticles() throws  Exception{
        return articleService.getLikedArticle().stream().map(this::articleToDTO).collect(Collectors.toList());
    }

    public CustomerArticleDto articleToDTO(Article article){
        //TO do
        //.discount(article.getDiscounts().get(article.getDiscounts().size()-1).getPercentage())
        CustomerArticleDto customerArticleDto = new CustomerArticleDto()
                .builder()
                .id(article.getId())
                .productId(article.getProduct().getId())
                .likes(likeService.check_like(article.getId()))
                .discounts(article.getDiscounts().stream().map(discount -> discount.getPercentage()).collect(Collectors.toList()))
                .name(article.getProduct().getName())
                .brand(article.getProduct().getBrand())
                .description(article.getProduct().getDescription())
                .category(article.getProduct().getCategory().getName())
                .pictures(article.getPictures().stream().map(picture -> {
                    try {
                        return fileStorageService.load(picture.getName()).getFile().getAbsolutePath();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList()))
//                .inventories(article.getInventory().stream().map(inventory -> new CustomerInventoryDto().builder()
//                        .id(inventory.getId())
//                        .price(inventory.getPrice())
//                        .price(inventory.getPrice())
//                        .inStock(inventory.getInStock())
//                        .size(inventory.getSize())
//                        .build()).collect(Collectors.toList()))
                .inventories(article.getInventory().stream().map(inventory -> inventoryToDto(inventory,article.getDiscounts())).collect(Collectors.toList()))
                .build();
        if(article.getDiscounts().size()>0){
            customerArticleDto.setDiscount(article.getDiscounts().get(article.getDiscounts().size()-1).getPercentage());
        }
        if (article.getColor() != null){
             customerArticleDto.setColor(article.getColor().getName());
        }
        return customerArticleDto;
    }
    public CustomerInventoryDto inventoryToDto(Inventory inventory,List<Discount> discounts){
        CustomerInventoryDto inventoryDto = new CustomerInventoryDto()
                .builder()
                .id(inventory.getId())
                .price(inventory.getPrice())
                .inStock(inventory.getInStock())
                .size(inventory.getSize())
                .build();
        if(discounts.size()!=0 && discounts.get(0)!=null && discounts.get(0).getPercentage()!=0){
            inventoryDto.setOriginalPrice(inventory.getPrice());
            Double price = (inventoryDto.getPrice()*(100-discounts.get(0).getPercentage()))/100;
            inventoryDto.setPrice(price);
        }
        else {
            inventoryDto.setPrice(inventoryDto.getPrice());
        }
        return inventoryDto;
    }
    public Article DtoToArticle(ArticleCreateDTO articleCreateDTO){
        return new Article()
                .builder()
                .color(new Color().builder().id(articleCreateDTO.getColor()).build())
                .sellerArticle(articleCreateDTO.getSellerArticle())
                .inventory(articleCreateDTO.getInventory().stream().map(inventoryCreateDTO -> inventoryCreateDTO.toInventory()).collect(Collectors.toList()))
                .build();
    }
    public ArticleUpdateDTO articleToUpdateDTO(Article article){
        ArticleUpdateDTO articleUpdateDTO =  new ArticleUpdateDTO()
                .builder()
                .sellerArticle(article.getSellerArticle())
                .id(article.getId())
                .color(article.getColor())
                .pictures(article.getPictures())
                .build();
        List<InventoryCreateDTO> availableInventories = new ArrayList<>();
        for (Inventory inventory:article.getInventory()){
            if (inventory.getAvailable()){
                availableInventories.add(
                        new InventoryCreateDTO()
                        .builder()
                        .id(inventory.getId())
                        .quantity(inventory.getQuantity())
                        .size(inventory.getSize())
                        .price(inventory.getPrice())
                        .build()
                );
            }
        }
        articleUpdateDTO.setInventory(availableInventories);
    return articleUpdateDTO;
    }
}
