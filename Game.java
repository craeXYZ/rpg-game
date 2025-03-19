import java.util.*;

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
    private String name;
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

    public void displayStats() {
        System.out.println("\nCharacter Stats:");
        System.out.println("Health: " + health);
        System.out.println("Attack: " + attack);
        System.out.println("Defense: " + defense);
        System.out.println("\nInventory: " + inventory);
    }

    public void addItem(String item) {
        inventory.add(item);
        System.out.println(item + " added to inventory!");
    }

    public void removeItem(String item) {
        if (inventory.remove(item)) {
            System.out.println(item + " removed from inventory!");
        } else {
            System.out.println("Item not found in inventory!");
        }
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getReward() {
        return reward;
    }
}

public class Game {
    private static Map<String, User> users = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static Character playerCharacter;
    private static Map<Integer, List<Monster>> dungeons = new HashMap<>();

    public static void main(String[] args) {
        initializeDungeons();
        while (true) {
            System.out.println("\n=== Welcome to the RPG Game ===");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    if (login()) {
                        mainMenu();
                    }
                    break;
                case 2:
                    signUp();
                    break;
                case 3:
                    System.out.println("Thanks for playing!");
                    return;
            }
        }
    }

    private static void initializeDungeons() {
        // Initialize dungeon level 1
        List<Monster> dungeon1 = new ArrayList<>();
        dungeon1.add(new Monster("Goblin", 50, 5, 2, "Wooden Sword"));
        dungeon1.add(new Monster("Rat", 30, 3, 1, "Health Potion"));
        dungeons.put(1, dungeon1);

        // Initialize dungeon level 2
        List<Monster> dungeon2 = new ArrayList<>();
        dungeon2.add(new Monster("Orc", 80, 8, 4, "Steel Sword"));
        dungeon2.add(new Monster("Wolf", 60, 6, 3, "Leather Armor"));
        dungeons.put(2, dungeon2);
    }

    private static boolean login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            System.out.println("Login successful!");
            playerCharacter = new Character();
            return true;
        }
        System.out.println("Invalid username or password!");
        return false;
    }

    private static void signUp() {
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        if (users.containsKey(username)) {
            System.out.println("Username already exists!");
            return;
        }
        System.out.print("Enter new password: ");
        String password = scanner.nextLine();
        users.put(username, new User(username, password));
        System.out.println("Account created successfully!");
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Character");
            System.out.println("2. Dungeon");
            System.out.println("3. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    characterMenu();
                    break;
                case 2:
                    dungeonMenu();
                    break;
                case 3:
                    return;
            }
        }
    }

    private static void characterMenu() {
        while (true) {
            System.out.println("\n=== Character Menu ===");
            playerCharacter.displayStats();
            System.out.println("\n1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Back to Main Menu");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter item name to add: ");
                    playerCharacter.addItem(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Enter item name to remove: ");
                    playerCharacter.removeItem(scanner.nextLine());
                    break;
                case 3:
                    return;
            }
        }
    }

    private static void dungeonMenu() {
        while (true) {
            System.out.println("\n=== Dungeon Menu ===");
            System.out.println("Available Dungeons:");
            for (Map.Entry<Integer, List<Monster>> entry : dungeons.entrySet()) {
                System.out.println("Level " + entry.getKey() + " Dungeon:");
                for (Monster monster : entry.getValue()) {
                    System.out.println("- " + monster.getName());
                }
            }

            System.out.println("\n1. Enter Dungeon");
            System.out.println("2. Back to Main Menu");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.print("Enter dungeon level (1-2): ");
                int level = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (dungeons.containsKey(level)) {
                    enterDungeon(level);
                } else {
                    System.out.println("Invalid dungeon level!");
                }
            } else if (choice == 2) {
                return;
            }
        }
    }

    private static void enterDungeon(int level) {
        List<Monster> monsters = dungeons.get(level);
        System.out.println("\nEntering level " + level + " dungeon...");

        for (Monster monster : monsters) {
            System.out.println("\nA " + monster.getName() + " appears!");
            if (battle(monster)) {
                System.out.println("You defeated the " + monster.getName() + "!");
                System.out.println("You received: " + monster.getReward());
                playerCharacter.addItem(monster.getReward());
            } else {
                System.out.println("You were defeated by the " + monster.getName() + "!");
                return;
            }
        }
        System.out.println("\nCongratulations! You cleared the dungeon!");
    }

    private static boolean battle(Monster monster) {
        int monsterHealth = monster.getHealth();
        int playerHealth = playerCharacter.getHealth();

        while (true) {
            // Player's turn
            int damage = Math.max(1, playerCharacter.getAttack() - monster.getDefense());
            monsterHealth -= damage;
            System.out.println("You deal " + damage + " damage to the " + monster.getName());

            if (monsterHealth <= 0) {
                return true;
            }

            // Monster's turn
            damage = Math.max(1, monster.getAttack() - playerCharacter.getDefense());
            playerHealth -= damage;
            System.out.println("The " + monster.getName() + " deals " + damage + " damage to you");

            if (playerHealth <= 0) {
                playerCharacter.setHealth(100); // Reset health after defeat
                return false;
            }

            System.out.println("\nYour HP: " + playerHealth);
            System.out.println("Monster HP: " + monsterHealth);

            System.out.println("\n1. Continue fighting");
            System.out.println("2. Try to run");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 2) {
                if (Math.random() < 0.5) {
                    System.out.println("You successfully ran away!");
                    return false;
                } else {
                    System.out.println("Couldn't escape!");
                }
            }
        }
    }
}
