package com.butomov.account.model;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(exclude = "password")
@Entity
public class User {

    @Id
    @GeneratedValue
    private UUID userId;

    @Column
    private String name;

    @Column
    private String password;
}
