package com.example.spring_boot_docker.controllers;

import com.example.spring_boot_docker.DTOs.CardDTO;
import com.example.spring_boot_docker.models.Card;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.spring_boot_docker.services.CardService;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    // Mostar todas las cartas
    @GetMapping
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    // Mostrar una carta
    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.getCardById(id));
    }

    //Añadir una carta
    @PostMapping
    public Card addCard(@RequestBody Card card) {
        return cardService.addCard(card);
    }

    // Añadir varias cartas

    @PostMapping("/multi")
    public ResponseEntity<List<CardDTO>> createMultipleCards(@RequestBody List<CardDTO> cardDTOs) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cardService.createMultipleCards(cardDTOs));
    }


}
