package com.alin.task_management_project.services;

import com.alin.task_management_project.dtos.AuthResponseDTO;
import com.alin.task_management_project.dtos.RegisterLoginRequestDTO;
import com.alin.task_management_project.dtos.UserResponseDTO;
import com.alin.task_management_project.entities.User;
import com.alin.task_management_project.exceptions.UserNotFoundException;
import com.alin.task_management_project.exceptions.UserRequestValidationException;
import com.alin.task_management_project.mappers.RegisterLoginMapper;
import com.alin.task_management_project.mappers.UserResponseMapper;
import com.alin.task_management_project.repositories.UserRepository;
import com.alin.task_management_project.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserResponseMapper userResponseMapper;
    private final RegisterLoginMapper registerLoginMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserResponseMapper userResponseMapper, RegisterLoginMapper registerLoginMapper, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userResponseMapper = userResponseMapper;
        this.registerLoginMapper = registerLoginMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO loginRequest(RegisterLoginRequestDTO registerLoginRequestDTO){
        User user = userRepository.findByName(registerLoginRequestDTO.name()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
//        if(!passwordEncoder.matches(
//                registerLoginRequestDTO.password(),
//                user.getPassword()
//        )){
//            throw  new UserRequestValidationException("Invalid Password");
//        }

       Authentication authentication =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerLoginRequestDTO.name(),
                        registerLoginRequestDTO.password()
                )
        );
       String role = authentication.getAuthorities().iterator().next().getAuthority();
       System.out.print(role);
        String generatedToken = jwtService.generateToken(authentication.getName(),role);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(
                authentication.getName(),
                generatedToken,
                role
        );





        return authResponseDTO;

    }
    public UserResponseDTO registerRequest(RegisterLoginRequestDTO registerLoginRequestDTO){
        String hashedPassword = passwordEncoder.encode(registerLoginRequestDTO.password());
        User user = registerLoginMapper.apply(registerLoginRequestDTO);
        user.setPassword(hashedPassword);

        userRepository.save(user);

        return userResponseMapper.apply(user);
    }
}
