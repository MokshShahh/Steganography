package steganography;

import java.awt.image.BufferedImage;

public class LSBCore {

    public void encode(BufferedImage image, String message) {
        byte[] data = (message + "\0").getBytes();
        int byteIdx = 0;
        int bitIdx = 0;

        outer:
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                int[] channels = {r, g, b};
                for (int i = 0; i < 3; i++) {
                    if (byteIdx < data.length) {
                        int bit = (data[byteIdx] >> (7 - bitIdx)) & 1;
                        channels[i] = (channels[i] & 0xFE) | bit;
                        
                        bitIdx++;
                        if (bitIdx == 8) {
                            bitIdx = 0;
                            byteIdx++;
                        }
                    } else {
                        rgb = (0xFF << 24) | (channels[0] << 16) | (channels[1] << 8) | channels[2];
                        image.setRGB(x, y, rgb);
                        break outer;
                    }
                }
                rgb = (0xFF << 24) | (channels[0] << 16) | (channels[1] << 8) | channels[2];
                image.setRGB(x, y, rgb);
            }
        }
    }

    public String decode(BufferedImage image) {
        StringBuilder message = new StringBuilder();
        int currentByte = 0;
        int bitIdx = 0;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                int[] channels = {
                    (rgb >> 16) & 0xFF,
                    (rgb >> 8) & 0xFF,
                    rgb & 0xFF
                };

                for (int i = 0; i < 3; i++) {
                    int bit = channels[i] & 1;
                    currentByte = (currentByte << 1) | bit;
                    bitIdx++;

                    if (bitIdx == 8) {
                        if (currentByte == 0) {
                            return message.toString();
                        }
                        message.append((char) currentByte);
                        currentByte = 0;
                        bitIdx = 0;
                    }
                }
            }
        }
        return message.toString();
    }
}
