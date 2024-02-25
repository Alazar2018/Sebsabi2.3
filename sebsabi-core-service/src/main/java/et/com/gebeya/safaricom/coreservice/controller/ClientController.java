package et.com.gebeya.safaricom.coreservice.controller;

import et.com.gebeya.safaricom.coreservice.dto.ClientRequest;
import et.com.gebeya.safaricom.coreservice.dto.ClientResponse;
import et.com.gebeya.safaricom.coreservice.model.Client;
import et.com.gebeya.safaricom.coreservice.service.ClientService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/core/client")
public class ClientController {
    private final ClientService clientService;
   @PostMapping("/signup")
   @ResponseStatus(HttpStatus.CREATED)
//   @CircuitBreaker(name = "identity",fallbackMethod = "fallBackMethod")
//   @TimeLimiter(name = "identity")
//   @Retry(name = "identity")
//   @RolesAllowed("CLIENTS")
    public CompletableFuture<String> createClients(@RequestBody ClientRequest clientRequest)
   {
       return  CompletableFuture.supplyAsync(()->clientService.createClients(clientRequest));
   }

   public CompletableFuture<String> fallBackMethod(ClientRequest clientRequest,RuntimeException runtimeException){
       return CompletableFuture.supplyAsync(()->"Oops! Something went wrong , please Try signing up after some time.");
   }


    @GetMapping("/view")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientResponse> getAllClients(){

        //System.out.println(authority);
        return clientService.getAllClients();
    }
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public String getMessage(){
       return "see us";
    }
}
