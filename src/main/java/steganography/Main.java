package steganography;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
public class Main {
    public static void main(String[] args) throws Exception {
        LSBCore lsb = new LSBCore();
        ImageHandler handler = new ImageHandler();

        // 1. Create a sample blank image (mocking a real photo)
        BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        String secret = "This is a hidden message saved to disk!";

        // 2. Encode the message
        System.out.println("Encoding: " + secret);
        lsb.encode(img, secret);

        // 3. Save the image as a lossless PNG
        String fileName = "./output/stego_output.png";
        handler.saveImage(img, fileName);
        System.out.println("Encoded image saved as: " + fileName);

        // 4. Load the image back for decoding
        BufferedImage loadedImg = handler.loadImage(fileName);
        String decoded = lsb.decode(loadedImg);

        System.out.println("Decoded from file: " + decoded);

        if (secret.equals(decoded)) {
            System.out.println("Process successful!");
        } else {
            System.out.println("Process failed.");
        }
    }
}
