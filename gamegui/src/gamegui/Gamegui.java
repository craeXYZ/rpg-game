import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class GameGUI {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private Map<String, User> users;

    public GameGUI() {
        users = new HashMap<>(); // Initialize user map
        frame = new JFrame("RPG Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        addLoginPanel();
        addSignUpPanel();
        addMainMenuPanel();

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void addLoginPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton signUpButton = new JButton("Sign Up");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (users.containsKey(username) && users.get(username).checkPassword(password)) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    cardLayout.show(mainPanel, "Main Menu");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Username or Password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Sign Up");
            }
        });

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(signUpButton);

        mainPanel.add(loginPanel, "Login");
    }

    private void addSignUpPanel() {
        JPanel signUpPanel = new JPanel(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("New Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("New Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton createAccountButton = new JButton("Create Account");
        JButton backButton = new JButton("Back");

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (users.containsKey(username)) {
                    JOptionPane.showMessageDialog(frame, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    users.put(username, new User(username, password));
                    JOptionPane.showMessageDialog(frame, "Account Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    cardLayout.show(mainPanel, "Login");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Login");
            }
        });

        signUpPanel.add(usernameLabel);
        signUpPanel.add(usernameField);
        signUpPanel.add(passwordLabel);
        signUpPanel.add(passwordField);
        signUpPanel.add(createAccountButton);
        signUpPanel.add(backButton);

        mainPanel.add(signUpPanel, "Sign Up");
    }

    private void addMainMenuPanel() {
        JPanel mainMenuPanel = new JPanel(new GridLayout(3, 1));

        JButton characterButton = new JButton("Character Menu");
        JButton dungeonButton = new JButton("Dungeon Menu");
        JButton logoutButton = new JButton("Logout");

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Login");
            }
        });

        mainMenuPanel.add(characterButton);
        mainMenuPanel.add(dungeonButton);
        mainMenuPanel.add(logoutButton);

        mainPanel.add(mainMenuPanel, "Main Menu");
    }

    public static void main(String[] args) {
        new GameGUI();
    }
}

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}

