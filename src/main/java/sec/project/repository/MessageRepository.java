package sec.project.repository;

import java.util.List;
import sec.project.domain.MessageObject;
import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.Account;

public interface MessageRepository extends JpaRepository<MessageObject, Long> {

    List<MessageObject> findByAccount(Account account);

}
