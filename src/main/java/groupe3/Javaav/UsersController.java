package groupe3.Javaav;

import groupe3.Javaav.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import groupe3.Javaav.model.User;
import groupe3.Javaav.model.viewmodels.UserViewModel;

@Controller
public class UsersController {

    @Autowired
    private UserDAO userService;

    private String errorMessage;

    @RequestMapping("/users")
    public String index(Model model){
        model.addAttribute("listUsers", userService.listAll());
        return "index";
    }

    @RequestMapping(value = { "/users/add" }, method = RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("userForm", new UserViewModel());
        return "add";
    }

    @RequestMapping(value = { "/users/add" }, method = RequestMethod.POST)
    public String addPost(Model model, @ModelAttribute("userForm") UserViewModel userViewModel){

        if (userViewModel.getFirstname() != null && userViewModel.getFirstname().length() > 0 //
                && userViewModel.getLastname() != null &&  userViewModel.getLastname().length() > 0) {
            User u = new User();
            u.setLastname(userViewModel.getLastname());
            u.setFirstname(userViewModel.getFirstname());
            u.setPhone(userViewModel.getPhone());
            u.setEmail(userViewModel.getEmail());
            userService.add(u);

            return "redirect:/users";
        }
        errorMessage = "Nom et prénom obligatoire";
        model.addAttribute("errorMessage", errorMessage);
        return "add";


    }
}
