package com.restConsume.entity;

import com.restConsume.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
public class Account  extends BaseEntity{

    @Column(unique = true)
    private Long accountNumber;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private String baseCurrency;
    private BigDecimal balance;
    @ManyToOne
    private User user;
}
