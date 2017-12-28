package sec.project.config;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sec.project.domain.Account;
import sec.project.repository.AccountRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    // PAS 20171222 commented out
    //private Map<String, String> accountDetails;

    // PAS 20171222 Copied from MegaUpload
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostConstruct
    public void init() {
        // this data would typically be retrieved from a database PAS 20171222 commented out
        // this.accountDetails = new TreeMap<>();
        // this.accountDetails.put("ted", "$2a$06$rtacOjuBuSlhnqMO2GKxW.Bs8J6KI0kYjw/gtF0bfErYgFyNTZRDm");
        
        // PAS 20171222 Copied from MegaUpload
        System.out.println("init in CustomUserDetailsService");
        Account account = new Account();
        account.setUsername("roger");
        account.setPassword(passwordEncoder.encode("carrots"));
        accountRepository.save(account);
        
        account = new Account();
        account.setUsername("valiant");
        account.setPassword(passwordEncoder.encode("vaudeville"));
        accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername in CustomUserDetailsService:" + username);
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                account.getUsername(),
                account.getPassword(),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("USER")));
    }
}
