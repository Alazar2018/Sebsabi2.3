package et.com.gebeya.safaricom.sebsabi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private Long formId;
    private Long questionId;
    private Long gigWorkerId;
    private String userAnswer;
}