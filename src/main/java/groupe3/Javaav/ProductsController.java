package groupe3.Javaav;

import groupe3.Javaav.dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import groupe3.Javaav.model.Product;
import groupe3.Javaav.model.viewmodels.ProductViewModel;

@Controller
public class ProductsController {

    @Autowired
    private ProductDAO productService;

    private String errorMessage;

    @RequestMapping("/products")
    public String index(Model model){
        model.addAttribute("listProducts", productService.listAll());
        return "product/index";
    }

    @RequestMapping(value = { "/products/add" }, method = RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("productForm", new ProductViewModel());
        return "add";
    }

    @RequestMapping(value = { "/products/add" }, method = RequestMethod.POST)
    public String addPost(Model model, @ModelAttribute("productForm") ProductViewModel productViewModel){

        if (productViewModel.getName() != null && productViewModel.getName().length() > 0 //
                && productViewModel.getType() != null &&  productViewModel.getType().length() > 0) {
            Product p = new Product();
            p.setName(productViewModel.getName());
            p.setType(productViewModel.getType());
            p.setRating(productViewModel.getRating());
            productService.add(p);

            return "redirect:/products";
        }
        errorMessage = "Nom et type obligatoiregi";
        model.addAttribute("errorMessage", errorMessage);
        return "add";
    }

    @RequestMapping(value = { "products/{id]" }, method = RequestMethod.DELETE)
    public String delete(@PathVariable(value = "id") Long id){
        System.out.println("test id = " + id);
        return null;
    }
}
