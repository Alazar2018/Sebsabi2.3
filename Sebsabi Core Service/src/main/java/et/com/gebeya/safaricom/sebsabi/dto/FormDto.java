package et.com.gebeya.safaricom.sebsabi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormDto {
    private String title;
    private String description;
    //private List<FormQuestionDto> questions;
    private int usageLimit;
}