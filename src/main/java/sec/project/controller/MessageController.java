package sec.project.controller;

import sec.project.repository.MessageRepository;
import sec.project.domain.MessageObject;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sec.project.domain.Account;
import sec.project.repository.AccountRepository;

@Controller
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "/messages/{id}", method = RequestMethod.GET)
    public String viewMessage(@PathVariable Long id, Model model) {
        System.out.println("viewMessage in MessageController");
        MessageObject mo = messageRepository.findOne(id);
        
        model.addAttribute("messagetitle", mo.getTitle());
        model.addAttribute("messagecontent", mo.getContent());
        System.out.println("viewmessage:" + mo.getTitle() + "|" + mo.getContent());
        return "message";
    }

    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public String addMessage(Authentication authentication, @RequestParam("title") String title, @RequestParam("content") String content) {
        System.out.println("addMessage in MessageController:" + title + "|" + content);
        
        Account account = accountRepository.findByUsername(authentication.getName());
        System.out.println("authentication.getName():" + authentication.getName());
        
        MessageObject messageObject = new MessageObject();
        messageObject.setTitle(title);
        messageObject.setContent(content);
        messageObject.setAccount(account);
        
        messageRepository.save(messageObject);
        

        return "redirect:/files";
    }
}
