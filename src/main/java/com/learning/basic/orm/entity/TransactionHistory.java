package com.learning.basic.orm.entity;

import com.learning.basic.orm.annotation.Column;
import com.learning.basic.orm.annotation.Entity;
import com.learning.basic.orm.annotation.PrimaryKey;
import com.learning.basic.orm.annotation.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table("transactions")
@Entity
@Data
@NoArgsConstructor
public class TransactionHistory {

    @PrimaryKey
    @Column("transaction_id")
    private Integer transactionId;

    @Column("account_number")
    private long accountNumber;

    @Column("name")
    private String name;

    @Column("transaction_type")
    private String transactionType;

    @Column("amount")
    private BigDecimal amount;

    public TransactionHistory(long accountNumber, String name, String transactionType, BigDecimal amount) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.transactionType = transactionType;
        this.amount = amount;
    }
}
