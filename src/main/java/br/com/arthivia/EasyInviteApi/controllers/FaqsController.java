package br.com.arthivia.EasyInviteApi.controllers;

import br.com.arthivia.EasyInviteApi.models.dtos.FaqsDto;
import br.com.arthivia.EasyInviteApi.services.FaqService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/faqs")
public class FaqsController {
    private final FaqService faqService;

    @GetMapping("/getall")
    public ResponseEntity<List<FaqsDto>> getAllFaqs() {
        var result = faqService.getAllFaqs();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/insert/all")
    public ResponseEntity<String> insertAllFaqs(@Valid @RequestBody List<FaqsDto> allfaqs){
        var result = faqService.insertAllFaqs(allfaqs);
        return ResponseEntity.ok(result);
    }
}
