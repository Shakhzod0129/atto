package uz.code.model;

import jdk.jfr.Timestamp;
import lombok.*;
import uz.code.enums.TransactionType;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Timestamp
public class Transaction {
    private Integer id;
    private String cardNumber;
    private Double amount;
    private String terminalCode;
    private TransactionType type;
    private LocalDateTime createdDate;
    }
