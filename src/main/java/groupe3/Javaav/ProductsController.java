package groupe3.Javaav;

import groupe3.Javaav.dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import groupe3.Javaav.model.Product;
import groupe3.Javaav.model.viewmodels.ProductViewModel;

@Controller
public class ProductsController {

    @Autowired
    private ProductDAO productService;

    private String errorMessage;

    @RequestMapping("/users")
    public String index(Model model){
        model.addAttribute("listProducts", productService.listAll());
        return "index";
    }

    @RequestMapping(value = { "/users/add" }, method = RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("userForm", new ProductViewModel());
        return "add";
    }

    @RequestMapping(value = { "/users/add" }, method = RequestMethod.POST)
    public String addPost(Model model, @ModelAttribute("userForm") ProductViewModel productViewModel){

        if (productViewModel.getName() != null && productViewModel.getName().length() > 0 //
                && productViewModel.getType() != null &&  productViewModel.getType().length() > 0) {
            Product u = new Product();
            u.setName(productViewModel.getName());
            u.setType(productViewModel.getType());
            u.setRating(productViewModel.getRating());
            productService.add(u);

            return "redirect:/users";
        }
        errorMessage = "Nom et prénom obligatoire";
        model.addAttribute("errorMessage", errorMessage);
        return "add";


    }
}
