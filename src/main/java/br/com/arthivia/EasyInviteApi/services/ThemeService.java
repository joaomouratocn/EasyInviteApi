package br.com.arthivia.EasyInviteApi.services;

import br.com.arthivia.EasyInviteApi.models.dtos.ThemeDto;
import br.com.arthivia.EasyInviteApi.repositories.ThemeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;

    public List<ThemeDto> getAllTheme(){
        return themeRepository.findAll().stream().map(ThemeDto::new).toList();
    }

    public ThemeDto getThemeById(@Valid UUID themeId) {
        var themeEntity = themeRepository.findById(themeId).orElseThrow(() -> new RuntimeException("Theme not found"));
        return new ThemeDto(themeEntity);
    }
}