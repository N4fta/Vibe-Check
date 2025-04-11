package com.neo.vibecheck.data.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.neo.vibecheck.data.entity.PostEntity;

public interface PostRepositoryJpa extends JpaRepository<PostEntity, Long> {

        @Query("FROM PostEntity p " +
                        "LEFT JOIN p.owner o " +
                        "LEFT JOIN p.comments p2 " +
                        "WHERE (:title = '' OR p.title LIKE %:title%) AND " +
                        "p.parent IS NULL ")
        Page<PostEntity> findByTitleLikeAndParentIsNull(@Param("title") String title,
                        Pageable pageable);

        @Query("FROM PostEntity p " +
                        "LEFT JOIN p.owner o " +
                        "LEFT JOIN p.comments p2 " +
                        "WHERE (:searchTerm = '' OR p.title LIKE %:searchTerm%) " +
                        "AND (:author = '' OR o.username LIKE %:author%) " +
                        "AND (:tag = '' OR p.body LIKE %:tag%) " +
                        "AND (:liked = false OR p.id IN " +
                        "    (SELECT pl.id.postId FROM LikeEntity pl WHERE pl.id.userId = :currentUser)) " +
                        "AND p.parent IS NULL ")
        Page<PostEntity> findNew(
                        @Param("searchTerm") String searchTerm,
                        @Param("author") String author,
                        @Param("tag") String tag,
                        @Param("liked") boolean liked,
                        @Param("currentUser") Long currentUser,
                        Pageable pageable);

        Page<PostEntity> findByParentIsNull(Pageable pageable);
}
