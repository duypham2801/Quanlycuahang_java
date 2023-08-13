package quan_ly_cua_hang;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class ImageUpload {
    private static final String IMAGE_DIRECTORY = "images"; 

    public static String uploadImage(File imageFile) {
        try {
            File directory = new File(IMAGE_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = UUID.randomUUID().toString() + ".jpg";
            File destFile = new File(IMAGE_DIRECTORY, fileName);

            Files.copy(imageFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return destFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
