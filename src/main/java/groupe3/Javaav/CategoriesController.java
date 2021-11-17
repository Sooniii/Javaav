package groupe3.Javaav;

import groupe3.Javaav.dao.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import groupe3.Javaav.model.Category;
import groupe3.Javaav.model.viewmodels.CategoryViewModel;

@Controller
public class CategoriesController {

    @Autowired
    private CategoryDAO categoryService;

    private String errorMessage;

    @RequestMapping("/categories")
    public String index(Model model){
        model.addAttribute("listCategories", categoryService.listAll());
        return "index";
    }

    @RequestMapping(value = { "/categories/add" }, method = RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("categoryForm", new CategoryViewModel());
        return "add";
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
        return "add";

    }
}
