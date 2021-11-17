package groupe3.Javaav;

import groupe3.Javaav.dao.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import groupe3.Javaav.model.Category;
import groupe3.Javaav.model.viewmodels.CategoryViewModel;

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

    @RequestMapping(value = { "/categories/add" }, method = RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("categoryForm", new CategoryViewModel());
        return "category/add";
    }

    @RequestMapping(value = { "/categories/add" }, method = RequestMethod.POST)
    public String addPost(Model model, @ModelAttribute("categoryForm") CategoryViewModel categoryViewModel){

        if (categoryViewModel.getName() != null && categoryViewModel.getName().length() > 0) {
            Category c = new Category();
            c.setName(categoryViewModel.getName());
            categoryService.add(c);

            return "redirect:/categories";
        }
        errorMessage = "Nom obligatoire";
        model.addAttribute("errorMessage", errorMessage);
        return "category/add";

    }
    @DeleteMapping("/{id}")
    public Category delete(@PathVariable(value = "id") Long categoryId){
        Category category = categoryService.delete(categoryId);

        System.out.println("test id = " + category);
        return category;
    }
}
