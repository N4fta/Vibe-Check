package com.neo.vibecheck.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeNotification {
    private Long postOwnerId;
    private Long postId;
    private String likedUsername;
}