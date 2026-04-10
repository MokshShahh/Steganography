package steganography;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageHandler {

    public void saveImage(BufferedImage image, String outputPath) throws IOException {
        File outputFile = new File(outputPath);
        ImageIO.write(image, "png", outputFile);
    }

    public BufferedImage loadImage(String inputPath) throws IOException {
        return ImageIO.read(new File(inputPath));
    }
}
