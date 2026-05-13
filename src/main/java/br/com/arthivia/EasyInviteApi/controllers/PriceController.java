package br.com.arthivia.EasyInviteApi.controllers;

import br.com.arthivia.EasyInviteApi.models.dtos.PriceDto;
import br.com.arthivia.EasyInviteApi.services.PriceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/price")
public class PriceController {
    private final PriceService priceService;

    @GetMapping()
    public ResponseEntity<PriceDto> getPrice() {
        var result = priceService.getPrice();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/new")
    public ResponseEntity<String> insertPrice(@RequestBody @Valid PriceDto priceDto){
        var result = priceService.savePrice(priceDto);
        return ResponseEntity.ok(result);
    }
}
