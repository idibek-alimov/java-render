package maryam.service.order;

import lombok.RequiredArgsConstructor;
import maryam.controller.order.ItemHolder;
import maryam.data.order.ItemRepository;
import maryam.data.order.OrderRepository;
import maryam.data.user.UserRepository;
import maryam.dto.order.DayOfTheWeek;
import maryam.dto.order.DayOrderStatisticDTO;
import maryam.dto.order.WeekOrderClass;
import maryam.dto.order.WeekOrderStatisticDTO;
import maryam.models.order.Item;
import maryam.models.order.Order;
import maryam.models.user.User;
import maryam.service.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor @Transactional
public class OrderService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final ItemService itemService;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    public Order addOrder(List<ItemHolder> items) throws Exception{
        try {
            User user = userService.getCurrentUser();
            System.out.println(user);
                Order order = orderRepository.save(new Order(user));
                order.setItems(itemService.addItems(items, order));
                return order;
        }
        catch (Exception e){
            throw new Exception("Fuck you there is no product left at this point Nigga");
        }
    }
    public Order setOrderShipping(Long id){
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if(optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(Order.Status.Shipping);
            return order;
        }
        else{
            return null;
        }
    }
    public Order setOrderArrived(Long id){
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            order.setStatus(Order.Status.Arrived);
            return order;
        }
        return null;
    }
    public Order setOrderDelivered(Long id){
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            order.setStatus(Order.Status.Delivered);
            return order;
        }
            return null;
    }

    public List<Order> orderListByUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByUsername(username);
        return orderRepository.findByUser(user);
    }
    public List<Order> listOfOrders(){
        return (List<Order>) orderRepository.findAll();
    }
//    public List<Order> listOfDeliveredOrders(){
//        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
//        if(username!="anonymousUser") {
//            Long id = userRepository.findByUsername(username).getId();
//
//            return orderRepository.findByUserDelivered(id);
//        }
//        return null;
//    }
//    public List<Item> listOfNotDeliveredOrders(){
//        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
//        if(username!="anonymousUser") {
//            Long id = userRepository.findByUsername(username).getId();
//
//            return orderToItemList(orderRepository.findByUserNotDelivered(id));
//        }
//        return null;
//    }
//    public Iterable<Order> uncheckedOrders(){
//        return orderRepository.findByCheckedIsNull();
//    }
    public void removeOrder(Long id){
        orderRepository.deleteById(id);
    }
    private List<Item> orderToItemList(List<Order> orderList){
        List<Item> itemList = new ArrayList<>();
        for(Order order:orderList){
            for(Item item:order.getItems()){
                itemList.add(item);
            }
        }
        return itemList;
    }

    public List<Order> byUserInQueue(){
        return orderRepository.findByUserInQueue(userService.getCurrentUser().getId());
    }
    public List<Order> byUserShipping(){
        return orderRepository.findByUserShipping(userService.getCurrentUser().getId());
    }
    public List<Order> byUserArrived(){
        return orderRepository.findByUserArrived(userService.getCurrentUser().getId());
    }
    public List<Order> byUserDelivered(){
        return orderRepository.findByUserDelivered(userService.getCurrentUser().getId());
    }

    public List<DayOrderStatisticDTO> findTodayOrdersShipping(){
        return orderRepository.findTodayOrdersShipping(userService.getCurrentUser().getId());
    }

    public List<WeekOrderClass> weekOrderConverter(List<WeekOrderStatisticDTO> weekOrder){
        List<DayOfTheWeek> dayOfTheWeek = getDayOfTheWeek();
        List<WeekOrderClass> emptyList = new ArrayList<>();
        Boolean bool = false;
        for (DayOfTheWeek day:dayOfTheWeek){
            bool = true;
            for(WeekOrderStatisticDTO week:weekOrder){
                if (week.getDay() == day.getDay()){
                    emptyList.add(new WeekOrderClass(week.getCount(),week.getPrice(),week.getDay(),day.getDayOfWeek()));
                    bool= false;
                }
            }
            if(bool){
                emptyList.add(new WeekOrderClass(0,0.0,day.getDay(),day.getDayOfWeek()));
            }
        }
        return emptyList;
    }
    public List<WeekOrderClass> findWeekOrdersShipping(){
        //List<WeekOrderStatisticDTO> weekOrder = orderRepository.findWeekOrdersShipping(userService.getCurrentUser().getId());
        //return null;
        return weekOrderConverter(orderRepository.findWeekOrdersShipping(userService.getCurrentUser().getId()));
    }
    public List<DayOrderStatisticDTO> findTodayOrdersDelivered(){
        return orderRepository.findTodayOrdersDelivered(userService.getCurrentUser().getId());
    }
    public List<WeekOrderClass> findWeekOrdersDelivered(){
        return weekOrderConverter(orderRepository.findWeekOrdersDelivered(userService.getCurrentUser().getId()));
    }

    public List<DayOfTheWeek> getDayOfTheWeek(){
        Calendar cal = Calendar.getInstance();

        List<String> dayOfTheWeek = new ArrayList<>();
        dayOfTheWeek.add("Sat");
        dayOfTheWeek.add("Sun");
        dayOfTheWeek.add("Mon");
        dayOfTheWeek.add("Tue");
        dayOfTheWeek.add("Wed");
        dayOfTheWeek.add("Thu");
        dayOfTheWeek.add("Fri");
        List<DayOfTheWeek> data = new ArrayList<>();
        cal.add(Calendar.DAY_OF_MONTH, -6);
        for (int i = 0; i < 7; i++) {
            data.add(new DayOfTheWeek(dayOfTheWeek.get(cal.getTime().getDay()),cal.getTime().getDate()));
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return data;
    }
}