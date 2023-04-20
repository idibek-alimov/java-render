package maryam.controller.order;

import lombok.RequiredArgsConstructor;
import maryam.models.order.Item;
import maryam.service.order.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    @GetMapping(path = "/user/{status}")
    public List<Item> getByUserStatus(@PathVariable("status")Integer status){
        return itemService.byUserStatus(status);
    }
    @GetMapping(path = "/owner/{status}")
    public List<Item> getByOwnerStatus(@PathVariable("status")Integer status){
        return itemService.byOwnerStatus(status);
    }
    @GetMapping(path = "/set/status/{itemId}/{status}")
    public Item setStatus(@PathVariable("status")Integer status,@PathVariable("itemId") Long itemId) throws Exception{
        System.out.println(status);
        System.out.println(itemId);
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


}
