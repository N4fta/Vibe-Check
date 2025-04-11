package com.neo.vibecheck.domain.requests;

import java.util.List;

import com.neo.vibecheck.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostPage {
    private List<Post> posts;
    private int pageNumber;
    private int totalPages;
}
