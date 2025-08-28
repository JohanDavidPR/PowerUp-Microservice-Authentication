package co.com.auth.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "applicants")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
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