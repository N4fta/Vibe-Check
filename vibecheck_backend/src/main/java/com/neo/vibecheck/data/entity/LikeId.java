package com.neo.vibecheck.data.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeId implements Serializable {

    @Column(name = "post_id")
    private long postId;

    @Column(name = "account_id")
    private long userId;

}