package et.com.gebeya.safaricom.sebsabi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProposalDto {
    private Long jobId;
    private Double ratePerForm;
    private String proposalText;

}