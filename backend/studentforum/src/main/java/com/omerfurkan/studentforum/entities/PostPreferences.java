package com.omerfurkan.studentforum.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "post_preferences")
@Data
public class PostPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String interactionEnvironment;
    private String postCategory;
}
