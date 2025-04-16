package com.scoreit.scoreit.repository;

import com.scoreit.scoreit.entity.EmailResetToken;
import com.scoreit.scoreit.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailResetTokenRepository extends JpaRepository<EmailResetToken, Long> {
    Optional<EmailResetToken> findByToken(String token);
}
