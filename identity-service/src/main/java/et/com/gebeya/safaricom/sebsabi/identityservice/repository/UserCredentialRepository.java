package et.com.gebeya.safaricom.sebsabi.identityservice.repository;

import et.com.gebeya.safaricom.sebsabi.identityservice.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCredentialRepository  extends JpaRepository<UserCredential,Integer> {
    List<UserCredential> findByUsername(String username);
}