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

    public Category getCategory(Category category){
        Optional<Category> optionalCategory = categoryRepository.findByName(category.getName());
        if(optionalCategory.isPresent()) {
            return optionalCategory.get();
        }
        else {
            return null;
        }
    }
    public Product addCategoryToProduct(Product product, Category category){
        category = getCategory(category);
        if (category!=null){
            product.setCategory(category);
        }
        return product;
    }
    public Product updateCategoryProduct(Product product, Category category){

        if(!product.getCategory().getName().equals(category.getName())) {

            return addCategoryToProduct(product, category);

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
        Category category = categoryRepository.save(new Category(categoryWithProperties.getName()));
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
