package et.com.gebeya.safaricom.coreservice.service;

import et.com.gebeya.safaricom.coreservice.dto.ClientRequest;
import et.com.gebeya.safaricom.coreservice.dto.ClientResponse;
import et.com.gebeya.safaricom.coreservice.dto.UserInformation;
import et.com.gebeya.safaricom.coreservice.event.ClientCreatedEvent;
import et.com.gebeya.safaricom.coreservice.model.Client;
import et.com.gebeya.safaricom.coreservice.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClientService {
    private final ClientRepository clientRepository;

    private final WebClient.Builder webClientBuilder;

    private final KafkaTemplate<String,ClientCreatedEvent> kafkaTemplate;

    public String createClients(ClientRequest clientRequest){
        Client client=new Client(clientRequest);
        clientRepository.save(client);
        createClientsUserInformation(client);
        log.info("Client {} is Created and saved",client.getFirstName());
        clientRepository.save(client);
        String fullName = client.getFirstName() + " " + client.getLastName();

        kafkaTemplate.send("notificationTopic",new ClientCreatedEvent(client.getEmail(),fullName));
        return "Client  Signed up Successfully ";
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
    public Optional<Client> getClientId(Long id){
        Optional<Client> clients= clientRepository.findById(id);
        return clients;
    }

    public Optional<Client> getClientByUsername(String email){
        Optional<Client> clients= clientRepository.findByEmail(email);
        return clients;
    }

    private ClientResponse mapToClientResponse(Client client) {
        return new ClientResponse(client);
    }
}
