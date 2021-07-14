package dev.pca.service;

import dev.pca.dao.ImageDao;
import dev.pca.models.Image;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public Image insert(String title, MultipartFile file) throws IOException {
        return imageDao.insert(title, file);
    }

    public Optional<Image> getByImageId(String id) {
        return imageDao.getById(id);
    }

    public Optional<Image> getImgBinByCode(String code, String width) {
        Matcher matcher = PATTERN.matcher(width);
        if (matcher.find()) {
            return imageDao.getByCode(code, Integer.parseInt(matcher.group(0)));
        }
        return imageDao.getByCode(code);
    }

    public Optional<Binary> getImgByCode(String code, String width) {
        Optional<Image> optImg = getImgBinByCode(code, width);
        if (optImg.isEmpty()) {
            return Optional.empty();
        }
        Binary imageBin = optImg.get().getImage();
        return Optional.of(imageBin);
    }
}
