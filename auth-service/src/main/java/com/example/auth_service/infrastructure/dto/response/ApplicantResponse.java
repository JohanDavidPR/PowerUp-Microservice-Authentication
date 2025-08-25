package com.example.auth_service.infrastructure.dto.response;

import lombok.Data;

@Data
class ApplicantResponse {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final double baseSalary;
}