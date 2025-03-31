package co.edu.uniquindio.social_reports.services.impl;

import co.edu.uniquindio.social_reports.services.ImageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService {

    private final Cloudinary cloudinary;

    public ImageServiceImpl() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "daljpqz0i");
        config.put("api_key", "273622847594172");
        config.put("api_secret", "WTV3M");

        cloudinary = new Cloudinary(config);
    }

    @Override
    public Map uploadImage(MultipartFile image) throws Exception {
        File file = transform(image);
        return cloudinary.uploader().upload(file, ObjectUtils.asMap("folder", "reportes"));
    }

    @Override
    public Map deleteImage(String imageId) throws Exception {
        return cloudinary.uploader().destroy(imageId, ObjectUtils.emptyMap());
    }

    private File transform(MultipartFile image) throws IOException {
        File file = File.createTempFile(image.getOriginalFilename(), null);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(image.getBytes());
        fos.close();
        return file;
    }

}
