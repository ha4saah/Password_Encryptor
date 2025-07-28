import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.*;
import java.util.Base64;

public class Main {
    private static final String USER_FILE = "users.txt";
    private static final String ENCRYPTED_PASSWORDS_FILE = "encrypted_passwords.txt";
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== Password Encryption & Decryption System ===");
        
        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private static void login() {
        System.out.println("\n=== Login ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        if (authenticateUser(username, password)) {
            System.out.println("Login successful!");
            showUserMenu(username);
        } else {
            System.out.println("Invalid username or password!");
        }
    }
    
    private static void register() {
        System.out.println("\n=== Registration ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        if (userExists(username)) {
            System.out.println("Username already exists!");
            return;
        }
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Confirm password: ");
        String confirmPassword = scanner.nextLine();
        
        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match!");
            return;
        }
        
        if (registerUser(username, password)) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed!");
        }
    }
    
    private static void showUserMenu(String username) {
        while (true) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. Encrypt a password");
            System.out.println("2. Decrypt a password");
            System.out.println("3. View my encrypted passwords");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    encryptPassword(username);
                    break;
                case 2:
                    decryptPassword(username);
                    break;
                case 3:
                    viewEncryptedPasswords(username);
                    break;
                case 4:
                    System.out.println("Logged out successfully!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private static void encryptPassword(String username) {
        System.out.println("\n=== Encrypt Password ===");
        System.out.print("Enter the password to encrypt: ");
        String passwordToEncrypt = scanner.nextLine();
        
        try {
            // Generate encryption key
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            SecretKey secretKey = keyGen.generateKey();
            
            // Encrypt the password
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(passwordToEncrypt.getBytes(StandardCharsets.UTF_8));
            String encryptedPassword = Base64.getEncoder().encodeToString(encryptedBytes);
            String encryptionKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            
            // Save encrypted password and key
            saveEncryptedPassword(username, encryptedPassword, encryptionKey);
            
            System.out.println("Password encrypted successfully!");
            System.out.println("Encrypted password: " + encryptedPassword);
            System.out.println("Encryption key: " + encryptionKey);
            System.out.println("IMPORTANT: Save this key to decrypt your password later!");
            
        } catch (Exception e) {
            System.out.println("Encryption failed: " + e.getMessage());
        }
    }
    
    private static void decryptPassword(String username) {
        System.out.println("\n=== Decrypt Password ===");
        
        // Show available encrypted passwords
        List<String> encryptedPasswords = getEncryptedPasswords(username);
        if (encryptedPasswords.isEmpty()) {
            System.out.println("No encrypted passwords found for this user.");
            return;
        }
        
        System.out.println("Your encrypted passwords:");
        for (int i = 0; i < encryptedPasswords.size(); i++) {
            String[] parts = encryptedPasswords.get(i).split("\\|");
            System.out.println((i + 1) + ". " + parts[0]);
        }
        
        System.out.print("Select password to decrypt (enter number): ");
        int selection = getIntInput() - 1;
        
        if (selection < 0 || selection >= encryptedPasswords.size()) {
            System.out.println("Invalid selection!");
            return;
        }
        
        String[] parts = encryptedPasswords.get(selection).split("\\|");
        String encryptedPassword = parts[0];
        String encryptionKey = parts[1];
        
        System.out.print("Enter the encryption key: ");
        String inputKey = scanner.nextLine();
        
        if (!inputKey.equals(encryptionKey)) {
            System.out.println("Incorrect encryption key!");
            return;
        }
        
        try {
            // Decrypt the password
            byte[] keyBytes = Base64.getDecoder().decode(encryptionKey);
            SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
            
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedPassword);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            String decryptedPassword = new String(decryptedBytes, StandardCharsets.UTF_8);
            
            System.out.println("Password decrypted successfully!");
            System.out.println("Decrypted password: " + decryptedPassword);
            
        } catch (Exception e) {
            System.out.println("Decryption failed: " + e.getMessage());
        }
    }
    
    private static void viewEncryptedPasswords(String username) {
        System.out.println("\n=== Your Encrypted Passwords ===");
        List<String> encryptedPasswords = getEncryptedPasswords(username);
        
        if (encryptedPasswords.isEmpty()) {
            System.out.println("No encrypted passwords found.");
            return;
        }
        
        for (int i = 0; i < encryptedPasswords.size(); i++) {
            String[] parts = encryptedPasswords.get(i).split("\\|");
            System.out.println((i + 1) + ". Encrypted: " + parts[0]);
        }
    }
    
    private static boolean authenticateUser(String username, String password) {
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
    
    private static boolean userExists(String username) {
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
    
    private static boolean registerUser(String username, String password) {
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
    
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            return password; // Fallback to plain text if hashing fails
        }
    }
    
    private static void saveEncryptedPassword(String username, String encryptedPassword, String encryptionKey) {
        try (FileWriter writer = new FileWriter(ENCRYPTED_PASSWORDS_FILE, true);
             BufferedWriter bw = new BufferedWriter(writer);
             PrintWriter out = new PrintWriter(bw)) {
            
            out.println(username + "|" + encryptedPassword + "|" + encryptionKey);
            
        } catch (IOException e) {
            System.out.println("Error saving encrypted password: " + e.getMessage());
        }
    }
    
    private static List<String> getEncryptedPasswords(String username) {
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
    
    private static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}
