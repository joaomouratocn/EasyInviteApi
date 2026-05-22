package br.com.arthivia.EasyInviteApi.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageService {
    @Value("${spring.upload-dir}")
    private String uploadDir;

    public String saveImage(MultipartFile file, String type) throws IOException {
        String folder = switch (type.toLowerCase()) {
            case "themes" -> "pages";
            case "profiles" -> "profiles";
            default -> throw new IllegalArgumentException("Invalid type");
        };

        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path target = Paths.get(uploadDir, folder, filename);

        Files.createDirectories(target.getParent());
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        return "images/" + folder + "/" + filename;
    }

    public Resource getImage(String folder, String filename) throws MalformedURLException {
        Path filePath = Paths.get(uploadDir, folder, filename);
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("Image Not Found!");
        }
        return resource;
    }
}
