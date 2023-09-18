package com.omerfurkan.studentforum.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "user")
@Data
@Accessors(chain = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name", unique = true)
    private String userName;
    private String password;
    @Column(name = "educational_email")
    private String educationalEmail;
    @Column(name = "is_active")
    private Boolean isActive;
}
