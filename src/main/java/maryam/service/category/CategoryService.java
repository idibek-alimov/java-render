package maryam.service.category;

import lombok.RequiredArgsConstructor;
//import maryam.data.category.CategoryPropertiesRepository;
import maryam.data.category.CategoryRepository;
import maryam.dto.category.SellerCategoryDTO;
import maryam.models.category.Category;
//import maryam.models.category.CategoryProperties;
import maryam.models.product.Product;
//import maryam.types.CategoryWithProperties;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
//    private final CategoryPropertiesRepository categoryPropertiesRepository;

    public Category getCategoryById(Long id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()){
            return optionalCategory.get();
        }
        else{
            return null;
        }
    }
    public Category getCategory(Category category){
        Optional<Category> optionalCategory = categoryRepository.findByName(category.getName());
        if(optionalCategory.isPresent()) {
            return optionalCategory.get();
        }
        else {
            return null;
        }
    }
    public Product addCategoryToProduct(Product product,Long id){
        Category category = getCategoryById(id);
        if (category!=null){
            product.setCategory(category);
        }
        return product;
    }
    public Product updateCategoryProduct(Product product,Long id){
        if(!product.getCategory().getId().equals(id)) {
            return addCategoryToProduct(product,id);
        }
        else{
            return product;
        }
    }
    public Category addCategory(Category category){
        Optional<Category> optionalCategory = categoryRepository.findByName(category.getName());
        if(optionalCategory.isPresent()){
            return optionalCategory.get();
        }
        else {
            Category createdCategory = categoryRepository.save(category);
            return createdCategory;
        }
    }
    public Category save(SellerCategoryDTO categoryWithProperties){
        Category category = new Category()
                .builder()
                .name(categoryWithProperties.getName())
                .nameTJ(categoryWithProperties.getNameTJ())
                .nameRU(categoryWithProperties.getNameRU())
                .color(categoryWithProperties.getColor())
                .size(categoryWithProperties.getSize())
                .build();
        if (categoryWithProperties.getCategory() != null){
            Optional<Category> parentCategory =  categoryRepository.findById(categoryWithProperties.getCategory());
            if (parentCategory.isPresent()){
                category.setCategory(parentCategory.get());
            }
        }
        return categoryRepository.save(category);
    }
    public List<Category> findBySimilarName(String name){
        return categoryRepository.findBySimilarName(name);
    }

    public List<Category> findMostCommon(Integer limit){
        return categoryRepository.findMostPopular(limit);
    }
}
