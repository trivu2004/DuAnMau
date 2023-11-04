package helper;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import model.NhanVien;

public class ShareHelper {

    public static final Image APP_ICON;

    static {
        String resourcePath = "/image/fpt.png"; // Use a relative path
        APP_ICON = new ImageIcon(ShareHelper.class.getResource(resourcePath)).getImage();
    }

    public static boolean saveLogo(File file) {
        File dir = new File("logos");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File newFile = new File(dir, file.getName());
        try {
            // Copy vào thư mục logos (đè nếu đã tồn tại)
            Path source = Paths.get(file.getAbsolutePath());
            Path destination = Paths.get(newFile.getAbsolutePath());
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static ImageIcon readLogo(String fileName) {
        File path = new File("src\\Image\\", fileName);
        ImageIcon originalIcon = new ImageIcon(path.getAbsolutePath());
        int newWidth = 200;
        int newHeight = 260;
        Image originalImage = originalIcon.getImage();
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        resizedImage.getGraphics().drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        return new ImageIcon(resizedImage);
    }

    public static NhanVien USER = null;

    public static void logoff() {
        ShareHelper.USER = null;
    }

    public static boolean authenticated() {
        return ShareHelper.USER != null;
    }
}
