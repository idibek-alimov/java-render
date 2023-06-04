package maryam.controller.product;

import lombok.RequiredArgsConstructor;
import maryam.models.category.Category;

import maryam.service.category.CategoryService;
import maryam.types.CategoryWithProperties;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping(path = "/name/similar/{name}")
    public List<Category> getBySimilarName(@PathVariable("name")String name){
        return categoryService.findBySimilarName(name);
    }
    @PostMapping(path = "/seller/create")
    public Category createCategory(@RequestBody CategoryWithProperties category){
        return categoryService.save(category);
    }
    @GetMapping(path="/common/{limit}")
    public List<Category> getMostCommon(@PathVariable("limit")Integer limit){
        return categoryService.findMostCommon(limit);
    }
}
