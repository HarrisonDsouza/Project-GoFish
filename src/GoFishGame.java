import java.util.List;
import java.util.Scanner;

public class GoFishGame {
    private final List<Player> players;
    private final Deck deck;

    public GoFishGame(List<Player> players) {
        this.players = players;
        this.deck = new Deck();
    }

    public void dealCards() {
        for (int i = 0; i < 7; i++) {
            for (Player player : players) {
                player.addCard(deck.drawCard());
            }
        }
    }

    public void playRound(Scanner scanner) {
        for (Player player : players) {
            if (player.getHandSize() == 0 && deck.getSize() > 0) {
                player.addCard(deck.drawCard());
            }

            System.out.println("\n" + player.getName() + ", it's your turn.");
            System.out.println(player);

            String rankToAsk = "";
            while (true) {
                System.out.print("Which rank do you want to ask for? ");
                rankToAsk = scanner.nextLine().toUpperCase();
                if (player.hasCardOfRank(rankToAsk)) {
                    break;
                } else {
                    System.out.println("You must have at least one card of that rank. Try again.");
                }
            }

            Player opponent = chooseOpponent(player);
            System.out.println(player.getName() + " asks " + opponent.getName() + " for " + rankToAsk + ".");

            if (opponent.hasCardOfRank(rankToAsk)) {
                System.out.println(opponent.getName() + " says: Yes, I have " + rankToAsk + ".");
                player.transferCards(opponent, rankToAsk);
            } else {
                System.out.println(opponent.getName() + " says: Go fish!");
                if (deck.getSize() > 0) {
                    player.addCard(deck.drawCard());
                }
            }

            player.checkAndBook();
        }
    }

    private Player chooseOpponent(Player currentPlayer) {
        for (Player player : players) {
            if (!player.equals(currentPlayer)) {
                return player;
            }
        }
        return null;
    }

    public boolean isOver() {
        int totalBooks = 0;
        for (Player player : players) {
            totalBooks += player.getBookCount();
        }
        return totalBooks == 26;
    }

    public void announceWinner() {
        Player winner = null;
        int maxBooks = 0;
        for (Player player : players) {
            if (player.getBookCount() > maxBooks) {
                maxBooks = player.getBookCount();
                winner = player;
            }
        }
        System.out.println("\nThe winner is " + winner.getName() + " with " + maxBooks + " books!");
    }
}
