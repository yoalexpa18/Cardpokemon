package com.example.spring_boot_docker.repositories;

import com.example.spring_boot_docker.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> id(Long id);
}
