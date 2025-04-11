package com.neo.vibecheck.domain.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequest {
    @NotNull
    private Long ownerId;

    @NotBlank
    private String name;

    @NotBlank
    private String body;

    private String trackId;
}
