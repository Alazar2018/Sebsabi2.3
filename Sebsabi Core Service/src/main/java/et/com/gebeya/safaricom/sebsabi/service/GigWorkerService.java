package et.com.gebeya.safaricom.sebsabi.service;


import et.com.gebeya.safaricom.sebsabi.dto.GigWorkerRequest;
import et.com.gebeya.safaricom.sebsabi.dto.GigwWorkerResponse;
import et.com.gebeya.safaricom.sebsabi.dto.UserInformation;
import et.com.gebeya.safaricom.sebsabi.model.GigWorker;
import et.com.gebeya.safaricom.sebsabi.repository.GigWorkerRepository;
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


    public void createGigWorkers(GigWorkerRequest gigWorkerRequest){
        GigWorker gigWorker=new GigWorker(gigWorkerRequest);
        createGigWorkersUserInformation(gigWorker);
        gigWorkerRepository.save(gigWorker);
        log.info("Gig-Worker {} is Created and saved",gigWorkerRequest.getFirstName());
    }
    private void createGigWorkersUserInformation(GigWorker gigWorker) {
        UserInformation userInformation = new UserInformation();
        userInformation.setUsername(gigWorker.getEmail());
        userInformation.setPassword(gigWorker.getPassword());

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
}
