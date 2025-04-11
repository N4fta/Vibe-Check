package com.neo.vibecheck.data.jpa;

import com.neo.vibecheck.data.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;

public interface RefreshTokenRepositoryJpa extends JpaRepository<RefreshTokenEntity, Integer> {
        boolean existsByUserIdAndTokenAndExpiresAtAfter(Long userId, String token, Timestamp currentTime);

        @Modifying
        @Query("UPDATE RefreshTokenEntity rt " +
                        "SET rt.isRevoked = true " +
                        "WHERE rt.userId = :userId AND rt.token = :token")
        void revokeRefreshToken(@Param("userId") Long userId,
                        @Param("token") String token);

}
