package maryam.controller.product;

import lombok.RequiredArgsConstructor;
import maryam.models.product.ProductGender;
import maryam.service.product.ProductGenderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/productgender")
@RequiredArgsConstructor
public class ProductGenderController {
    private final ProductGenderService productGenderService;
//    @GetMapping(path = "/name/similar/{name}")
//    public List<ProductGender> getBySimilarName(@PathVariable("name")String name){
//        return productGenderService.findBySimilarName(name);
//    }
    @PostMapping(path = "/create")
    public ProductGender createProductGender(@RequestBody  ProductGender productGender){
        return productGenderService.save(productGender);
    }
}
