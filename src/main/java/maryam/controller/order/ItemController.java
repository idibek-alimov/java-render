package maryam.controller.order;

import lombok.RequiredArgsConstructor;
import maryam.controller.product.ArticleController;
import maryam.dto.article.CustomerArticleDto;
import maryam.dto.order.SellerItemDTO;
import maryam.models.order.Item;
import maryam.service.article.ArticleService;
import maryam.service.order.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final ArticleService articleService;
    private final ArticleController articleController;
    @GetMapping(path = "/user/{status}")
    public List<CustomerArticleDto> getByUserStatus(@PathVariable("status")Integer status){
        return itemService.byUserStatus(status).stream().map(item -> articleController.articleToDTO(articleService.getArticleById(item.getInventory().getArticle().getId()))).collect(Collectors.toList());
    }
    @GetMapping(path = "/user/delivery")
    public List<CustomerArticleDto> getDeliveries(){
        return itemService.getDeliveries().stream().map(item -> articleController.articleToDTO(articleService.getArticleById(item.getInventory().getArticle().getId()))).collect(Collectors.toList());
    }
    @GetMapping(path = "/seller/{status}")
    public List<SellerItemDTO> getByOwnerStatus(@PathVariable("status")String status){
        //"queue" | "shipping" | "arrived" | "delivered";
        switch (status){
            case "queue":return itemService.byOwnerStatus(0).stream().map(item -> itemToSellerItemDto(item)).collect(Collectors.toList());
            case "shipping":return itemService.byOwnerStatus(1).stream().map(item -> itemToSellerItemDto(item)).collect(Collectors.toList());
            case "arrived":return itemService.byOwnerStatus(2).stream().map(item -> itemToSellerItemDto(item)).collect(Collectors.toList());
            case "delivered":return itemService.byOwnerStatus(3).stream().map(item -> itemToSellerItemDto(item)).collect(Collectors.toList());
        }
        return null;
        //return itemService.byOwnerStatus(status).stream().map(item -> itemToSellerItemDto(item)).collect(Collectors.toList());
    }
    @GetMapping(path = "/manager/set/status/{itemId}/{status}")
    public Item setStatus(@PathVariable("status")Integer status,@PathVariable("itemId") Long itemId) throws Exception{
        switch (status){
            case 0:return itemService.setStatus(itemId, Item.Status.InQueue);
            case 1:return itemService.setStatus(itemId, Item.Status.Shipping);
            case 2:return itemService.setStatus(itemId, Item.Status.Arrived);
            case 3:return itemService.setStatus(itemId, Item.Status.Delivered);
        }
        return null;//itemService.byOwnerStatus(status);
    }
    @GetMapping(path = "/owner/{timeIndex}/{timeValue}/{status}")
    public List<Item> getByOwnerStatusAndTimeInterval(@PathVariable("timeIndex")Integer timeIndex,
                                                      @PathVariable("timeValue")String timeValue,
                                                      @PathVariable("status")Integer status){
        switch (timeValue){
            case "day":return itemService.byOwnerStatusAndTimeInterval(status,timeIndex, ItemService.TimeValue.Day);
            case "week":return itemService.byOwnerStatusAndTimeInterval(status,timeIndex, ItemService.TimeValue.Week);
            case "month":return itemService.byOwnerStatusAndTimeInterval(status,timeIndex,ItemService.TimeValue.Month);
            case "year":return itemService.byOwnerStatusAndTimeInterval(status,timeIndex, ItemService.TimeValue.Year);
        }
        return itemService.byOwnerStatus(status);
    }

    public SellerItemDTO itemToSellerItemDto(Item item){
        return new SellerItemDTO()
                .builder()
                .id(item.getId())
                .price(item.getPrice())
                .name(item.getInventory().getArticle().getProduct().getName())
                .brand(item.getInventory().getArticle().getProduct().getBrand())
                .sellerArticle(item.getInventory().getArticle().getSellerArticle())
                .createdAt(item.getCreated_at().toString())
                .pictures(item.getInventory().getArticle().getPictures().stream().map(picture -> picture.getName()).collect(Collectors.toList()))
                .size(item.getInventory().getSize())
                .status(item.getStatus().name())
                .build();
    }
}
