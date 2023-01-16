package maryam.service.order;

import lombok.RequiredArgsConstructor;
import maryam.controller.order.ItemHolder;
import maryam.data.order.OrderRepository;
import maryam.data.user.UserRepository;
import maryam.models.order.Order;
import maryam.models.user.User;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor @Transactional
public class OrderService {
    private final UserRepository userRepository;
    private final ItemService itemService;
    private final OrderRepository orderRepository;
    public Order addOrder(List<ItemHolder> items){

        User user = userRepository.findByUsername((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()!="anonymousUser"&& items.size()!=0) {
            Order order = new Order(user);
            order = orderRepository.save(order);
            itemService.addItems(items, order);
            return order;
        }
        return null;
    }
    public Order checkOutOrder(Long id){
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if(optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setChecked(true);
            return order;
        }
        else{
            return null;
        }
    }

    public Order setDeliveredOrder(Long id){
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            order.setDelivered(true);
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
    public List<Order> listOfDeliveredOrders(){
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if(username!="anonymousUser") {
            Long id = userRepository.findByUsername(username).getId();

            return orderRepository.findByUserDelivered(id);
        }
        return null;
    }
    public List<Order> listOfNotDeliveredOrders(){
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if(username!="anonymousUser") {
            Long id = userRepository.findByUsername(username).getId();

            return orderRepository.findByUserNotDelivered(id);
        }
        return null;
    }
    public Iterable<Order> uncheckedOrders(){
        return orderRepository.findByCheckedIsNull();
    }
    public void removeOrder(Long id){
        orderRepository.deleteById(id);
    }
}