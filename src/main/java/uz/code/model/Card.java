package uz.code.model;

import jdk.jfr.Timestamp;
import lombok.*;
import uz.code.enums.CardStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Timestamp
public class Card {
    private String number;
    private LocalDate expDate;
    private Double balance;
    private CardStatus status;
    private String phone;
    private LocalDateTime createdDate;

}
