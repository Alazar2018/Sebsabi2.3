package et.com.gebeya.safaricom.sebsabi.identityservice.service;

import et.com.gebeya.safaricom.sebsabi.identityservice.repository.UserCredentialRepository;
import et.com.gebeya.safaricom.sebsabi.identityservice.entity.CustomUserDetails;
import et.com.gebeya.safaricom.sebsabi.identityservice.entity.UserCredential;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserCredentialRepository repository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserCredential> credentials = repository.findByUsername(username);
        if (credentials.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        } else if (credentials.size() > 1) {
            // Log a warning if multiple users are found with the same username
            LoggerFactory.getLogger(CustomUserDetailsService.class)
                    .warn("Multiple users found with the same username: {}", username);
        }
        return new CustomUserDetails(credentials.get(0));
    }
}