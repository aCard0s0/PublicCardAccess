package dev.pca.services;

import dev.pca.dao.ImageDao;
import dev.pca.models.Image;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public Optional<Image> getImgVerboseById(String code, String width) {
        Matcher matcher = PATTERN.matcher(width);
        if (matcher.find()) {
            return imageDao.getById(code, Integer.parseInt(matcher.group(0)));
        }
        return imageDao.getById(code);
    }

    public Optional<Image> getImgVerboseByCode(String code, String width) {
        Matcher matcher = PATTERN.matcher(width);
        if (matcher.find()) {
            return imageDao.getByCode(code, Integer.parseInt(matcher.group(0)));
        }
        return imageDao.getByCode(code);
    }

    private Optional<Binary> convertToBinary(Optional<Image> image) {
        if (image.isEmpty()) {
            return Optional.empty();
        }
        Binary imageBin = image.get().getImage();
        return Optional.of(imageBin);
    }
}
