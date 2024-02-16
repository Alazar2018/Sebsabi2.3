package et.com.gebeya.safaricom.coreservice.service;


import et.com.gebeya.safaricom.coreservice.dto.GigWorkerRequest;
import et.com.gebeya.safaricom.coreservice.dto.GigwWorkerResponse;
import et.com.gebeya.safaricom.coreservice.dto.UserInformation;
import et.com.gebeya.safaricom.coreservice.model.Form;
import et.com.gebeya.safaricom.coreservice.model.GigWorker;
import et.com.gebeya.safaricom.coreservice.repository.FormRepository;
import et.com.gebeya.safaricom.coreservice.repository.GigWorkerRepository;
import et.com.gebeya.safaricom.coreservice.Exceptions.FormNotFoundException;
import et.com.gebeya.safaricom.coreservice.Exceptions.GigWorkerNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GigWorkerService {
    private final GigWorkerRepository gigWorkerRepository;
    private final WebClient.Builder webClientBuilder;
    private final FormRepository formRepository;


    public String createGigWorkers(GigWorkerRequest gigWorkerRequest){
        GigWorker gigWorker=new GigWorker(gigWorkerRequest);
       createGigWorkersUserInformation(gigWorker);
        gigWorkerRepository.save(gigWorker);
        log.info("Gig-Worker {} is Created and saved",gigWorkerRequest.getFirstName());
        return "Gig worker Signed up Successfully ";
    }
    private void createGigWorkersUserInformation(GigWorker gigWorker) {
        UserInformation userInformation = new UserInformation();
        userInformation.setUsername(gigWorker.getEmail());
        userInformation.setPassword(gigWorker.getPassword());
        userInformation.setRoles("ROLE_GIGWORKER");

        String response = webClientBuilder.build().post()
                .uri("http://identity-service/auth/register")
                .bodyValue(userInformation)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info("Response from identity micro service==> {}", response);
    }


    public List<GigwWorkerResponse> getAllGigWorker(){
        List<GigWorker> gigWorkers= gigWorkerRepository.findAll();
        return gigWorkers.stream().map(this::mapToClientResponse).toList();
    }

    private GigwWorkerResponse mapToClientResponse(GigWorker gigWorker) {
        return new GigwWorkerResponse(gigWorker);
    }

    public GigWorker getGigWorkerById(Long gigWorkerId) {
        return gigWorkerRepository.findById(gigWorkerId)
                .orElseThrow(() -> new RuntimeException("GigWorker not found with id: " + gigWorkerId));

    }
    public GigWorker assignJobToGigWorker(Long gigWorkerId, Long formId) {
        // Validate gig worker ID
        GigWorker gigWorker = gigWorkerRepository.findById(gigWorkerId)
                .orElseThrow(() -> new GigWorkerNotFoundException("Gig worker not found with ID: " + gigWorkerId));

        // Validate form ID
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new FormNotFoundException("Form not found with ID: " + formId));

        // Additional validation checks if necessary, such as checking if the form is already assigned to a gig worker

        // Assign the job to the gig worker by updating the entity
        gigWorker.setAssignedForm(form);

        // Save the updated gig worker entity
        return gigWorkerRepository.save(gigWorker);
    }
}
