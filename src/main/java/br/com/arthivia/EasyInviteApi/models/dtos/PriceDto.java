package br.com.arthivia.EasyInviteApi.models.dtos;

import br.com.arthivia.EasyInviteApi.models.entities.PriceEntity;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PriceDto(@NotNull BigDecimal price) {
    public PriceDto(PriceEntity priceEntity) {
        this(
            priceEntity.getPrice()
        );
    }
}
