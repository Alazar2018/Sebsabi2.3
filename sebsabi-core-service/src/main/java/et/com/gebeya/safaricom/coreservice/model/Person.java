package et.com.gebeya.safaricom.coreservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class Person extends BaseModel{
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
}
