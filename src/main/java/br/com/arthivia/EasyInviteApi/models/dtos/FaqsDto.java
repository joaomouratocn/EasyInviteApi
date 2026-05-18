package br.com.arthivia.EasyInviteApi.models.dtos;

import br.com.arthivia.EasyInviteApi.models.entities.FaqEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FaqsDto(
        UUID id,
        @NotNull
        @NotEmpty
        String question,
        @NotNull
        @NotEmpty
        String answer,
        @NotNull
        int order
) {
    public FaqsDto(FaqEntity faq) {
        this(
                faq.getId(),
                faq.getQuestion(),
                faq.getAnswer(),
                faq.getOrder()
        );
    }
}
