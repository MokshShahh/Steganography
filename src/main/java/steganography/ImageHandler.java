package steganography;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageHandler {

    public void saveImage(BufferedImage image, String outputPath) throws IOException {
        File file = new File(outputPath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        ImageIO.write(image, "png", file);
    }

    public BufferedImage loadImage(String inputPath) throws IOException {
        return ImageIO.read(new File(inputPath));
    }
}
