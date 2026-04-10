package steganography;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) throws Exception {
        LSBCore lsb = new LSBCore();

        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        String secret = "Hello LSB!";

        System.out.println("Encoding message: " + secret);
        lsb.encode(img, secret);

        String decoded = lsb.decode(img);
        System.out.println("Decoded message: " + decoded);

        if (secret.equals(decoded)) {
            System.out.println("Success!");
        } else {
            System.out.println("Failure.");
        }
    }
}
