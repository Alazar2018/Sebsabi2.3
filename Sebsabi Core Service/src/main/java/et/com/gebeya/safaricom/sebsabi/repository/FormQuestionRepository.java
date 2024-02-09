package et.com.gebeya.safaricom.sebsabi.repository;

import et.com.gebeya.safaricom.sebsabi.model.FormQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface FormQuestionRepository extends JpaRepository<FormQuestion,Long> {

    List<FormQuestion> getFormQuestionByForm_Id(Long form_id);
}
