package et.com.gebeya.safaricom.coreservice.controller;

import et.com.gebeya.safaricom.coreservice.dto.FormDto;
import et.com.gebeya.safaricom.coreservice.dto.FormQuestionDto;
import et.com.gebeya.safaricom.coreservice.dto.JobFormDisplaydto;
import et.com.gebeya.safaricom.coreservice.dto.UserResponseDto;
import et.com.gebeya.safaricom.coreservice.model.*;
import et.com.gebeya.safaricom.coreservice.service.FormQuestionService;
import et.com.gebeya.safaricom.coreservice.service.FormService;
import et.com.gebeya.safaricom.coreservice.service.GigWorkerService;
import et.com.gebeya.safaricom.coreservice.dto.AssignRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/forms")
public class FormController {

    private final FormService formService;
    private final FormQuestionService formQuestionService;
    private final GigWorkerService gigWorkerService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public JobFormDisplaydto createForm(@RequestBody FormDto formDTO ,@RequestParam String clientUsername)  {
        JobFormDisplaydto newForm=formService.createForm(formDTO,clientUsername);
       return newForm;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Form> getAllForms() {
        return formService.getAllForms();
    }
    @GetMapping("/view")
    @ResponseStatus(HttpStatus.OK)
    public List<Form> getAllFormsByStatus(Status status) {
        return formService.getFormsByStatus(status);
    }

    @GetMapping("/{client_id}")
    @ResponseStatus(HttpStatus.OK)
    public Form getFormByClientId(@PathVariable Long client_id) {
        return formService.getFormByClientId(client_id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Form updateForm(@PathVariable Long id, @RequestBody FormDto formDTO) throws InvocationTargetException, IllegalAccessException {
        return formService.updateForm(id, formDTO);
    }
    @PostMapping("/add/question-to-form")
    public Form addQuestionToForm(@RequestParam Long formID, @RequestBody FormQuestionDto questionDTO) throws InvocationTargetException, IllegalAccessException {
        return formService.addQuestionToForm(formID, questionDTO);
    }
    @GetMapping("/view/questionOfForm")
    public List<FormQuestion> viewQuestions(@RequestParam Long formID) throws InvocationTargetException, IllegalAccessException {
        return formQuestionService.getFormQuestionBYFOrmID(formID);
    }
    @PostMapping("/assign-job")
    public ResponseEntity<GigWorker> assignJobToGigWorker(@RequestBody AssignRequest request) {
        Long gigWorkerId = request.getGigWorkerId();
        Long formId = request.getFormId();
        GigWorker assignedGigWorker = gigWorkerService.assignJobToGigWorker(gigWorkerId, formId);
        return ResponseEntity.ok(assignedGigWorker);
    }

    @GetMapping("/{formId}")
    public ResponseEntity<Form> getForm(@PathVariable Long formId, Principal principal) throws AccessDeniedException {
        Long gigWorkerId = Long.parseLong(principal.getName()); // assuming gig worker's ID is the username
        Form form = formService.getFormForGigWorker(gigWorkerId, formId);
        return ResponseEntity.ok(form);
    }
    @PostMapping("/submit-response")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse submitResponse(@RequestBody UserResponseDto responseDTO) {
        return formService.submitResponse(responseDTO);
    }
}
