package br.com.arthivia.EasyInviteApi.services;

import br.com.arthivia.EasyInviteApi.models.dtos.FaqsDto;
import br.com.arthivia.EasyInviteApi.repositories.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FaqServices {
    private final FaqRepository faqRepository;

    public List<FaqsDto> getAllFaqs() {
        return faqRepository.findAll().stream().map(FaqsDto::new).toList();
    }
}
