package maryam.controller.order;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
//import maryam.models.inventory.Inventory;
//import maryam.models.order.Item;
import maryam.models.order.Item;
import maryam.models.order.Order;
import maryam.serializer.View;
import maryam.service.order.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping(path="/byuser")
    public List<Order> orderByUser(){
        return orderService.orderListByUser();
    }
    @GetMapping(path="/notdelivered")
    public List<Item> orderByUserNotDelivered(){
        return orderService.listOfNotDeliveredOrders();
    }
    @GetMapping(path="/delivered")
    public List<Order> orderByUserDelivered(){
        return orderService.listOfDeliveredOrders();
    }

    @GetMapping(path="/setdelivered/{id}")
    public Order setDeliveredOrder(@PathVariable("id")Long id){
        return orderService.setDeliveredOrder(id);
    }
    //@JsonView(View.OnlyId.class)
    @PostMapping(path ="")
    public Order addOrder(@RequestPart("items") List<ItemHolder> items){
        System.out.println("came here");
        return orderService.addOrder(items);
    }
    @PostMapping(path ="/add")
    public Long addNewOrder(@RequestBody List<ItemHolder> items){
        System.out.println("came here");
        System.out.println(items);
        Order order = orderService.addOrder(items);
        System.out.println(order.getId());
//        System.out.println(order.getItems().get(0));
        return order.getId();
    }

    @PutMapping("/checkout/{id}")
    public ResponseEntity<Order> checkoutOrder(@PathVariable("id") Long id){
        Order order = orderService.checkOutOrder(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping(path = "/{id}")
    public Order checkout(@PathVariable("id") Long id){
        return orderService.checkOutOrder(id);
    }


    @GetMapping
    public List<Order> checkout(){
        return orderService.listOfOrders();
    }
    //@JsonView(ItemView.Base.class)
//    @JsonView(View.OnlyId.class)
    @GetMapping(path="/unchecked")
    public Iterable<Order> unchecked(){
        return orderService.uncheckedOrders();
    }


    @DeleteMapping(path="/delete/{id}")
    public void deleteOrder(@PathVariable("id")Long id){
        orderService.removeOrder(id);
    }

}
