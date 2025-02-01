package com.example.spring_boot_docker.services;


import com.example.spring_boot_docker.DTOs.CardDTO;
import com.example.spring_boot_docker.models.Card;
import org.springframework.stereotype.Service;
import com.example.spring_boot_docker.repositories.CardRepository;


import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public CardDTO getCardById(Long id) {
        Card card = cardRepository.findById(id).orElse(null);
        CardDTO cardDTO = new CardDTO();
        cardDTO.setName(card.getName());
        cardDTO.setType(card.getType());
        cardDTO.setRarity(card.getRarity());
        cardDTO.setImageUrl(card.getImageUrl());
        System.out.println(cardDTO);
        return cardDTO;
    }

    public Card addCard(Card card) {
        return cardRepository.save(card);
    }

    public List<CardDTO> createMultipleCards(List<CardDTO> cardDTOs) {

        for (CardDTO cardDTO : cardDTOs) {
            Card card = new Card();
            card.setName(cardDTO.getName());
            card.setType(cardDTO.getType());
            card.setRarity(cardDTO.getRarity());
            card.setImageUrl(cardDTO.getImageUrl());
            cardRepository.save(card);
        }
        return cardDTOs;
    }
}