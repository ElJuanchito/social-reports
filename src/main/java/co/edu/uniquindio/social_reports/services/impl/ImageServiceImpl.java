package co.edu.uniquindio.social_reports.services.impl;

import co.edu.uniquindio.social_reports.services.interfaces.ImageService;
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
        config.put("cloud_name", "dafksy5tq");
        config.put("api_key", "929237532275326");
        config.put("api_secret", "luhYq6qtKsbhFZhxYS4vWkWxTsc");

        cloudinary = new Cloudinary(config);
    }

    @Override
    public String uploadImage(MultipartFile image) throws Exception {
        File file = transform(image);
        Map map = cloudinary.uploader().upload(file, ObjectUtils.asMap("folder", "reportes"));
        return (String) map.get("url");
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
