import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class PasswordEncryptorSwingGUI extends JFrame {
    private static final String USER_FILE = "users.txt";
    private static final String ENCRYPTED_PASSWORDS_FILE = "encrypted_passwords.txt";
    
    private String currentUser;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    public PasswordEncryptorSwingGUI() {
        setTitle("Password Encryptor Pro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Create main panel with card layout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(new Color(18, 18, 18)); // Dark cyber theme
        
        // Create screens
        createWelcomeScreen();
        createLoginScreen();
        createRegisterScreen();
        createDashboardScreen();
        
        add(mainPanel);
        showWelcomeScreen();
    }
    
    private void createWelcomeScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(18, 18, 18));
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));
        
        // Title
        JLabel titleLabel = new JLabel("Password Encryptor Pro");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 255, 127));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Secure Password Management System");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(169, 169, 169));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Buttons
        JButton loginBtn = createModernButton("Login", new Color(0, 255, 127));
        JButton registerBtn = createModernButton("Register", new Color(138, 43, 226));
        JButton exitBtn = createModernButton("Exit", new Color(220, 20, 60));
        
        loginBtn.addActionListener(e -> showLoginScreen());
        registerBtn.addActionListener(e -> showRegisterScreen());
        exitBtn.addActionListener(e -> System.exit(0));
        
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(subtitleLabel);
        panel.add(Box.createVerticalStrut(40));
        panel.add(loginBtn);
        panel.add(Box.createVerticalStrut(15));
        panel.add(registerBtn);
        panel.add(Box.createVerticalStrut(15));
        panel.add(exitBtn);
        panel.add(Box.createVerticalGlue());
        
        mainPanel.add(panel, "welcome");
    }
    
    private void createLoginScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(18, 18, 18));
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));
        
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(0, 255, 127));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextField usernameField = createModernTextField("Enter Username");
        JPasswordField passwordField = createModernPasswordField("Enter Password");
        
        JButton loginBtn = createModernButton("Login", new Color(0, 255, 127));
        JButton backBtn = createModernButton("Back", new Color(64, 64, 64));
        
        loginBtn.addActionListener(e -> {
            if (authenticateUser(usernameField.getText(), new String(passwordField.getPassword()))) {
                currentUser = usernameField.getText();
                showDashboardScreen();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        backBtn.addActionListener(e -> showWelcomeScreen());
        
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(15));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(loginBtn);
        panel.add(Box.createVerticalStrut(15));
        panel.add(backBtn);
        panel.add(Box.createVerticalGlue());
        
        mainPanel.add(panel, "login");
    }
    
    private void createRegisterScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(18, 18, 18));
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));
        
        JLabel titleLabel = new JLabel("Register");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(0, 255, 127));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextField usernameField = createModernTextField("Enter Username");
        JPasswordField passwordField = createModernPasswordField("Enter Password");
        JPasswordField confirmPasswordField = createModernPasswordField("Re-enter Password");
        
        JButton registerBtn = createModernButton("Register", new Color(0, 255, 127));
        JButton backBtn = createModernButton("Back", new Color(64, 64, 64));
        
        registerBtn.addActionListener(e -> {
            if (userExists(usernameField.getText())) {
                JOptionPane.showMessageDialog(this, "Username already exists!", "Registration Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!Arrays.equals(passwordField.getPassword(), confirmPasswordField.getPassword())) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!", "Registration Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (registerUser(usernameField.getText(), new String(passwordField.getPassword()))) {
                JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                showWelcomeScreen();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to register user!", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        backBtn.addActionListener(e -> showWelcomeScreen());
        
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(15));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(15));
        panel.add(confirmPasswordField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(registerBtn);
        panel.add(Box.createVerticalStrut(15));
        panel.add(backBtn);
        panel.add(Box.createVerticalGlue());
        
        mainPanel.add(panel, "register");
    }
    
    private void createDashboardScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(18, 18, 18));
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));
        
        JLabel welcomeLabel = new JLabel("Welcome, " + (currentUser != null ? currentUser : "User") + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(0, 255, 127)); // Cyber green
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton encryptBtn = createModernButton("Encrypt Password", new Color(0, 255, 127));
        JButton decryptBtn = createModernButton("Decrypt Password", new Color(255, 69, 0));
        JButton viewBtn = createModernButton("View Passwords", new Color(138, 43, 226));
        JButton logoutBtn = createModernButton("Logout", new Color(220, 20, 60));
        
        encryptBtn.addActionListener(e -> {
            createEncryptScreen();
            showEncryptScreen();
        });
        decryptBtn.addActionListener(e -> {
            createDecryptScreen();
            showDecryptScreen();
        });
        viewBtn.addActionListener(e -> {
            createViewPasswordsScreen();
            showViewPasswordsScreen();
        });
        logoutBtn.addActionListener(e -> {
            currentUser = null;
            showWelcomeScreen();
        });
        
        panel.add(Box.createVerticalGlue());
        panel.add(welcomeLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(encryptBtn);
        panel.add(Box.createVerticalStrut(15));
        panel.add(decryptBtn);
        panel.add(Box.createVerticalStrut(15));
        panel.add(viewBtn);
        panel.add(Box.createVerticalStrut(15));
        panel.add(logoutBtn);
        panel.add(Box.createVerticalGlue());
        
        mainPanel.add(panel, "dashboard");
    }
    
    private void createEncryptScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(18, 18, 18));
        panel.setBorder(new EmptyBorder(30, 50, 30, 50));
        
        JLabel titleLabel = new JLabel("Encrypt Password");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 255, 127));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPasswordField passwordField = createModernPasswordField("Enter Password to Encrypt");
        JTextArea resultArea = new JTextArea(8, 40);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setBackground(new Color(30, 30, 30));
        resultArea.setForeground(new Color(0, 255, 127));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        
        JButton encryptBtn = createModernButton("Encrypt", new Color(0, 255, 127));
        JButton backBtn = createModernButton("Back", new Color(64, 64, 64));
        
        encryptBtn.addActionListener(e -> {
            try {
                String password = new String(passwordField.getPassword());
                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a password to encrypt!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Generate encryption key
                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                keyGen.init(256);
                SecretKey secretKey = keyGen.generateKey();
                
                // Encrypt the password
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] encryptedBytes = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
                String encryptedPassword = Base64.getEncoder().encodeToString(encryptedBytes);
                String encryptionKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
                
                // Save encrypted password and key
                saveEncryptedPassword(currentUser, encryptedPassword, encryptionKey);
                
                resultArea.setText("Password encrypted successfully!\n\n" +
                    "Encrypted Password:\n" + encryptedPassword + "\n\n" +
                    "Encryption Key:\n" + encryptionKey + "\n\n" +
                    "IMPORTANT: Save this key to decrypt your password later!");
                
                JOptionPane.showMessageDialog(this, "Password encrypted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Encryption failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(encryptBtn);
        panel.add(Box.createVerticalStrut(20));
        panel.add(scrollPane);
        panel.add(Box.createVerticalStrut(20));
        panel.add(backBtn);
        
        mainPanel.add(panel, "encrypt");
    }
    
    private void createDecryptScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(18, 18, 18));
        panel.setBorder(new EmptyBorder(30, 50, 30, 50));
        
        JLabel titleLabel = new JLabel("Decrypt Password");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 255, 127));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Get encrypted passwords
        List<String> encryptedPasswords = getEncryptedPasswords(currentUser);
        if (encryptedPasswords.isEmpty()) {
            JLabel noPasswordsLabel = new JLabel("No encrypted passwords found.");
            noPasswordsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            noPasswordsLabel.setForeground(Color.WHITE);
            noPasswordsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JButton backBtn = createModernButton("Back", new Color(117, 117, 117));
            backBtn.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));
            
            panel.add(titleLabel);
            panel.add(Box.createVerticalStrut(30));
            panel.add(noPasswordsLabel);
            panel.add(Box.createVerticalStrut(30));
            panel.add(backBtn);
        } else {
            String[] passwordOptions = new String[encryptedPasswords.size()];
            for (int i = 0; i < encryptedPasswords.size(); i++) {
                passwordOptions[i] = "Password " + (i + 1);
            }
            
            JComboBox<String> passwordCombo = new JComboBox<>(passwordOptions);
            passwordCombo.setPreferredSize(new Dimension(300, 35));
            passwordCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            
            JPasswordField keyField = createModernPasswordField("Enter Encryption Key");
            JTextArea resultArea = new JTextArea(5, 40);
            resultArea.setEditable(false);
            resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            resultArea.setBackground(new Color(30, 30, 30));
            resultArea.setForeground(new Color(0, 255, 127));
            JScrollPane scrollPane = new JScrollPane(resultArea);
            scrollPane.setPreferredSize(new Dimension(400, 150));
            
            JButton decryptBtn = createModernButton("Decrypt", new Color(255, 69, 0));
            JButton backBtn = createModernButton("Back", new Color(64, 64, 64));
            
            decryptBtn.addActionListener(e -> {
                try {
                    int selection = passwordCombo.getSelectedIndex();
                    if (selection == -1) {
                        JOptionPane.showMessageDialog(this, "Please select a password to decrypt!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    String[] parts = encryptedPasswords.get(selection).split("\\|");
                    String encryptedPassword = parts[0];
                    String encryptionKey = parts[1];
                    
                    if (!new String(keyField.getPassword()).equals(encryptionKey)) {
                        JOptionPane.showMessageDialog(this, "Incorrect encryption key!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    // Decrypt the password
                    byte[] keyBytes = Base64.getDecoder().decode(encryptionKey);
                    SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
                    
                    Cipher cipher = Cipher.getInstance("AES");
                    cipher.init(Cipher.DECRYPT_MODE, secretKey);
                    
                    byte[] encryptedBytes = Base64.getDecoder().decode(encryptedPassword);
                    byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
                    String decryptedPassword = new String(decryptedBytes, StandardCharsets.UTF_8);
                    
                    resultArea.setText("Password decrypted successfully!\n\n" +
                        "Decrypted Password:\n" + decryptedPassword);
                    
                    JOptionPane.showMessageDialog(this, "Password decrypted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Decryption failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            
            backBtn.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));
            
            panel.add(titleLabel);
            panel.add(Box.createVerticalStrut(20));
            panel.add(passwordCombo);
            panel.add(Box.createVerticalStrut(15));
            panel.add(keyField);
            panel.add(Box.createVerticalStrut(20));
            panel.add(decryptBtn);
            panel.add(Box.createVerticalStrut(20));
            panel.add(scrollPane);
            panel.add(Box.createVerticalStrut(20));
            panel.add(backBtn);
        }
        
        mainPanel.add(panel, "decrypt");
    }
    
    private void createViewPasswordsScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(18, 18, 18));
        panel.setBorder(new EmptyBorder(30, 50, 30, 50));
        
        JLabel titleLabel = new JLabel("Your Encrypted Passwords");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 255, 127));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        List<String> encryptedPasswords = getEncryptedPasswords(currentUser);
        if (encryptedPasswords.isEmpty()) {
            JLabel noPasswordsLabel = new JLabel("No encrypted passwords found.");
            noPasswordsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            noPasswordsLabel.setForeground(Color.WHITE);
            noPasswordsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JButton backBtn = createModernButton("Back", new Color(117, 117, 117));
            backBtn.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));
            
            panel.add(titleLabel);
            panel.add(Box.createVerticalStrut(30));
            panel.add(noPasswordsLabel);
            panel.add(Box.createVerticalStrut(30));
            panel.add(backBtn);
        } else {
            JTextArea passwordsArea = new JTextArea(15, 50);
            passwordsArea.setEditable(false);
            passwordsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            passwordsArea.setBackground(new Color(30, 30, 30));
            passwordsArea.setForeground(new Color(0, 255, 127));
            JScrollPane scrollPane = new JScrollPane(passwordsArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));
            
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < encryptedPasswords.size(); i++) {
                String[] parts = encryptedPasswords.get(i).split("\\|");
                sb.append("Password ").append(i + 1).append(":\n");
                sb.append("Encrypted: ").append(parts[0]).append("\n\n");
            }
            passwordsArea.setText(sb.toString());
            
            JButton backBtn = createModernButton("Back", new Color(117, 117, 117));
            backBtn.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));
            
            panel.add(titleLabel);
            panel.add(Box.createVerticalStrut(20));
            panel.add(scrollPane);
            panel.add(Box.createVerticalStrut(20));
            panel.add(backBtn);
        }
        
        mainPanel.add(panel, "view");
    }
    
    private JButton createModernButton(String text, Color color) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Create gradient background
                GradientPaint gradient = new GradientPaint(0, 0, color, getWidth(), getHeight(), color.darker());
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                
                // Draw text
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), textX, textY);
                
                g2d.dispose();
            }
        };
        
        button.setPreferredSize(new Dimension(250, 50));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 0, 0, 0)); // Transparent
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Hover effect with gradient
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 0, 0, 0));
                button.repaint();
            }
            public void mousePressed(MouseEvent e) {
                button.setBackground(new Color(0, 0, 0, 0));
                button.repaint();
            }
            public void mouseReleased(MouseEvent e) {
                button.setBackground(new Color(0, 0, 0, 0));
                button.repaint();
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 0, 0, 0));
                button.repaint();
            }
        });
        
        return button;
    }
    
    private JTextField createModernTextField(String prompt) {
        JTextField field = new JTextField(20);
        field.setPreferredSize(new Dimension(300, 45));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        field.setBackground(new Color(30, 30, 30));
        field.setForeground(new Color(0, 255, 127));
        field.setCaretColor(new Color(0, 255, 127));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(64, 64, 64), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }
    
    private JPasswordField createModernPasswordField(String prompt) {
        JPasswordField field = new JPasswordField(20);
        field.setPreferredSize(new Dimension(300, 45));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        field.setBackground(new Color(30, 30, 30));
        field.setForeground(new Color(0, 255, 127));
        field.setCaretColor(new Color(0, 255, 127));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(64, 64, 64), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }
    
    private void showWelcomeScreen() {
        cardLayout.show(mainPanel, "welcome");
    }
    
    private void showLoginScreen() {
        cardLayout.show(mainPanel, "login");
    }
    
    private void showRegisterScreen() {
        cardLayout.show(mainPanel, "register");
    }
    
    private void showDashboardScreen() {
        cardLayout.show(mainPanel, "dashboard");
    }
    
    private void showEncryptScreen() {
        cardLayout.show(mainPanel, "encrypt");
    }
    
    private void showDecryptScreen() {
        cardLayout.show(mainPanel, "decrypt");
    }
    
    private void showViewPasswordsScreen() {
        cardLayout.show(mainPanel, "view");
    }
    
    // Backend methods (same as console version)
    private boolean authenticateUser(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2 && parts[0].equals(username)) {
                    String storedHash = parts[1];
                    String inputHash = hashPassword(password);
                    return storedHash.equals(inputHash);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user file: " + e.getMessage());
        }
        return false;
    }
    
    private boolean userExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length > 0 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // File doesn't exist yet, so user doesn't exist
        }
        return false;
    }
    
    private boolean registerUser(String username, String password) {
        try (FileWriter writer = new FileWriter(USER_FILE, true);
             BufferedWriter bw = new BufferedWriter(writer);
             PrintWriter out = new PrintWriter(bw)) {
            
            String hashedPassword = hashPassword(password);
            out.println(username + "|" + hashedPassword);
            return true;
            
        } catch (IOException e) {
            System.out.println("Error registering user: " + e.getMessage());
            return false;
        }
    }
    
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            return password; // Fallback to plain text if hashing fails
        }
    }
    
    private void saveEncryptedPassword(String username, String encryptedPassword, String encryptionKey) {
        try (FileWriter writer = new FileWriter(ENCRYPTED_PASSWORDS_FILE, true);
             BufferedWriter bw = new BufferedWriter(writer);
             PrintWriter out = new PrintWriter(bw)) {
            
            out.println(username + "|" + encryptedPassword + "|" + encryptionKey);
            
        } catch (IOException e) {
            System.out.println("Error saving encrypted password: " + e.getMessage());
        }
    }
    
    private List<String> getEncryptedPasswords(String username) {
        List<String> passwords = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ENCRYPTED_PASSWORDS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3 && parts[0].equals(username)) {
                    passwords.add(parts[1] + "|" + parts[2]);
                }
            }
        } catch (IOException e) {
            // File doesn't exist yet
        }
        return passwords;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PasswordEncryptorSwingGUI().setVisible(true);
        });
    }
}