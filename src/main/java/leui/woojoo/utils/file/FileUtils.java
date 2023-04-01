package leui.woojoo.utils.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;

@Slf4j
@Component
public class FileUtils {
    @Value("${file.path}")
    private String imagePath;

    public String upload(MultipartFile file, String kind) throws IOException {
        if (file == null || file.isEmpty()) {
            return "default.png";
        }

        long nowDate = System.currentTimeMillis();
        long timeStamp = new Timestamp(nowDate).getTime();
        String ext = file.getOriginalFilename().split("\\.")[1];

        File newFileName = new File( imagePath + "/" + kind + "/"
                + timeStamp + "_" + kind + "." + ext);

        log.info(newFileName.getName());
        file.transferTo(newFileName);

        return newFileName.getName();
    }

    public Resource download(String fileName) throws IOException {
        Path path = Paths.get(imagePath + fileName);
        return new InputStreamResource(Files.newInputStream(path));
    }

    public void delete(String fileName, String kind) {
        File file = new File(imagePath + kind + "/" + fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
