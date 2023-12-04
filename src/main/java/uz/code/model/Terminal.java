package uz.code.model;

import jdk.jfr.Timestamp;
import lombok.*;
import uz.code.enums.TerminalStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Timestamp
public class Terminal {
    private String code;
    private String address;
    private TerminalStatus status;
    private LocalDateTime createdDate;

}
