package dev.pca.services;

import dev.pca.dao.ImageDao;
import dev.pca.models.Image;
import org.bson.types.Binary;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ImageService {
    private final static Pattern PATTERN = Pattern.compile("^\\d+");
    private final ImageDao imageDao;

    public ImageService(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    public Optional<Image> insert(String title, MultipartFile file) {
        return imageDao.insert(title, file);
    }

    public Optional<Binary> getImgFileById(String id, String width) {
        Optional<Image> image = getImgVerboseById(id, width);
        return convertToBinary(image);
    }

    public Optional<Binary> getImgFileByCode(String code, String width) {
        Optional<Image> image = getImgVerboseByCode(code, width);
        return convertToBinary(image);
    }

    public Optional<Image> getImgVerboseById(String id, String width) {
        Optional<Image> img = imageDao.getById(id);
        return checkPresenceAndResize(img, width);
    }

    public Optional<Image> getImgVerboseByCode(String code, String width) {
        Optional<Image> img = imageDao.getByCode(code);
        return checkPresenceAndResize(img, width);
    }

    private Optional<Image> checkPresenceAndResize(Optional<Image> img, String width) {
        if (img.isPresent()) {
            Matcher matcher = PATTERN.matcher(width);
            if (matcher.find()) {
                return resizedImage(img.get(), Integer.parseInt(matcher.group(0)));
            }
            return img;
        }
        return Optional.empty();
    }

    private Optional<Binary> convertToBinary(Optional<Image> image) {
        if (image.isEmpty()) {
            return Optional.empty();
        }
        Binary imageBin = image.get().getImage();
        return Optional.of(imageBin);
    }

    private Optional<Image> resizedImage(Image img, int width) {
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
}
