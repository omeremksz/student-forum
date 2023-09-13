package com.omerfurkan.studentforum.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name",unique = true)
    private String userName;
    private String password;
    @Column(name = "educational_email")
    private String educationalEmail;
}
