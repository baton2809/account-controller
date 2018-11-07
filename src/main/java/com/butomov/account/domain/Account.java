package com.butomov.account.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;

@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Account {

    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private Long accountId;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.PERSIST, CascadeType.LOCK})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    private Double amount;
    // TODO добавить лимит

    public Account(final User user) {
        this.user = user;
        this.amount = 0.;
    }
}
