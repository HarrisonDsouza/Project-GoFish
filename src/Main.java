import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int numberOfPlayers = 0;
            while (true) {
                System.out.print("Enter the number of players (at least 2): ");
                if (scanner.hasNextInt()) {
                    numberOfPlayers = scanner.nextInt();
                    if (numberOfPlayers >= 2) {
                        break;
                    } else {
                        System.out.println("There must be at least 2 players. Please try again.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Clear the invalid input
                }
            }
            scanner.nextLine(); // Consume the remaining newline

            List<Player> players = new ArrayList<>();
            for (int i = 0; i < numberOfPlayers; i++) {
                System.out.print("Enter name for player " + (i + 1) + ": ");
                String playerName = scanner.nextLine();
                players.add(new Player(playerName));
            }

            GoFishGame game = new GoFishGame(players);
            game.dealCards();

            while (!game.isOver()) {
                game.playRound(scanner);
            }

            game.announceWinner();
        }
    }
}
