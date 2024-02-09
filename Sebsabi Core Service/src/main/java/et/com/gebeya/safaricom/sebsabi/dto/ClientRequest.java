package et.com.gebeya.safaricom.sebsabi.dto;

import et.com.gebeya.safaricom.sebsabi.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String companyName;
    private String companyType;
    private String occupation;
    private Status isActive;
}
