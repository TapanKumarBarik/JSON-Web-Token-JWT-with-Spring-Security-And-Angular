package com.example.supportportal.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,updatable = false)
    private Long Id;

    private String userId;

    @Column(nullable = false,length = 100)
    private String firstName;

    @Column(nullable = false,length = 100)
    private String lastName;

    @Column(nullable = false,length = 100)
    private String password;

    @Column(nullable = false,length = 100)
    private String email;

    private String profileUrl;

    @Column(nullable = false)
    private Date lastLoginDate;

    @Column(nullable = false)
    private Date lastLoginDateDisplay;

    @Column(nullable = false)
    private Date joinedDate;

    private String[] roles;

    private String[] authorities;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private boolean isNotLocked;

}
