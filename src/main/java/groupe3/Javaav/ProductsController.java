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

import java.util.List;

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

    @GetMapping("/products/sortBy")
    public List<Product> sortBy(@RequestParam(value = "rate") String firstFilter, @RequestParam(required=false) String secondFilter, @RequestParam(required=false) String thirdFilter){
        return productService.sortByRate(firstFilter);
    }
}
