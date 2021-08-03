package dev.pca.controller;

import dev.pca.controller.exceptions.CardNotFoundException;
import dev.pca.controller.exceptions.ImageNotFoundException;
import dev.pca.models.Image;
import dev.pca.service.ImageService;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

@RestController
@RequestMapping(path="fab/v0/images", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CardController.class);

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        checkArgument(imageService != null, "imageService cannot be null.");
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<Image> postImg(@RequestParam("code") String title, @RequestParam("file") MultipartFile file) {
        LOGGER.debug("operation='postImg', message='{}, {}'", title, file);
        try {
            return ResponseEntity.ok(imageService.insert(title, file));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(params = "/id")
    public ResponseEntity<Image> getImgBinById(@RequestParam("id") String id) {
        LOGGER.debug("operation='getImgBinById', message='{}'", id);
        Optional<Image> image = imageService.getByImageId(id);
        return buildResponse(image, String.format("Image ID: %s not found.", id));
    }

    @GetMapping(path = "/bin", params = "code")
    public ResponseEntity<Image> getImgBinByCode(@RequestParam("code") String code, @RequestParam("width") Optional<String> width) {
        LOGGER.debug("operation='getImgBinByCode', message='{}, {}'", code, width);
        Optional<Image> image = imageService.getImgBinByCode(code, width.orElse("not provided"));
        return buildResponse(image, String.format("Image code: %s not found.", code));
    }

    @GetMapping(params = "/code")
    public ResponseEntity<byte[]> getImgByCode(@RequestParam("code") String code, @RequestParam("width") Optional<String> width) {
        LOGGER.debug("operation='getImgByCode', message='{}, {}'", code, width);
        Optional<Binary> image = imageService.getImgByCode(code, width.orElse("not provided"));
        if (image.isEmpty()) {
            throw new ImageNotFoundException("Image code: "+ code +" not found");
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image.get().getData());
    }

    private ResponseEntity<Image> buildResponse(Optional<Image> card, String errMessage) {
        if (card.isEmpty()) {
            throw new CardNotFoundException(errMessage);
        }
        return ResponseEntity.ok(card.get());
    }
}
