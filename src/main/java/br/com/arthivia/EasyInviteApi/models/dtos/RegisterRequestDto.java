package br.com.arthivia.EasyInviteApi.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDto(
        @Email @NotBlank String email,
        @NotBlank String name,
        @NotBlank String password,
        boolean sendNewsletter
) {}