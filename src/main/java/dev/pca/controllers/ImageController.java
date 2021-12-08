package dev.pca.controllers;

import dev.pca.controllers.exceptions.ImageNotFoundException;
import dev.pca.controllers.exceptions.InvalidImageException;
import dev.pca.models.Image;
import dev.pca.services.ImageService;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

@CrossOrigin(origins = "http://localhost:8080/", maxAge = 3600)
@RestController
@RequestMapping(path="v0/fab/images", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CardController.class);

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        checkArgument(imageService != null, "imageService cannot be null.");
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<Image> saveImg(@RequestParam String code, @RequestParam MultipartFile file) {
        LOGGER.debug("operation='saveImg', message='{}, {}'", code, file);

        Optional<Image> newImg = imageService.insert(code, file);
        if (newImg.isEmpty()) {
            throw new InvalidImageException("Cannot read image file");
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
            .path("/{id}")
            .buildAndExpand(newImg.get().getCode())
            .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(params = "id")
    public ResponseEntity<byte[]> getImgFileById(@RequestParam String id, @RequestParam Optional<String> width) {
        LOGGER.debug("operation='getImgFileById', message='{}, {}'", id, width.orElse("width not provided"));
        Optional<Binary> image = imageService.getImgFileById(id, width.orElse("width not provided"));
        return buildFileResponse(image, String.format("Image ID: %s not found.", id));
    }

    @GetMapping(params = "code")
    public ResponseEntity<byte[]> getImgFileByCode(@RequestParam String code, @RequestParam Optional<String> width) {
        LOGGER.debug("operation='getImgFileByCode', message='{}, {}'", code, width.orElse("width not provided"));
        Optional<Binary> image = imageService.getImgFileByCode(code, width.orElse("not provided"));
        return buildFileResponse(image,String.format("Image code: %s not found.", code));
    }

    @GetMapping(path = "/verbose", params = "id")
    public ResponseEntity<Image> getImgVerboseById(@RequestParam String id, @RequestParam Optional<String> width) {
        LOGGER.debug("operation='getImgVerboseById', message='{}, {}'", id, width.orElse("width not provided"));
        Optional<Image> image = imageService.getImgVerboseById(id, width.orElse("width not provided"));
        return buildVerboseResponse(image, String.format("Image ID: %s not found.", id));
    }

    @GetMapping(path = "/verbose", params = "code")
    public ResponseEntity<Image> getImgVerboseByCode(@RequestParam String code, @RequestParam Optional<String> width) {
        LOGGER.debug("operation='getImgVerboseByCode', message='{}, {}'", code, width.orElse("width not provided"));
        Optional<Image> image = imageService.getImgVerboseByCode(code, width.orElse("width not provided"));
        return buildVerboseResponse(image, String.format("Image code: %s not found.", code));
    }

    private ResponseEntity<byte[]> buildFileResponse(Optional<Binary> image, String errMessage) {
        if (image.isEmpty()) {
            throw new ImageNotFoundException(errMessage);
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image.get().getData());
    }

    private ResponseEntity<Image> buildVerboseResponse(Optional<Image> card, String errMessage) {
        if (card.isEmpty()) {
            throw new ImageNotFoundException(errMessage);
        }
        return ResponseEntity.ok(card.get());
    }
}
