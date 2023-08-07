package maryam.controller.order;

import lombok.RequiredArgsConstructor;
//import maryam.models.inventory.Inventory;
//import maryam.models.order.Item;
import maryam.dto.order.DayOrderStatisticDTO;
import maryam.dto.order.OrderCreateDto;
import maryam.dto.order.WeekOrderClass;
import maryam.dto.order.WeekOrderStatisticDTO;
import maryam.models.order.Item;
import maryam.models.order.Order;
import maryam.models.order.PickPoint;
import maryam.service.order.OrderService;
import maryam.service.order.PickPointService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path="/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final PickPointService pickPointService;

    @PostMapping(path = "/allow/pp/add",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public PickPoint addPickPoint(@RequestPart("pp") PickPoint pickPoint, @RequestPart("picture")List<MultipartFile> pictures){
        return pickPointService.createPickPoint(pickPoint,pictures);
    }
    @GetMapping(path = "/allow/pp/all")
    public List<PickPoint> listOfPickPoints(){
        return pickPointService.getAllPickPoints();
    }

    @PostMapping(path ="/add")
    public Long addNewOrder(@RequestBody OrderCreateDto orderCreateDto) throws Exception{
        Order order = orderService.addOrder(orderCreateDto);
        try {
            return order.getId();
        }
        catch (Exception e){
            throw new Exception(e);
        }
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

    @GetMapping(path = "/seller/today/shipping")
    public List<DayOrderStatisticDTO> todayOrdersShipping(){
        return orderService.findTodayOrdersShipping();
    }
    @GetMapping(path = "/seller/week/shipping")
    public List<WeekOrderClass> weekOrdersShipping(){
        System.out.println("got here");
        return orderService.findWeekOrdersShipping();
    }
    @GetMapping(path = "/seller/today/delivered")
    public List<DayOrderStatisticDTO> todayOrdersDelivered() {
        return orderService.findTodayOrdersDelivered();
    }
    @GetMapping(path = "/seller/week/delivered")
    public List<WeekOrderClass> weekOrdersDelivered(){
        return orderService.findWeekOrdersDelivered();
    }
    @DeleteMapping(path="/delete/{id}")
    public void deleteOrder(@PathVariable("id")Long id){
        orderService.removeOrder(id);
    }

}
