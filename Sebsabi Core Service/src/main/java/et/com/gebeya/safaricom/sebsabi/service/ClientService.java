package et.com.gebeya.safaricom.sebsabi.service;

import et.com.gebeya.safaricom.sebsabi.dto.ClientRequest;
import et.com.gebeya.safaricom.sebsabi.dto.ClientResponse;
import et.com.gebeya.safaricom.sebsabi.dto.UserInformation;
import et.com.gebeya.safaricom.sebsabi.model.Client;
import et.com.gebeya.safaricom.sebsabi.model.enums.Authority;
import et.com.gebeya.safaricom.sebsabi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {
    private final ClientRepository clientRepository;

    private final WebClient.Builder webClientBuilder;

    public void createClients(ClientRequest clientRequest){
        Client client=new Client(clientRequest);
        clientRepository.save(client);
        createClientsUserInformation(client);
        log.info("Client {} is Created and saved",client.getFirstName());

    }

    private void createClientsUserInformation(Client client) {
        UserInformation userInformation = new UserInformation();
        userInformation.setUsername(client.getEmail());
        userInformation.setPassword(client.getPassword());
        userInformation.setRoles("ROLE_CLIENTS");

        String response = webClientBuilder.build().post()
                .uri("http://identity-service/auth/register")
                .bodyValue(userInformation)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info("Response from identity micro service==> {}", response);
    }
    public List<ClientResponse> getAllClients(){
        List<Client> clients= clientRepository.findAll();
       return clients.stream().map(this::mapToClientResponse).toList();
    }

    private ClientResponse mapToClientResponse(Client client) {
        return new ClientResponse(client);
    }
}
