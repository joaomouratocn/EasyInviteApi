package br.com.arthivia.EasyInviteApi.services;

import br.com.arthivia.EasyInviteApi.models.dtos.ThemeDto;
import br.com.arthivia.EasyInviteApi.repositories.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;

    public List<ThemeDto> getAllTheme(){
        return themeRepository.findAll().stream().map(ThemeDto::new).toList();
    }
}
