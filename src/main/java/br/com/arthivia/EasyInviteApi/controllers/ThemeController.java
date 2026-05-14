package br.com.arthivia.EasyInviteApi.controllers;

import br.com.arthivia.EasyInviteApi.models.dtos.ThemeDto;
import br.com.arthivia.EasyInviteApi.services.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/themes")
public class ThemeController {
    private final ThemeService themeService;

    @GetMapping("/getall")
    public ResponseEntity<List<ThemeDto>> getAllThemes(){
        var result = themeService.getAllTheme();
        return ResponseEntity.ok(result);
    }
}
