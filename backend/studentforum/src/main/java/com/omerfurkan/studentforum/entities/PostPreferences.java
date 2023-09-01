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
    @Column(name = "interaction_environment")
    private String interactionEnvironment;
    @Column(name = "post_category")
    private String postCategory;
}
