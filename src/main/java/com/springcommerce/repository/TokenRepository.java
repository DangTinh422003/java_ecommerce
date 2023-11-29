package com.springcommerce.repository;

import com.springcommerce.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {
    @Query("SELECT t FROM Token t WHERE t.user.id = ?1 AND (t.expired = false OR t.revoked = false)")
    List<Token> findAllValidTokenByUser(String id);

    Optional<Token> findByToken(String token);

    @Query("SELECT t FROM Token t WHERE t.token = ?1 AND t.expired = false AND t.revoked = false")
    Token findUserByToken(String token);

    @Query("SELECT t FROM Token t WHERE t.user.id = ?1")
    List<Token> findAllTokenByUserId(String id);

   void deleteAllTokenByUserId(String id);

}
