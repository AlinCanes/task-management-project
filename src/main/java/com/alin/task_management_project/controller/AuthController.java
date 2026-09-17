package com.alin.task_management_project.controller;

import com.alin.task_management_project.dtos.AuthResponseDTO;
import com.alin.task_management_project.dtos.RegisterLoginRequestDTO;
import com.alin.task_management_project.dtos.UserResponseDTO;
import com.alin.task_management_project.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginRequest(@Valid @RequestBody RegisterLoginRequestDTO dto){
        return ResponseEntity.status(200).body(authService.loginRequest(dto));
    }
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerRequest(@Valid @RequestBody RegisterLoginRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerRequest(dto));
    }
}
