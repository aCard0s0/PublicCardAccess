package dev.pca.dao;

import dev.pca.models.Image;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ImageDao {
    @Value("${pca.api.maxRequestSize.images:10}")
    private Long MAX_REQUEST_SIZE;

    private final ImageRepository imageRepo;

    public ImageDao(ImageRepository imageRepo) {
        this.imageRepo = imageRepo;
    }

    public Optional<Image> insert(String title, MultipartFile file) {
        Image image = new Image(title);
        try {
            image.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));

        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        image.setContentType("image/png");
        image = imageRepo.insert(image);
        return Optional.of(image);
    }

    public Optional<Image> getById(String id) {
        return imageRepo.findById(id);
    }

    public Optional<Image> getById(String id, int width) {
        Optional<Image> image = getById(id);
        return resizedImage(image, width);
    }

    public Optional<Image> getByCode(String code) {
        return imageRepo.findAll().stream()
                .filter(image -> image.getCode().equals(code))
                .findFirst();
    }

    public Optional<Image> getByCode(String code, int width) {
        Optional<Image> image = getByCode(code);
        return resizedImage(image, width);
    }

    private Optional<Image> resizedImage(Optional<Image> image, int width) {
        if (image.isPresent()) {
            Image img = image.get();
            try {
                ByteArrayInputStream in = new ByteArrayInputStream(img.getImage().getData());
                BufferedImage resizedImg = Scalr.resize(ImageIO.read(in), width);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ImageIO.write(resizedImg, "PNG", out);
                out.flush();
                img.setImage(new Binary(out.toByteArray()));
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                return Optional.empty();
            }
            return Optional.of(img);
        }
        return Optional.empty();
    }

    public Optional<Collection<Image>> getByCodes(List<String> codes) {
        return Optional.of(imageRepo.findAll().stream()
                .filter(image -> codes.contains(image.getCode()))
                .limit(MAX_REQUEST_SIZE)
                .collect(Collectors.toList()));
    }

}
