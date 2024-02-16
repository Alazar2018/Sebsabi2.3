package et.com.gebeya.safaricom.coreservice.controller;

import et.com.gebeya.safaricom.coreservice.dto.ClientRequest;
import et.com.gebeya.safaricom.coreservice.dto.ClientResponse;
import et.com.gebeya.safaricom.coreservice.dto.GigWorkerRequest;
import et.com.gebeya.safaricom.coreservice.dto.GigwWorkerResponse;
import et.com.gebeya.safaricom.coreservice.service.GigWorkerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/core/gig-worker")
public class GigWorkerController {
    private final GigWorkerService gigWorkerService;
   @PostMapping("/signup")
   @CircuitBreaker(name = "identity",fallbackMethod = "fallBackMethod")
   @TimeLimiter(name = "identity")
   @Retry(name = "identity")
   @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<String> createGigWorker(@RequestBody GigWorkerRequest gigWorkerRequest){
      return CompletableFuture.supplyAsync(()->gigWorkerService.createGigWorkers(gigWorkerRequest));
   }
    public CompletableFuture<String> fallBackMethod(GigWorkerRequest gigWorkerRequest, RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()->"Oops! Something went wrong , please Try signing up after some time.");
    }

    @GetMapping("/gig-workers")
    @ResponseStatus(HttpStatus.OK)
    public List<GigwWorkerResponse> getAllGigWorkers(){
        return gigWorkerService.getAllGigWorker();
    }
}
