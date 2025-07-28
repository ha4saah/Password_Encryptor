# 🔐 Password Encryptor Pro

[![Java](https://img.shields.io/badge/Java-11+-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![Version](https://img.shields.io/badge/Version-2.0-blue.svg)](https://github.com/yourusername/password-encryptor)
[![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20Linux%20%7C%20macOS-lightgrey.svg)](https://github.com/yourusername/password-encryptor)

A secure, modern password encryption and decryption system with a beautiful dark cyber-themed GUI. Built with Java Swing, featuring AES-256 encryption and SHA-256 hashing for maximum security.

![Password Encryptor Pro](https://via.placeholder.com/800x400/121212/00FF7F?text=Password+Encryptor+Pro)

## ✨ Features

### 🔒 Security Features
- **AES-256 Encryption**: Military-grade encryption for passwords
- **SHA-256 Hashing**: Secure password hashing for user authentication
- **Unique Encryption Keys**: Each password gets its own encryption key
- **File-based Storage**: Secure local data persistence

### 🎨 User Interface
- **Dark Cyber Theme**: Sleek, modern dark interface with cyber aesthetics
- **Gradient Buttons**: Beautiful rounded buttons with gradient effects
- **Responsive Design**: Adapts to different screen sizes
- **Smooth Animations**: Hover effects and transitions
- **Professional UX**: Intuitive navigation and clear feedback

### 🚀 Functionality
- **User Registration & Login**: Secure authentication system
- **Password Encryption**: Encrypt passwords with unique keys
- **Password Decryption**: Decrypt passwords using saved keys
- **Password Management**: View and manage all encrypted passwords
- **Cross-Platform**: Works on Windows, Linux, and macOS

## 🛠️ Installation

### Prerequisites
- **Java Development Kit (JDK)**: Version 11 or higher
- **Java Runtime Environment (JRE)**: Included with JDK

### Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/password-encryptor.git
   cd password-encryptor
   ```

2. **Compile the application**
   ```bash
   javac PasswordEncryptorSwingGUI.java
   ```

3. **Run the application**
   ```bash
   java PasswordEncryptorSwingGUI
   ```

## 🎯 Usage Guide

### First Time Setup

1. **Launch the Application**
   - Run `java PasswordEncryptorSwingGUI`
   - Enjoy the beautiful dark cyber-themed welcome screen

2. **Create Your Account**
   - Click "Register" button
   - Enter a unique username
   - Enter and confirm your password
   - Experience smooth animations and transitions

3. **Login to Your Account**
   - Click "Login" button
   - Enter your credentials
   - Access your personalized dashboard

### Password Management

#### 🔐 Encrypting Passwords
1. Click "Encrypt Password" from the dashboard
2. Enter the password you want to encrypt
3. Click "Encrypt" button
4. **IMPORTANT**: Save the encryption key displayed
5. Your password is now securely encrypted and stored

#### 🔓 Decrypting Passwords
1. Click "Decrypt Password" from the dashboard
2. Select which encrypted password to decrypt
3. Enter the encryption key you saved earlier
4. View your decrypted password

#### 📋 Viewing Passwords
1. Click "View Passwords" from the dashboard
2. See all your encrypted passwords listed
3. Use this to identify which passwords you want to decrypt

## 🎨 Screenshots

### Welcome Screen
```
┌─────────────────────────────────────┐
│        Password Encryptor Pro       │
│    Secure Password Management       │
│                                     │
│    [Login] [Register] [Exit]       │
└─────────────────────────────────────┘
```

### Dashboard
```
┌─────────────────────────────────────┐
│        Welcome, username!           │
│                                     │
│    [Encrypt Password]               │
│    [Decrypt Password]               │
│    [View Passwords]                 │
│    [Logout]                         │
└─────────────────────────────────────┘
```

## 🔧 Technical Details

### Architecture
- **Frontend**: Java Swing with custom rendering
- **Backend**: Pure Java with cryptographic libraries
- **Storage**: Text-based files with pipe-separated values
- **Security**: AES-256 encryption, SHA-256 hashing

### File Structure
```
Password_Encryptor/
├── PasswordEncryptorSwingGUI.java    # Main GUI application
├── PasswordEncryptorSwingGUI.class   # Compiled GUI
├── Main.java                         # Console version (backup)
├── Main.class                        # Compiled console version
├── README.md                         # This documentation
├── users.txt                         # User credentials (hashed)
└── encrypted_passwords.txt           # Encrypted passwords data
```

### Data Format
- **users.txt**: `username|hashed_password`
- **encrypted_passwords.txt**: `username|encrypted_password|encryption_key`

### Color Scheme
- **Background**: Deep black (#121212)
- **Primary Text**: Cyber green (#00FF7F)
- **Secondary Text**: Light gray (#A9A9A9)
- **Accent Colors**: Purple (#8A2BE2), Orange (#FF4500), Red (#DC143C)

## 🔒 Security Features

### Encryption
- **Algorithm**: AES-256 (Advanced Encryption Standard)
- **Key Generation**: Cryptographically secure random keys
- **Mode**: AES in ECB mode with Base64 encoding
- **Key Storage**: Each password has its own unique key

### Authentication
- **Hashing**: SHA-256 with salt
- **Password Storage**: Hashed passwords only
- **Session Management**: Local user sessions
- **File Security**: Plain text files (consider encryption for production)

### Best Practices
- ✅ Unique encryption keys for each password
- ✅ Secure password hashing
- ✅ Input validation and sanitization
- ✅ Error handling and user feedback
- ✅ Clean, maintainable code structure

## 🚀 Development

# Compile
javac PasswordEncryptorSwingGUI.java

# Run
java PasswordEncryptorSwingGUI
```

### Dependencies
- **Java Standard Library**: All required libraries included
- **No External Dependencies**: Pure Java implementation
- **Cross-Platform**: Works on any system with Java 11+

### Code Structure
```java
// Main application class
public class PasswordEncryptorSwingGUI extends JFrame {
    // GUI components and event handling
}

// Key methods:
- authenticateUser()     // User authentication
- registerUser()        // User registration
- encryptPassword()     // AES encryption
- decryptPassword()     // AES decryption
- hashPassword()        // SHA-256 hashing
```

## 🤝 Contributing

We welcome contributions! Please feel free to submit a Pull Request.

### How to Contribute
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Development Guidelines
- Follow Java coding conventions
- Add comments for complex logic
- Test thoroughly before submitting
- Update documentation as needed

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **Java Swing**: For the GUI framework
- **Java Cryptography**: For AES and SHA implementations
- **Open Source Community**: For inspiration and best practices


## 🔄 Version History

- **v2.0** - Dark cyber theme, gradient buttons, improved UX
- **v1.1** - Console version with full functionality
- **v1.0** - Initial release with basic encryption

---

**Made with ❤️ and ☕ by Hafsah Younis**

*Remember: Keep your encryption keys safe - without them, you cannot decrypt your passwords!* 