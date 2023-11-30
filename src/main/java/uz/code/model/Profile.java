package uz.code.model;

import jdk.jfr.Timestamp;
import lombok.*;
import uz.code.enums.ProfileStatus;
import uz.code.enums.UserRole;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Timestamp
public class Profile {
    private String name;
    private String surname;
    private String phone;
    private String password;
    private LocalDateTime createdDate;
    private ProfileStatus status;
    private UserRole role;
}
