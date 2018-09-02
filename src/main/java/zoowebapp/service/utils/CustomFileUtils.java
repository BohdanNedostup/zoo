package zoowebapp.service.utils;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public interface CustomFileUtils {

    String PROJECT_PATH = System.getProperty("user.dir");
    String SEPARATOR = System.getProperty("file.separator");
    String ROOT_PATH = PROJECT_PATH + SEPARATOR + "src"
            + SEPARATOR + "main" + SEPARATOR + "webapp" + SEPARATOR + "upload";

    static File createFolder(String folderName){
        File uploadDir = new File(ROOT_PATH);
        if (!uploadDir.exists()){
            uploadDir.mkdir();
        }

        File folder = new File(uploadDir.getAbsolutePath() + SEPARATOR + folderName);
        if (!folder.exists()){
            folder.mkdir();
        }

        return folder;
    }

    static void createImage(String folderName, MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty() && multipartFile != null){

            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(multipartFile.getBytes()));
            File destination = new File(createFolder(folderName).getAbsolutePath() + SEPARATOR + multipartFile.getOriginalFilename());
            ImageIO.write(bufferedImage, "png", destination);
        }

    }

    static String getImage(String folderName, String image) throws IOException {
        File file = null;
        byte[] encodedFileToByte = null;
        String encodedFile = null;

        if (image != null || image != ""){
            file = new File(ROOT_PATH + SEPARATOR + folderName + SEPARATOR + image);
            encodedFileToByte = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
            encodedFile = new String(encodedFileToByte);
        }

        return encodedFile;
    }
}
