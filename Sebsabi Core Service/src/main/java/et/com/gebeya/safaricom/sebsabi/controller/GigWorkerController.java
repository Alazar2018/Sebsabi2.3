package et.com.gebeya.safaricom.sebsabi.controller;

import et.com.gebeya.safaricom.sebsabi.dto.GigWorkerRequest;
import et.com.gebeya.safaricom.sebsabi.dto.GigwWorkerResponse;
import et.com.gebeya.safaricom.sebsabi.service.GigWorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/core/gig-worker")
public class GigWorkerController {
    private final GigWorkerService gigWorkerService;
   @PostMapping("/signup")
   @ResponseStatus(HttpStatus.CREATED)
    public void createGigWorker(@RequestBody GigWorkerRequest gigWorkerRequest){
        gigWorkerService.createGigWorkers(gigWorkerRequest);
   }
    @GetMapping("/gig-workers")
    @ResponseStatus(HttpStatus.OK)
    public List<GigwWorkerResponse> getAllGigWorkers(){
        return gigWorkerService.getAllGigWorker();
    }
}
