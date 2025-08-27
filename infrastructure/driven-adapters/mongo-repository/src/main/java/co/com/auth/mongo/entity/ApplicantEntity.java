package co.com.auth.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "applicants")
public class ApplicantEntity {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String phone;
    private String email;
    private Double baseSalary;
}