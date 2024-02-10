package et.com.gebeya.safaricom.sebsabi.repository;

import et.com.gebeya.safaricom.sebsabi.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {
    Optional<Form> findByIdAndAssignedGigWorkerId(Long formId, Long gigWorkerId);
}
