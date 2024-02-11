package et.com.gebeya.safaricom.sebsabi.controller;

import et.com.gebeya.safaricom.sebsabi.dto.ClientRequest;
import et.com.gebeya.safaricom.sebsabi.dto.ClientResponse;
import et.com.gebeya.safaricom.sebsabi.service.ClientService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/core")
public class ClientController {
    private final ClientService clientService;
   @PostMapping("/signup/clients")
   @ResponseStatus(HttpStatus.CREATED)
   @RolesAllowed("CLIENTS")
    public void createClients(@RequestBody ClientRequest clientRequest)
   {
        clientService.createClients(clientRequest);
   }
    @GetMapping("/clients")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientResponse> getAllClients(@RequestHeader("loggedInUser") String authority){

        System.out.println(authority);
        return clientService.getAllClients();
    }
    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_CLIENT')")
    @ResponseStatus(HttpStatus.OK)
    public String getMessage(){
       return "see us";
    }
}
