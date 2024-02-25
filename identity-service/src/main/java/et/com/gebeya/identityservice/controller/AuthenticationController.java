package et.com.gebeya.identityservice.controller;

import et.com.gebeya.identityservice.dto.requestDto.TokenDto;
import et.com.gebeya.identityservice.dto.requestDto.UserInformation;
import et.com.gebeya.identityservice.dto.responseDto.AuthenticationResponse;
import et.com.gebeya.identityservice.dto.responseDto.ValidationResponseDto;
import et.com.gebeya.identityservice.entity.UserCredentials;
import et.com.gebeya.identityservice.service.AuthenticationService;
import et.com.gebeya.identityservice.service.JwtService;
import et.com.gebeya.identityservice.service.UserCredentialsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")

public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserCredentialsService userCredentialsService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserInformation userInformation) {
        return ResponseEntity.ok(authenticationService.signIn(userInformation));
    }
    @PostMapping("/register")
    public ResponseEntity<UserCredentials> signup(@RequestBody UserCredentials userCredentials) {
        return ResponseEntity.ok(userCredentialsService.createUpdateUser(userCredentials));
    }
    @PostMapping("/validate")
    public ResponseEntity<ValidationResponseDto> validate(@RequestBody TokenDto request)
    {
        return authenticationService.validate(request);
    }

}
