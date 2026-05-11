package br.com.arthivia.EasyInviteApi.models.dtos;

import br.com.arthivia.EasyInviteApi.models.entities.FaqEntity;

public record FaqsDto(
        String id,
        String question,
        String answer,
        Integer order
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
