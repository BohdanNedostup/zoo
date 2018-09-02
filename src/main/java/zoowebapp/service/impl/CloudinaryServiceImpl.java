package zoowebapp.service.impl;

import zoowebapp.service.CloudinaryService;


import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
@PropertySource("classpath:cloudinary.properties")
public class CloudinaryServiceImpl implements CloudinaryService{

    private Cloudinary cloudinary;

    public CloudinaryServiceImpl(Environment environment){
        this.cloudinary = new Cloudinary(environment.getProperty("cloudinary.env"));
    }

    @Override
    public String uploadFile(MultipartFile multipartFile, String path) {
        Uploader uploader = cloudinary.uploader();
        String fileName = multipartFile.getOriginalFilename().substring(0, multipartFile.getOriginalFilename().lastIndexOf("."));
        Map<?, ?> options = ObjectUtils.asMap(
                "public_id", fileName,
                "folder", path,
                "overwrite", true
        );

        Map<?, ?> result;
        try{

            InputStream inputStream = multipartFile.getInputStream();
            result = uploader.uploadLarge(inputStream, options);
            inputStream.close();

        } catch(Exception e){
            e.printStackTrace();
            return null;
        }

        return (String) result.get("url");
    }

    /**
     *
     * @param publicId
     * @param options
     * @throws IOException
     * @param publicId have format "folder/file" without file extention
     */
    @Override
    public void destroyFile(String publicId, Map options) throws IOException {
        Uploader uploader = cloudinary.uploader();
        uploader.destroy(publicId, ObjectUtils.emptyMap());
    }
}