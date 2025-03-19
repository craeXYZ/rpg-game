import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameGUI {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private Map<String, User> users;
    private Character playerCharacter;
    private List<List<Monster>> dungeons;

    public GameGUI() {
        users = new HashMap<>(); // Initialize user map
        playerCharacter = new Character(); // Initialize player character
        dungeons = new ArrayList<>(); // Initialize dungeons
        initializeDungeons();

        frame = new JFrame("RPG Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        addLoginPanel();
        addSignUpPanel();
        addMainMenuPanel();
        addCharacterMenuPanel();
        addDungeonMenuPanel();

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void initializeDungeons() {
        List<Monster> dungeon1 = new ArrayList<>();
        dungeon1.add(new Monster("Goblin", 50, 10, 5, "Wooden Sword"));
        dungeon1.add(new Monster("Rat", 30, 5, 2, "Health Potion"));

        List<Monster> dungeon2 = new ArrayList<>();
        dungeon2.add(new Monster("Orc", 80, 15, 10, "Steel Shield"));
        dungeon2.add(new Monster("Wolf", 60, 12, 8, "Leather Armor"));

        dungeons.add(dungeon1);
        dungeons.add(dungeon2);
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

        characterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Character Menu");
            }
        });

        dungeonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Dungeon Menu");
            }
        });

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

    private void addCharacterMenuPanel() {
        JPanel characterMenuPanel = new JPanel(new GridLayout(4, 1));

        JTextArea statsArea = new JTextArea(playerCharacter.getStats());
        statsArea.setEditable(false);

        JButton addItemButton = new JButton("Add Item");
        JButton removeItemButton = new JButton("Remove Item");
        JButton backButton = new JButton("Back");

        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String item = JOptionPane.showInputDialog(frame, "Enter item to add:");
                if (item != null && !item.isEmpty()) {
                    playerCharacter.addItem(item);
                    statsArea.setText(playerCharacter.getStats());
                }
            }
        });

        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String item = JOptionPane.showInputDialog(frame, "Enter item to remove:");
                if (item != null && !item.isEmpty()) {
                    playerCharacter.removeItem(item);
                    statsArea.setText(playerCharacter.getStats());
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Main Menu");
            }
        });

        characterMenuPanel.add(new JScrollPane(statsArea));
        characterMenuPanel.add(addItemButton);
        characterMenuPanel.add(removeItemButton);
        characterMenuPanel.add(backButton);

        mainPanel.add(characterMenuPanel, "Character Menu");
    }

    private void addDungeonMenuPanel() {
        JPanel dungeonMenuPanel = new JPanel(new GridLayout(3, 1));

        JTextArea dungeonArea = new JTextArea("Available Dungeons:\n1. Goblin Cave\n2. Orc Fortress");
        dungeonArea.setEditable(false);

        JButton enterDungeonButton = new JButton("Enter Dungeon");
        JButton backButton = new JButton("Back");

        enterDungeonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(frame, "Enter Dungeon Level (1 or 2):");
                try {
                    int level = Integer.parseInt(input) - 1;
                    if (level >= 0 && level < dungeons.size()) {
                        startBattle(dungeons.get(level));
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid Dungeon Level!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Main Menu");
            }
        });

        dungeonMenuPanel.add(new JScrollPane(dungeonArea));
        dungeonMenuPanel.add(enterDungeonButton);
        dungeonMenuPanel.add(backButton);

        mainPanel.add(dungeonMenuPanel, "Dungeon Menu");
    }

    private void startBattle(List<Monster> monsters) {
        JFrame battleFrame = new JFrame("Battle");
        battleFrame.setSize(600, 400);
        battleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel battlePanel = new JPanel(new BorderLayout());

        JTextArea battleLogArea = new JTextArea();
        battleLogArea.setEditable(false);

        JLabel playerHealthLabel = new JLabel("Your Health: " + playerCharacter.getHealth());
        JLabel monsterLabel = new JLabel();
        JLabel monsterHealthLabel = new JLabel();

        JButton attackButton = new JButton("Attack");
        JButton runButton = new JButton("Run");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(attackButton);
        buttonPanel.add(runButton);

        Monster currentMonster = monsters.get(0);
        monsterLabel.setText("Monster: " + currentMonster.getName());
        monsterHealthLabel.setText("Monster Health: " + currentMonster.getHealth());

        attackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int damage = Math.max(1, playerCharacter.getAttack() - currentMonster.getDefense());
                currentMonster.setHealth(currentMonster.getHealth() - damage);
                battleLogArea.append("You dealt " + damage + " damage to " + currentMonster.getName() + "\n");

                if (currentMonster.getHealth() <= 0) {
                    battleLogArea.append("You defeated the " + currentMonster.getName() + " and received " + currentMonster.getReward() + "!\n");
                    playerCharacter.addItem(currentMonster.getReward());

                    monsters.remove(0);
                    if (monsters.isEmpty()) {
                        battleLogArea.append("\nYou cleared the dungeon!\n");
                        attackButton.setEnabled(false);
                        runButton.setText("Close");
                    } else {
                        Monster nextMonster = monsters.get(0);
                        currentMonster.setHealth(nextMonster.getHealth());
                        monsterLabel.setText("Monster: " + nextMonster.getName());
                        monsterHealthLabel.setText("Monster Health: " + nextMonster.getHealth());
                    }
                } else {
                    int monsterDamage = Math.max(1, currentMonster.getAttack() - playerCharacter.getDefense());
                    playerCharacter.setHealth(playerCharacter.getHealth() - monsterDamage);
                    playerHealthLabel.setText("Your Health: " + playerCharacter.getHealth());
                    battleLogArea.append(currentMonster.getName() + " dealt " + monsterDamage + " damage to you!\n");
                }

                monsterHealthLabel.setText("Monster Health: " + currentMonster.getHealth());
            }
        });

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                battleFrame.dispose();
            }
        });

        battlePanel.add(new JScrollPane(battleLogArea), BorderLayout.CENTER);
        battlePanel.add(playerHealthLabel, BorderLayout.NORTH);
        battlePanel.add(monsterLabel, BorderLayout.WEST);
        battlePanel.add(monsterHealthLabel, BorderLayout.EAST);
        battlePanel.add(buttonPanel, BorderLayout.SOUTH);

        battleFrame.add(battlePanel);
        battleFrame.setVisible(true);
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

class Character {
    private int health;
    private int attack;
    private int defense;
    private List<String> inventory;

    public Character() {
        this.health = 100;
        this.attack = 10;
        this.defense = 5;
        this.inventory = new ArrayList<>();
    }

    public String getStats() {
        return "Health: " + health + "\n" +
               "Attack: " + attack + "\n" +
               "Defense: " + defense + "\n" +
               "Inventory: " + inventory;
    }

    public void addItem(String item) {
        inventory.add(item);
    }

    public void removeItem(String item) {
        inventory.remove(item);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }
}

class Monster {
    private String name;
    private int health;
    private int attack;
    private int defense;
    private String reward;

    public Monster(String name, int health, int attack, int defense, String reward) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.reward = reward;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public String getReward() {
        return reward;
    }
}
