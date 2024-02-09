package et.com.gebeya.safaricom.sebsabi.repository;

import et.com.gebeya.safaricom.sebsabi.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {
    //limit using the limiter

}