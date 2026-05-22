package br.com.arthivia.EasyInviteApi.controllers;

import br.com.arthivia.EasyInviteApi.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/{folder}/{filename}")
    public ResponseEntity<Resource> getImage(
            @PathVariable String folder,
            @PathVariable String filename) throws MalformedURLException {

        var result = imageService.getImage(folder, filename);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(result);
    }
}
