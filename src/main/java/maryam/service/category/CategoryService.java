package maryam.service.category;

import lombok.RequiredArgsConstructor;
import maryam.data.category.CategoryPropertiesRepository;
import maryam.data.category.CategoryRepository;
import maryam.models.category.Category;
import maryam.models.category.CategoryProperties;
import maryam.models.product.Product;
import maryam.types.CategoryWithProperties;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryPropertiesRepository categoryPropertiesRepository;

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
    public Category save(CategoryWithProperties categoryWithProperties){
        Category category = new Category()
                .builder()
                .name(categoryWithProperties.getName())
                .nameTJ(categoryWithProperties.getNameTJ())
                .nameRU(categoryWithProperties.getNameRU())
                .build();
        category = categoryRepository.save(category);
        if(categoryWithProperties.getColor()!=null || categoryWithProperties.getSize()!=null){
            CategoryProperties categoryProperties =categoryPropertiesRepository.save(new CategoryProperties(category,categoryWithProperties.getSize(),categoryWithProperties.getColor(),categoryWithProperties.getGender()));
            category.setCategoryProperties(categoryProperties);
        }

        return category;
    }
    public List<Category> findBySimilarName(String name){
        return categoryRepository.findBySimilarName(name);
    }

    public List<Category> findMostCommon(Integer limit){
        return categoryRepository.findMostPopular(limit);
    }
}
