package com.neo.vibecheck.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_like")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeEntity {

    @EmbeddedId
    private LikeId id;
}
