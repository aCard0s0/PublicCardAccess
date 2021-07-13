package dev.pca.controller;

import dev.pca.controller.exceptions.ImageNotFoundException;
import dev.pca.models.Image;
import dev.pca.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<Image> create(@RequestParam("code") String title, @RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(imageService.insert(title, file));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(params = "id")
    public ResponseEntity<Image> getByImageId(@RequestParam("id") String id) {
        return imageService.getByImageId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(params = "code")
    public ResponseEntity<Image> getByCode(@RequestParam("code") String code, @RequestParam("width") Optional<String> width) {
        Optional<Image> image = imageService.getByImageCode(code, width.orElse("not provided"));
        if (image.isEmpty()) {
            throw new ImageNotFoundException("Image code: "+ code +" not found");
        }
        return ResponseEntity.ok(image.get());
    }
}
