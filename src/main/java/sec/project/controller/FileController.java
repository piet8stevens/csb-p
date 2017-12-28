package sec.project.controller;

import sec.project.repository.FileRepository;
import sec.project.domain.FileObject;
import sec.project.repository.MessageRepository;
import sec.project.domain.MessageObject;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
public class FileController {

    @Autowired
    private FileRepository fileRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private MessageRepository messageRepository;

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public String list(Authentication authentication, Model model, final HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache");
        String username = authentication.getName();
        System.out.println("list in FileController for user:" + username);
        if (accountRepository.findByUsername(username) == null) {
            System.out.println("No such user found in accountRepository!");
        }
        model.addAttribute("files", accountRepository.findByUsername(username).getFileObjects());
        System.out.println("adding messages in FileController.list");

        model.addAttribute("messages", messageRepository.findAll());
        model.addAttribute("username", username);
        model.addAttribute("address", accountRepository.findByUsername(username).getAddress());
        System.out.println("FileController.list exit");
        return "files";
    }

    @RequestMapping(value = "/files/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> viewFile(@PathVariable Long id) {
        System.out.println("viewFile in FileController");
        FileObject fo = fileRepository.findOne(id);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fo.getContentType()));
        headers.add("Content-Disposition", "attachment; filename=" + fo.getName());
        headers.setContentLength(fo.getContentLength());

        return new ResponseEntity<>(fo.getContent(), headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/files", method = RequestMethod.POST)
    public String addFile(Authentication authentication, @RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("addFile in FileController");
        
        Account account = accountRepository.findByUsername(authentication.getName());
        System.out.println("authentication.getName():" + authentication.getName());
        
        FileObject fileObject = new FileObject();
        fileObject.setContentType(file.getContentType());
        fileObject.setContent(file.getBytes());
        fileObject.setName(file.getOriginalFilename());
        fileObject.setContentLength(file.getSize());
        fileObject.setAccount(account);
        
        fileRepository.save(fileObject);
        

        return "redirect:/files";
    }

    @RequestMapping(value = "/files/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long id) {
        System.out.println("deleteFile in FileController");
        fileRepository.delete(id);
        return "redirect:/files";
    }
}
