package et.com.gebeya.safaricom.sebsabi.dto;

import et.com.gebeya.safaricom.sebsabi.model.Form;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormQuestionDto {
    private String questionText;
    private String questionType;
    //private Form form;
}