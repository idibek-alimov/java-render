package maryam.service.user;

import ch.qos.logback.core.encoder.EchoEncoder;
import lombok.RequiredArgsConstructor;
import maryam.data.user.SellerPropertiesRepository;
import maryam.models.user.SellerProperties;
import maryam.models.user.User;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerPropertiesService {
    private final SellerPropertiesRepository sellerPropertiesRepository;
    //private final UserService userService;
    public SellerProperties createSellerProperties(User user) {
            SellerProperties sellerProperties = new SellerProperties(user);
            sellerProperties = sellerPropertiesRepository.save(sellerProperties);
            return sellerProperties;
    }
    public SellerProperties addProductInStorage(User user,Integer quantity){
        SellerProperties sellerProperties = user.getSellerProperties();
        sellerProperties.setProductsInStorage(sellerProperties.getProductsInStorage()+quantity);
        return  sellerProperties;
    }
    public SellerProperties removeProductInStorage(User user,Integer quantity){
        SellerProperties sellerProperties = user.getSellerProperties();
        sellerProperties.setProductsInStorage(sellerProperties.getProductsInStorage()-quantity);
        return  sellerProperties;
    }
    public SellerProperties addProductsSold(Integer quantity){
//        SellerProperties sellerProperties = userService.getCurrentUser().getSellerProperties();
//        sellerProperties.setProductsInStorage(sellerProperties.getProductsInStorage()+quantity);
//        return  sellerProperties;
        return null;
    }
    public SellerProperties sellProductHandle(User user,Integer quantity,Double price){
        SellerProperties sellerProperties = user.getSellerProperties();
        sellerProperties.setBalance(sellerProperties.getBalance()+price);
        sellerProperties.setProductsSold(sellerProperties.getProductsSold()+quantity);
        sellerProperties = sellerPropertiesRepository.save(sellerProperties);
        user.setSellerProperties(sellerProperties);
        return sellerProperties;
    }
}
