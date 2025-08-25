package com.example.auth_service.infrastructure.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ApplicantRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private LocalDate dateOfBirth;

    private String address;
    private String phoneNumber;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Min(0)
    @Max(15000000)
    private Double baseSalary;
}
