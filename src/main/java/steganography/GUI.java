package steganography;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class GUI extends JFrame {
    private LSBCore lsb = new LSBCore();
    private ImageHandler handler = new ImageHandler();

    private JTextField encodePathField = new JTextField(20);
    private JTextArea encodeMessageArea = new JTextArea(10, 20);
    private JTextField decodePathField = new JTextField(20);
    private JTextArea decodeMessageArea = new JTextArea(10, 20);

    private File encodeFile;
    private File decodeFile;

    public GUI() {
        setTitle("Steganography Tool");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createEncodePanel(), createDecodePanel());
        splitPane.setDividerLocation(400);
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

        // Message Input
        gbc.gridy = 2;
        panel.add(new JLabel("Message to hide:"), gbc);

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        panel.add(new JScrollPane(encodeMessageArea), gbc);

        // Action Button
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        JButton encodeBtn = new JButton("Hide & Save");
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

        // Action Button
        gbc.gridy = 2;
        JButton decodeBtn = new JButton("Decode Message");
        panel.add(decodeBtn, gbc);

        // Message Output
        gbc.gridy = 3;
        panel.add(new JLabel("Decoded Message:"), gbc);

        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        decodeMessageArea.setEditable(false);
        panel.add(new JScrollPane(decodeMessageArea), gbc);

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
        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a message.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File("encoded_image.png"));
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File saveFile = chooser.getSelectedFile();
            try {
                BufferedImage image = handler.loadImage(encodeFile.getAbsolutePath());
                lsb.encode(image, message);
                handler.saveImage(image, saveFile.getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Message hidden successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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

        try {
            BufferedImage image = handler.loadImage(decodeFile.getAbsolutePath());
            String message = lsb.decode(image);
            decodeMessageArea.setText(message);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI().setVisible(true));
    }
}
