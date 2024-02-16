package et.com.gebeya.safaricom.coreservice.model;

import et.com.gebeya.safaricom.coreservice.dto.FormQuestionDto;
import et.com.gebeya.safaricom.coreservice.model.enums.QuestionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FormQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;
    private QuestionType questionType;
    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;  // Add this field

    public FormQuestion(FormQuestionDto formQuestionDto){
        this.setQuestionText(formQuestionDto.getQuestionText());
        this.setQuestionType(QuestionType.valueOf(formQuestionDto.getQuestionType()));
    }
}
