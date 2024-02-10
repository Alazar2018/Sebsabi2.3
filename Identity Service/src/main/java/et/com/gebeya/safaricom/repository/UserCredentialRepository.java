package et.com.gebeya.safaricom.repository;

import et.com.gebeya.safaricom.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCredentialRepository  extends JpaRepository<UserCredential,Integer> {
    List<UserCredential> findByUsername(String username);
}