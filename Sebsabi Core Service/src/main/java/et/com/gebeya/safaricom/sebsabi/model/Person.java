package et.com.gebeya.safaricom.sebsabi.model;

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
    private String email;
    private String password;
}
