package br.com.arthivia.EasyInviteApi.services;

import br.com.arthivia.EasyInviteApi.models.dtos.FaqsDto;
import br.com.arthivia.EasyInviteApi.models.entities.FaqEntity;
import br.com.arthivia.EasyInviteApi.repositories.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FaqService {
    private final FaqRepository faqRepository;

    public List<FaqsDto> getAllFaqs() {
        return faqRepository.findAll().stream().map(FaqsDto::new).toList();
    }

    public String insertAllFaqs(List<FaqsDto> allfaqs) {
        var faqsEntities = allfaqs.stream().map(FaqEntity::new).toList();
        faqRepository.saveAll(faqsEntities);
        return "Faqs registered successfully";
    }
}
