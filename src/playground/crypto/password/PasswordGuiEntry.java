package playground.crypto.password;

import javax.swing.*;
import java.util.Arrays;

public class PasswordGuiEntry {
    public static void main(String[] args) {
        final JFrame frame = new JFrame("JPasswordField");

        JLabel passwordLabel = new JLabel("Password:");
        final JPasswordField passwordField = new JPasswordField(20);
        passwordLabel.setLabelFor(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            // JTextField.getText(): String
            char[] password = passwordField.getPassword();
            JOptionPane.showMessageDialog(frame,
                    "Password length: " + password.length);
            Arrays.fill(password, ' ');
        });

        JPanel panel = new JPanel();

        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
