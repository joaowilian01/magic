package com.example.magic.repositories;

import com.example.magic.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByOrderByName();
    List<Card> findAllByOrderByPrice();

    @Query(value = "SELECT * FROM tb_card where player = :player", nativeQuery = true)
    List<Card> findAllByPlayer(@Param("player") String player);

    @Query(value = "SELECT * FROM tb_card where player = :player ORDER BY name ASC", nativeQuery = true)
    List<Card> findAllByPlayerOrderByName(@Param("player") String player);

    @Query(value = "SELECT * FROM tb_card where player = :player ORDER BY price ASC", nativeQuery = true)
    List<Card> findAllByPlayerOrderByPrice(@Param("player") String player);
}
