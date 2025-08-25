package com.example.auth_service.infrastructure.adapter.in.web;

import com.example.auth_service.application.port.in.RegisterUserUseCase;
import com.example.auth_service.domain.model.User;
import com.example.auth_service.infrastructure.dto.request.ApplicantRequest;
import com.example.auth_service.infrastructure.dto.response.ApplicantResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

}
