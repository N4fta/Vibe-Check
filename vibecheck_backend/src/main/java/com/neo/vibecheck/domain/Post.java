package com.neo.vibecheck.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Long id;

    private User owner;

    private String title;

    private String body;

    private String trackId;

    // Stops infinite referencing in JSON
    @JsonBackReference // When Serializing to JSON don't write this one
    private Post parent;

    @JsonManagedReference // When Serializing to JSON write this one
    private List<Post> comments;

    private int likes;
}
