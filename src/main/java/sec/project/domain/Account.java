package sec.project.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;
// PAS 20171222 Merged Signup and Account java classes.
@Entity
public class Account extends AbstractPersistable<Long> {

    @Column(unique = true)
    private String username;
    private String password;
    private String address;

   public Account() {
        super();
    }

    public Account(String username, String address) {
        this();
        this.username = username;
        this.address = address;
    }

    @OneToMany(mappedBy = "account")
    private List<FileObject> fileObjects;
    
    public List<FileObject> getFileObjects() {
        return fileObjects;
    }

    public void setFileObjects(List<FileObject> fileObjects) {
        this.fileObjects = fileObjects;
    }

    @OneToMany(mappedBy = "account")
    private List<MessageObject> messageObjects;

    public List<MessageObject> getMessageObjects() {
        return messageObjects;
    }

    public void setMessageObjects(List<MessageObject> messageObjects) {
        this.messageObjects = messageObjects;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
