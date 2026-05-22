package br.com.arthivia.EasyInviteApi.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TokenRequestDto(
        @NotNull
        @NotEmpty
        String token
) {
}
