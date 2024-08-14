import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private final String name;
    private final List<Card> hand;
    private final List<List<Card>> books;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public int getHandSize() {
        return hand.size();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public boolean hasCardOfRank(String rank) {
        return hand.stream().anyMatch(card -> card.getRank().equals(rank));
    }

    public void transferCards(Player fromPlayer, String rank) {
        List<Card> cardsToTransfer = fromPlayer.getHand().stream()
                .filter(card -> card.getRank().equals(rank))
                .collect(Collectors.toList());
        fromPlayer.getHand().removeAll(cardsToTransfer);
        hand.addAll(cardsToTransfer);
    }

    public void checkAndBook() {
        List<String> ranks = hand.stream()
                .map(Card::getRank)
                .distinct()
                .collect(Collectors.toList());
        
        for (String rank : ranks) {
            long count = hand.stream().filter(card -> card.getRank().equals(rank)).count();
            if (count == 4) {
                List<Card> book = hand.stream()
                        .filter(card -> card.getRank().equals(rank))
                        .collect(Collectors.toList());
                books.add(book);
                hand.removeAll(book);
                System.out.println(name + " books the " + rank + "s.");
            }
        }
    }

    public int getBookCount() {
        return books.size();
    }

    @Override
    public String toString() {
        return name + "'s hand: " + hand;
    }
}
