package groupe3.Javaav;

import groupe3.Javaav.dao.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import groupe3.Javaav.model.Category;
import groupe3.Javaav.model.viewmodels.CategoryViewModel;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private CategoryDAO categoryService;

    private String errorMessage;

    @GetMapping("")
    public List<Category> getAll(){
        return categoryService.listAll();
    }

    @GetMapping("/{id}")
    public Category getUsersById(@PathVariable(value = "id") Long categoryId){
        Category category = categoryService.findById(categoryId);
        System.out.println(category);
        return category;
    }

    @PostMapping("")
    public ResponseEntity.BodyBuilder addCategory(@RequestBody Category category){

        int res = categoryService.add(category);
        if (res == 1){
            return ResponseEntity.ok();
        }else{
            return ResponseEntity.badRequest();
        }

    }
}
