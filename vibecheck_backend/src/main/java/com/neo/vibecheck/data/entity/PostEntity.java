package com.neo.vibecheck.data.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @EqualsAndHashCode.Exclude
    private UserEntity owner;

    @NotBlank
    @Length(min = 3, max = 50)
    @Column(name = "title")
    private String title;

    @NotBlank
    @Length(min = 3, max = 200)
    @Column(name = "body")
    private String body;

    @Column(name = "track_id")
    private String trackId;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private PostEntity parent;

    @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private List<PostEntity> comments;

    @Column(name = "created")
    private Date created;

    @Formula("(SELECT COUNT(*) FROM post_like pl WHERE pl.post_id = id)")
    private int likes;
}
