package zoowebapp.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {

    String uploadFile(MultipartFile multipartFile, String path);

    void destroyFile(String publicId, Map options) throws IOException;
}
