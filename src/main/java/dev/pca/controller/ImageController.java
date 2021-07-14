package dev.pca.controller;

import dev.pca.controller.exceptions.ImageNotFoundException;
import dev.pca.models.Image;
import dev.pca.service.ImageService;
import org.bson.types.Binary;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("fab/v1/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<Image> postImg(@RequestParam("code") String title, @RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(imageService.insert(title, file));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(params = "id")
    public ResponseEntity<Image> getImgBinById(@RequestParam("id") String id) {
        Optional<Image> image = imageService.getByImageId(id);
        if (image.isEmpty()) {
            throw new ImageNotFoundException("Image id: " + id + " not found");
        }
        return ResponseEntity.ok(image.get());
    }

    @GetMapping(path = "bin", params = "code")
    public ResponseEntity<Image> getImgBinByCode(@RequestParam("code") String code, @RequestParam("width") Optional<String> width) {
        Optional<Image> image = imageService.getImgBinByCode(code, width.orElse("not provided"));
        if (image.isEmpty()) {
            throw new ImageNotFoundException("Image code: "+ code +" not found");
        }
        return ResponseEntity.ok(image.get());
    }

    @GetMapping(params = "code")
    public ResponseEntity<byte[]> getImgByCode(@RequestParam("code") String code, @RequestParam("width") Optional<String> width) {
        Optional<Binary> image = imageService.getImgByCode(code, width.orElse("not provided"));
        if (image.isEmpty()) {
            throw new ImageNotFoundException("Image code: "+ code +" not found");
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image.get().getData());
    }
}
