package br.com.arthivia.EasyInviteApi.models.dtos;

import br.com.arthivia.EasyInviteApi.models.entities.UserEntity;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String email,
        String name
){
    // Construtor compacto para transformar facilmente a Entidade em DTO
    public UserResponseDto(UserEntity user) {
        this(user.getId(), user.getEmail(), user.getName());
    }
}
