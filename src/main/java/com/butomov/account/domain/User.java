package com.butomov.account.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class User {

    @Id
    @GeneratedValue
    private UUID userId;

    @Column
    private String name;

    @Column
    private String password;

    public User(final String name) {
        this.name = name;
    }
}
