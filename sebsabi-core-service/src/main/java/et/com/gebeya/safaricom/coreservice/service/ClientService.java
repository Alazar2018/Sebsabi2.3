package et.com.gebeya.safaricom.coreservice.service;

import et.com.gebeya.safaricom.coreservice.dto.requestDto.ClientRequest;
import et.com.gebeya.safaricom.coreservice.dto.responseDto.ClientResponse;
import et.com.gebeya.safaricom.coreservice.dto.requestDto.UserRequestDto;
import et.com.gebeya.safaricom.coreservice.event.ClientCreatedEvent;
import et.com.gebeya.safaricom.coreservice.model.Client;
import et.com.gebeya.safaricom.coreservice.model.Status;
import et.com.gebeya.safaricom.coreservice.model.enums.Authority;
import et.com.gebeya.safaricom.coreservice.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClientService {
    private final ClientRepository clientRepository;

    private final WebClient.Builder webClientBuilder;

    private final KafkaTemplate<String, ClientCreatedEvent> kafkaTemplate;

    public String createClients(ClientRequest clientRequest) {
        Client client = new Client(clientRequest);
        clientRepository.save(client);
        // log.info(client.getId().toString());
        createClientsUserInformation(client);
        log.info("Client {} is Created and saved", client.getFirstName());
        clientRepository.save(client);
        String fullName = client.getFirstName() + " " + client.getLastName();

        kafkaTemplate.send("notificationTopic",new ClientCreatedEvent(client.getEmail(),fullName));
        return "Client  Signed up Successfully ";
    }


    private void createClientsUserInformation(Client client) {
        UserRequestDto newUser = UserRequestDto.builder()
                .userId(client.getId())
                .userName(client.getEmail())
                .name(client.getFirstName())
                .password(client.getPassword())
                .authority(Authority.CLIENT)
                .isActive(true)
                .build();
        String response = webClientBuilder.build().post()
                .uri("http://identity-service/api/auth/register")
                .bodyValue(newUser)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        // log.info("Response from identity micro service==> {}", response);
    }
    private void updateClientsUserInformation(Client client) {
        // Create a UserRequestDto object with only the password field set
        UserRequestDto updateUser = UserRequestDto.builder()
                .userId(client.getId())
                .password(client.getPassword()) // Assuming this is the password field in the Client object
                .build(); // Build the UserRequestDto object

        // Make a POST request to update the password in the identity service
        String response = webClientBuilder.build().post()
                .uri("http://identity-service/api/auth/reset/password")
                .bodyValue(updateUser) // Pass the UserRequestDto object as the body
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // Log the response from the identity microservice
        // log.info("Response from identity micro service==> {}", response);
    }

    public List<ClientResponse> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(this::mapToClientResponse).toList();
    }

    public Optional<Client> getClientId(Long id) {
        return clientRepository.findById(id);
    }

    public Optional<Client> getClientByUsername(String email) {
        return clientRepository.findByEmail(email);
    }

    public ClientResponse getClientById(Long userId) {
        Optional<Client> clients = clientRepository.findById(userId);
        if (clients.isPresent()) {
            Client client = clients.get();
            return new ClientResponse(client);
        }

        throw new RuntimeException("Client not Found with this is");
    }

    private ClientResponse mapToClientResponse(Client client) {
        return new ClientResponse(client);
    }

    public ClientResponse updateClient(Long id, ClientRequest clientRequest) throws InvocationTargetException, IllegalAccessException {
        Optional<Client> existingClientOptional = clientRepository.findById(id);
        if (existingClientOptional.isPresent()) {
            Client existingClient = existingClientOptional.get();

            // Check if the ClientRequest contains a non-null password
            if (clientRequest.getPassword() != null && !clientRequest.getPassword().isEmpty()) {
                // If password is being updated, call updateClientsUserInformation method
                existingClient.setPassword(clientRequest.getPassword());

                updateClientsUserInformation(existingClient);
            }

            // Use NullAwareBeanUtilsBean to handle null properties
            BeanUtilsBean notNullBeanUtils = new NullAwareBeanUtilsBean();
            notNullBeanUtils.copyProperties(existingClient, clientRequest);

            // Save the updated client
            Client updatedClient = clientRepository.save(existingClient);

            // Return the updated client as ClientResponse
            return new ClientResponse(updatedClient);
        } else {
            throw new RuntimeException("Client not found with id: " + id);
        }
    }


    public static class NullAwareBeanUtilsBean extends BeanUtilsBean {
        @Override
        public void copyProperty(Object dest, String name, Object value) throws IllegalAccessException, InvocationTargetException {
            if (value != null) {
                if (value instanceof Integer && (Integer) value == 0) {
                    // If the value is 0 (default value for int), we don't want to copy it
                    return;
                }
                if (value instanceof Enum && ((Enum<?>) value).ordinal() == 0) {
                    // If the value is the first enum constant (ordinal 0), we don't want to copy it
                    return;
                }
                if (dest instanceof Status && value instanceof String) {
                    // If the destination is of type Status enum and value is a string, convert it to Status enum
                    Status status = Status.valueOf(((String) value).toUpperCase()); // Convert the string to uppercase before converting to enum
                    super.copyProperty(dest, name, status);
                    return;
                }
                super.copyProperty(dest, name, value);
            }
        }
    }
}