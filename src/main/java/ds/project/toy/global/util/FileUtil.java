package ds.project.toy.global.util;

import io.jsonwebtoken.lang.Strings;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtil {

    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(
        MediaType.IMAGE_JPEG_VALUE,
        MediaType.IMAGE_PNG_VALUE
    );

    public boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        String extension = Strings.getFilenameExtension(file.getOriginalFilename());
        return extension != null && contentType != null
            && ALLOWED_IMAGE_TYPES.contains(contentType);
    }
}