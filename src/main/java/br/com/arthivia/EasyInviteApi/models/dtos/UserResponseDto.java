package br.com.arthivia.EasyInviteApi.models.dtos;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String email,
        String name,
        String pictureUrl
){}
