
package Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageConverter {

    // Metode untuk mengonversi byte[] ke BufferedImage
    public static BufferedImage convertBytesToImage(byte[] imageData) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
        return ImageIO.read(bis);
    }

    // Metode untuk mengonversi BufferedImage ke byte[]
    public static byte[] convertImageToBytes(BufferedImage image, String formatName) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, formatName, bos);
        return bos.toByteArray();
    }
}
