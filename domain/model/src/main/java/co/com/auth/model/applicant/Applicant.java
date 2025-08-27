package co.com.auth.model.applicant;
import lombok.*;
//import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class Applicant {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String phone;
    private String email;
    private BigDecimal baseSalary;
}
