package br.com.arthivia.EasyInviteApi.services;

import br.com.arthivia.EasyInviteApi.models.dtos.PriceDto;
import br.com.arthivia.EasyInviteApi.models.entities.PriceEntity;
import br.com.arthivia.EasyInviteApi.repositories.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceService {
    private final PriceRepository priceRepository;

    public PriceDto getPrice() {
        var priceEntity = priceRepository.findByEnableTrue().orElseThrow(() -> new RuntimeException("Price not found"));
        return new PriceDto(priceEntity);
    }

    public String savePrice(PriceDto priceDto) {
        var priceEntity = priceRepository.findByEnableTrue().orElseThrow(() -> new RuntimeException("Price not found"));
        priceRepository.disablePriceById(priceEntity.getId());
        var newPrice = new PriceEntity(priceDto);
        priceRepository.save(newPrice);
        return "Price changed!";
    }
}
