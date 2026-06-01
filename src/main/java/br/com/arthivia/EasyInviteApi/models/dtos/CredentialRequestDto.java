package br.com.arthivia.EasyInviteApi.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record CredentialRequestDto(
        @NotBlank(message = "Token must not be blank")
        String credential
) {
}
