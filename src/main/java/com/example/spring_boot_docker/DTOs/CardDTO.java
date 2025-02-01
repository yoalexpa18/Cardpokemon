package com.example.spring_boot_docker.DTOs;

import lombok.Data;

@Data
public class CardDTO {
    private String name;
    private String type;
    private String rarity;
    private String imageUrl;
}