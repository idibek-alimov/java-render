package maryam.controller.order;

import lombok.RequiredArgsConstructor;
//import maryam.models.inventory.Inventory;
//import maryam.models.order.Item;
import maryam.dto.order.DayOrderStatisticDTO;
import maryam.dto.order.WeekOrderClass;
import maryam.dto.order.WeekOrderStatisticDTO;
import maryam.models.order.Item;
import maryam.models.order.Order;
import maryam.service.order.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(path ="/add")
    public Long addNewOrder(@RequestBody List<ItemHolder> items) throws Exception{
        //System.out.println(items);
        Order order = orderService.addOrder(items);
        return order.getId();
    }
    @GetMapping(path="/queue")
    public List<Order> byUserInQueue(){
        return orderService.byUserInQueue();
    }
    @GetMapping(path="/shipping")
    public List<Order> byUserShipping(){
        return orderService.byUserShipping();
    }
    @GetMapping(path="/arrived")
    public List<Order> byUserArrived(){
        return orderService.byUserArrived();
    }
    @GetMapping(path="/delivered")
    public List<Order> byUserDelivered(){
        return orderService.byUserDelivered();
    }

    @GetMapping(path="/by/user")
    public List<Order> orderByUser(){
        return orderService.orderListByUser();
    }
    @GetMapping(path="/delivered/false")
    public List<Item> orderByUserNotDelivered(){
        return null; // orderService.listOfNotDeliveredOrders();
    }
    @GetMapping(path="/delivered/true")
    public List<Order> orderByUserDelivered(){

        return null; //orderService.listOfDeliveredOrders();
    }

    //@JsonView(View.OnlyId.class)
//    @PostMapping(path ="")
//    public Order addOrder(@RequestPart("items") List<ItemHolder> items){
//        System.out.println("came here");
//        return orderService.addOrder(items);
//    }

    @PutMapping("/set/shipping/{id}")
    public ResponseEntity<Order> checkoutOrder(@PathVariable("id") Long id){
        Order order = orderService.setOrderShipping(id);
        return ResponseEntity.ok(order);
    }
    @GetMapping(path="/set/arrived/{id}")
    public Order setArrivedOrder(@PathVariable("id")Long id){
        return orderService.setOrderArrived(id);
    }
    @GetMapping(path="/set/delivered/{id}")
    public Order setDeliveredOrder(@PathVariable("id")Long id){
        return orderService.setOrderDelivered(id);
    }

//    @GetMapping(path = "/{id}")
//    public Order checkout(@PathVariable("id") Long id){
//        return orderService.checkOutOrder(id);
//    }


    @GetMapping
    public List<Order> checkout(){
        return orderService.listOfOrders();
    }
    //@JsonView(ItemView.Base.class)
//    @JsonView(View.OnlyId.class)
    @GetMapping(path="/unchecked")
    public Iterable<Order> unchecked(){

        return null;// orderService.uncheckedOrders();
    }

    @GetMapping(path = "/owner/today/shipping")
    public List<DayOrderStatisticDTO> todayOrdersShipping(){
        return orderService.findTodayOrdersShipping();
    }
    @GetMapping(path = "/owner/week/shipping")
    public List<WeekOrderClass> weekOrdersShipping(){
        return orderService.findWeekOrdersShipping();
    }
    @GetMapping(path = "/owner/today/delivered")
    public List<DayOrderStatisticDTO> todayOrdersDelivered() {
        return orderService.findTodayOrdersDelivered();
    }
    @GetMapping(path = "/owner/week/delivered")
    public List<WeekOrderClass> weekOrdersDelivered(){
        return orderService.findWeekOrdersDelivered();
    }
    @DeleteMapping(path="/delete/{id}")
    public void deleteOrder(@PathVariable("id")Long id){
        orderService.removeOrder(id);
    }

}
