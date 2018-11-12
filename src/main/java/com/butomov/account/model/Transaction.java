package com.butomov.account.model;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private Long transactionId;

    @Column
    private Double amount;

    @OneToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.PERSIST, CascadeType.LOCK})
    @JoinColumn(name = "sender", referencedColumnName = "account_id", nullable = false)
    private Account sender;

    @OneToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.PERSIST, CascadeType.LOCK})
    @JoinColumn(name = "payee", referencedColumnName = "account_id", nullable = false)
    private Account payee;

    @Column
    private Timestamp timestamp;

    @Column
    private String status;
}
