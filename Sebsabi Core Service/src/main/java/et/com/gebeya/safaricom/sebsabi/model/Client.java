package et.com.gebeya.safaricom.sebsabi.model;

import et.com.gebeya.safaricom.sebsabi.dto.ClientRequest;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "clients")
public class Client extends Person{

   private String companyName;
   private String companyType;
   private String occupation;


   public Client(ClientRequest clientRequest){
      this.setFirstName(clientRequest.getFirstName());
      this.setLastName(clientRequest.getLastName());
      this.setEmail(clientRequest.getEmail());
      this.setOccupation(clientRequest.getOccupation());
      this.setCompanyName(clientRequest.getCompanyName());
      this.setCompanyType(clientRequest.getCompanyType());
      this.setIsActive(clientRequest.getIsActive());
   }
}
