package et.com.gebeya.safaricom.sebsabi.identityservice.service;

import et.com.gebeya.safaricom.sebsabi.identityservice.repository.UserCredentialRepository;
import et.com.gebeya.safaricom.sebsabi.identityservice.entity.UserCredential;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {
    @Autowired
    private UserCredentialRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;
    @Transactional
    @SneakyThrows
    public ResponseEntity<String> saveUser(UserCredential credential) {
        log.info("Wait Started");
        Thread.sleep(10000);
        log.info("Wait Ended");
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return ResponseEntity.ok(String.format("User Information : %s created", credential.getUsername()));
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }


}