package maryam.service.product;


import lombok.RequiredArgsConstructor;

import maryam.data.product.ProductGenderRepository;

import maryam.models.product.Product;
import maryam.models.product.ProductGender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductGenderService {
    private final ProductGenderRepository productGenderRepository;
    public ProductGender addProductGender(ProductGender productGender){
        Optional<ProductGender> optionalProductGender = productGenderRepository.findByName(productGender.getName());
        if(optionalProductGender.isPresent()){
            //optionalBrand.get().getProducts().add(product);
            return optionalProductGender.get();
        }
        else {
            ProductGender createdProductGender = productGenderRepository.save(productGender);

            return createdProductGender;
        }
    }
    public ProductGender getProductGender(ProductGender productGender){
        Optional<ProductGender> optionalProductGender = productGenderRepository.findByName(productGender.getName());
        if(optionalProductGender.isPresent()){
            return optionalProductGender.get();
        }
        else {
            return null;
        }
    }
    public void setGenderToProduct(Product product,ProductGender productGender){
        productGender = getProductGender(productGender);
        if (productGender!=null){
            product.setProductGender(productGender);
        }
    }
    public ProductGender save(ProductGender productGender){
        return productGenderRepository.save(productGender);
    }
//
}
