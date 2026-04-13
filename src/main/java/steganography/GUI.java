package steganography;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class GUI extends JFrame {
    private LSBCore lsb = new LSBCore();
    private ImageHandler handler = new ImageHandler();

    private JTextField encodePathField = new JTextField(20);
    private JTextField encodeKeyField = new JTextField(20);
    private JTextArea encodeMessageArea = new JTextArea(10, 20);
    
    private JTextField decodePathField = new JTextField(20);
    private JTextField decodeKeyField = new JTextField(20);
    private JTextArea encryptedMessageArea = new JTextArea(10, 20);
    private JTextArea decryptedMessageArea = new JTextArea(10, 20);

    private File encodeFile;
    private File decodeFile;

    public GUI() {
        setTitle("Steganography Tool with Encryption");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createEncodePanel(), createDecodePanel());
        splitPane.setDividerLocation(450);
        add(splitPane);
    }

    private JPanel createEncodePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Encode (Hide Message)"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // File Selection
        gbc.gridy = 0;
        JButton selectBtn = new JButton("Select Cover Image");
        panel.add(selectBtn, gbc);

        gbc.gridy = 1;
        encodePathField.setEditable(false);
        panel.add(encodePathField, gbc);

        // Encryption Key
        gbc.gridy = 2;
        panel.add(new JLabel("Encryption Key:"), gbc);
        gbc.gridy = 3;
        panel.add(encodeKeyField, gbc);

        // Message Input
        gbc.gridy = 4;
        panel.add(new JLabel("Message to hide:"), gbc);

        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        panel.add(new JScrollPane(encodeMessageArea), gbc);

        // Action Button
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        JButton encodeBtn = new JButton("Encrypt & Hide");
        panel.add(encodeBtn, gbc);

        selectBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                encodeFile = chooser.getSelectedFile();
                encodePathField.setText(encodeFile.getAbsolutePath());
            }
        });

        encodeBtn.addActionListener(e -> handleEncode());

        return panel;
    }

    private JPanel createDecodePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Decode (Extract Message)"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // File Selection
        gbc.gridy = 0;
        JButton selectBtn = new JButton("Select Stego Image");
        panel.add(selectBtn, gbc);

        gbc.gridy = 1;
        decodePathField.setEditable(false);
        panel.add(decodePathField, gbc);

        // Decryption Key
        gbc.gridy = 2;
        panel.add(new JLabel("Decryption Key:"), gbc);
        gbc.gridy = 3;
        panel.add(decodeKeyField, gbc);

        // Action Button
        gbc.gridy = 4;
        JButton decodeBtn = new JButton("Extract & Decrypt");
        panel.add(decodeBtn, gbc);

        // Encrypted Output
        gbc.gridy = 5;
        panel.add(new JLabel("Extracted (Encrypted) Message:"), gbc);
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        encryptedMessageArea.setEditable(false);
        encryptedMessageArea.setLineWrap(true);
        panel.add(new JScrollPane(encryptedMessageArea), gbc);

        // Decrypted Output
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        panel.add(new JLabel("Decrypted Message:"), gbc);
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        decryptedMessageArea.setEditable(false);
        decryptedMessageArea.setLineWrap(true);
        panel.add(new JScrollPane(decryptedMessageArea), gbc);

        selectBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                decodeFile = chooser.getSelectedFile();
                decodePathField.setText(decodeFile.getAbsolutePath());
            }
        });

        decodeBtn.addActionListener(e -> handleDecode());

        return panel;
    }

    private void handleEncode() {
        if (encodeFile == null) {
            JOptionPane.showMessageDialog(this, "Please select an image first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String message = encodeMessageArea.getText();
        String key = encodeKeyField.getText();
        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a message.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (key.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an encryption key.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String encryptedMessage = CryptoUtils.encrypt(message, key);
        if (encryptedMessage == null) {
            JOptionPane.showMessageDialog(this, "Encryption failed.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File("encoded_image.png"));
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File saveFile = chooser.getSelectedFile();
            try {
                BufferedImage image = handler.loadImage(encodeFile.getAbsolutePath());
                lsb.encode(image, encryptedMessage);
                handler.saveImage(image, saveFile.getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Message encrypted and hidden successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleDecode() {
        if (decodeFile == null) {
            JOptionPane.showMessageDialog(this, "Please select a stego image first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String key = decodeKeyField.getText();
        if (key.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the decryption key.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            BufferedImage image = handler.loadImage(decodeFile.getAbsolutePath());
            String extractedMessage = lsb.decode(image);
            encryptedMessageArea.setText(extractedMessage);
            
            String decryptedMessage = CryptoUtils.decrypt(extractedMessage, key);
            if (decryptedMessage != null) {
                decryptedMessageArea.setText(decryptedMessage);
            } else {
                decryptedMessageArea.setText("[Decryption Failed - Possibly wrong key or no message]");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI().setVisible(true));
    }
}
