package co.edu.uniquindio.social_reports.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ImageService {

    String uploadImage(MultipartFile image) throws Exception;

    Map deleteImage(String imageId) throws Exception;
}
