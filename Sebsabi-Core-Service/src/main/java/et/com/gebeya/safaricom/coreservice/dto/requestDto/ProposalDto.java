package et.com.gebeya.safaricom.coreservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProposalDto {
    private Long formId;
    private Long gigWorkerId;
    private Double ratePerForm;
    private String proposalText;

}