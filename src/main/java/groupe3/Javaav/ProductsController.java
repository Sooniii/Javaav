 package groupe3.Javaav;

        import groupe3.Javaav.dao.ProductDAO;
        import groupe3.Javaav.model.Category;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.http.HttpStatus;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.*;

        import groupe3.Javaav.model.Product;
        import groupe3.Javaav.model.viewmodels.ProductViewModel;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductDAO productService;

    private String errorMessage;

    @GetMapping("")
    public List<Product> getAll(){
        return productService.listAll();
    }

    @PostMapping("")
    public HttpStatus addProduct(@RequestBody Product product){

        int res = productService.add(product);
        if (res == 1){
            return HttpStatus.CREATED;
        }else{
            return HttpStatus.BAD_REQUEST;
        }
    }


    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable(value = "id") Long productId){
        int res = productService.delete(productId);
        if (res == 1) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @GetMapping("/sortBy")
    public List<Product> sortBy(@RequestParam Map<String, String> allParams){
        List<Product> products = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (Map.Entry request: allParams.entrySet()) {
            keys.add((String) request.getKey());
            values.add((String) request.getValue());
        }
        products.addAll(productService.dosomething(keys, values));
        return products;
    }
    @GetMapping("/sort")
    public List<Product> sorting(@RequestParam String asc, @RequestParam String desc){
        return productService.sorting(asc, desc);
    }
}