package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.repository.AccountRepository;

@Controller
public class SignupController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @RequestMapping("*")
    public String defaultMapping() {
        System.out.println("defaultMapping in SignupController");
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        System.out.println("loadForm in SignupController");
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(Authentication authentication, Model model, @RequestParam String name, @RequestParam String address) {
        System.out.println("submitForm in SignupController");
        if (accountRepository.findByUsername(name) != null) {
            System.out.println("Name already exists in accountRepository");
            return "namealreadyexists";
        }
        //accountRepository.save(new Account(name, address));
        Account account = new Account();
        account.setUsername(name);
        account.setAddress(address);
        accountRepository.save(account);
        model.addAttribute("username", name);
        return "done";
    }
    
    @RequestMapping(value="/done1", method = RequestMethod.POST)
    public String createAccount(Authentication authentication, Model model, @RequestParam String username, @RequestParam String password) {
        System.out.println("createAccount in SignupController:" + username + "; pwd:" + password);
        Account account = accountRepository.findByUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        accountRepository.save(account);
        model.addAttribute("username", username);
        model.addAttribute("files", account.getFileObjects());
        return "/top";
    }

    @RequestMapping(value="/top", method=RequestMethod.POST)
    public String topMenu() {
        return "/top";
    }
}
