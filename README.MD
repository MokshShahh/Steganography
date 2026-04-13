# Steganography Tool with AES Encryption

A Java-based application implementing Least Significant Bit (LSB) steganography combined with AES encryption. This tool allows users to securely encrypt text messages before embedding them within images, providing two layers of data protection.

## Features

- **AES Encryption:** Messages are encrypted using the AES-128 algorithm (with SHA-256 key derivation) before being hidden.
- **LSB Encoding:** Embeds encrypted data into the least significant bits of image pixel data.
- **Message Extraction & Decryption:** Decodes hidden data from images and decrypts it using a user-provided key.
- **Enhanced GUI:** Updated interface with dedicated fields for encryption/decryption keys and side-by-side processing panels.
- **Lossless Storage:** Uses the PNG format to ensure that both the image and the embedded encrypted data remain intact.

## Technical Specifications

- **Language:** Java 17
- **Build Tool:** Apache Maven
- **Security:** AES (Advanced Encryption Standard) with PKCS5Padding.
- **Algorithm:** 1-bit LSB substitution across RGB channels.
- **GUI Framework:** Java Swing.

## Prerequisites

- Java Development Kit (JDK) 17 or higher.
- Apache Maven.

## Installation and Build

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd Steganography
   ```

2. Build the project using Maven:
   ```bash
   mvn clean package
   ```

The executable JAR file will be located in the `target/` directory.

## Usage

To start the application, execute the following command:

```bash
java -jar target/steganography-lsb-1.0-SNAPSHOT.jar
```

### Encoding a Message

1. In the **Encode** panel, select a cover image.
2. Enter a secret **Encryption Key**.
3. Enter the message you wish to hide.
4. Click **Encrypt & Hide** and choose a destination to save the new image.

### Decoding a Message

1. In the **Decode** panel, select the image containing the hidden message.
2. Enter the correct **Decryption Key**.
3. Click **Extract & Decrypt**.
4. The application will display the extracted (encrypted) string and the final decrypted message.

## Project Structure

- `CryptoUtils.java`: Handles AES encryption and decryption logic.
- `LSBCore.java`: Manages bit-level manipulation for LSB encoding and decoding.
- `GUI.java`: Implements the enhanced user interface and workflow.
- `ImageHandler.java`: Provides utilities for image loading and saving.
- `Main.java`: Application entry point.

## License

This project is released under the MIT License.
