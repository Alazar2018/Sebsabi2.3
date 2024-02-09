package et.com.gebeya.safaricom.sebsabi.service;

import et.com.gebeya.safaricom.sebsabi.model.FormQuestion;
import et.com.gebeya.safaricom.sebsabi.repository.FormQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormQuestionService {
    private final FormQuestionRepository formQuestionRepository;

    public List<FormQuestion> getFormQuestionBYFOrmID(Long formId){
        return formQuestionRepository.getFormQuestionByForm_Id(formId);
    }

}
