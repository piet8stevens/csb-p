package sec.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override

    protected void configure(HttpSecurity http) throws Exception {
        // copied from MegaUpload PAS 20171222
        // you can access the h2-console through /h2-console
        /* faulty version kludges:
        http.csrf().disable();
        http.authorizeRequests().antMatchers("*").permitAll();
        http.formLogin().defaultSuccessUrl("/files", true);
        http.headers().frameOptions().sameOrigin();
        http.headers().defaultsDisabled(); // in order to disable xss protection
        // http.headers().xssProtection().disable();
        // http.headers().contentTypeOptions();
        http.headers().cacheControl();
        */
        
        http.authorizeRequests()
                .antMatchers("/h2-console/*").denyAll()
                //.anyRequest().authenticated()
                .antMatchers("/form").permitAll()
                .antMatchers("/top").permitAll();
        // http.csrf()
                    //.ignoringAntMatchers("/h2-console/*");
                    //.requireCsrfProtectionMatcher(new AllExceptUrlsStartedWith("/h2-console"))
        // http.headers().defaultsDisabled();
        http.headers().frameOptions().disable();
                    http.formLogin()
                .permitAll();
        http.formLogin().defaultSuccessUrl("/files", true);
        
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
