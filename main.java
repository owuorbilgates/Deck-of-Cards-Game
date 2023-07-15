import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * The Main class represents the entry point of the program. It provides a console-based user interface
 * for shuffling and dealing cards from a deck of cards.
 *
 * @author Bilgates
 */
public class Main {
    /**
     * The main method is the starting point of the program. It prompts the user for the number of suits
     * and ranks, creates a deck of cards, and presents a menu for shuffling, dealing hands, and generating
     * a histogram of card values.
     *
     * @param args The command line arguments (unused).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many suits? ");
        int numSuits = scanner.nextInt();

        System.out.print("How many ranks? ");
        int maxRank = scanner.nextInt();

        DeckOfCards deck = new DeckOfCards(maxRank, numSuits);
        System.out.println("Deck size: " + deck.getSize());

        int choice;
        do {
            System.out.println("1=shuffle, 2=deal, 3=histogram, 4=quit:");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    deck.shuffle();
                    System.out.println("Deck shuffled.");
                    break;
                case 2:
                    System.out.print("How many cards? ");
                    int handSize = scanner.nextInt();
                    Card[] hand = deck.deal(handSize);
                    System.out.println("Hand: " + Arrays.toString(hand));
                    break;
                case 3:
                    System.out.print("How many cards? ");
                    handSize = scanner.nextInt();
                    int[] histogram = deck.histogram(handSize);
                    System.out.println("Histogram: " + Arrays.toString(histogram));
                    break;
                case 4:
                    System.out.println("BYE!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 4);
    }
}

/**
 * The Card class represents a playing card with a rank and a suit.
 */
class Card {
    private int rank;
    private int suit;

    /**
     * Creates a new Card object with the specified rank and suit.
     *
     * @param rank The rank of the card.
     * @param suit The suit of the card.
     */
    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Returns the rank of the card.
     *
     * @return The rank of the card.
     */
    public int getRank() {
        return rank;
    }

    /**
     * Returns the suit of the card.
     *
     * @return The suit of the card.
     */
    public int getSuit() {
        return suit;
    }

    /**
     * Returns the value of the card, which is calculated as the rank multiplied by the suit.
     *
     * @return The value of the card.
     */
    public int getValue() {
        return rank * suit;
    }

    /**
     * Returns a string representation of the card.
     *
     * @return The string representation of the card.
     */
    @Override
    public String toString() {
        return "Card(" + rank + ", " + suit + ")";
    }
}

/**
 * The DeckOfCards class represents a deck of playing cards.
 */
class DeckOfCards {
    private Card[] cards;
    private int size;

    /**
     * Creates a new deck of cards with the specified maximum rank and number of suits.
     *
     * @param maxRank  The maximum rank of the cards.
     * @param numSuits The number of suits in the deck.
     */
    public DeckOfCards(int maxRank, int numSuits) {
        size = maxRank * numSuits;
        cards = new Card[size];
        int index = 0;
        for (int suit = 1; suit <= numSuits; suit++) {
            for (int rank = 1; rank <= maxRank; rank++) {
                cards[index] = new Card(rank, suit);
                index++;
            }
        }
    }

    /**
     * Shuffles the deck of cards by randomly swapping pairs of cards in the array.
     */
    public void shuffle() {
        Random rand = new Random();
        for (int i = size - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            Card temp = cards[i];
            cards[i] = cards[j];
            cards[j] = temp;
        }
    }

    /**
     * Returns the size of the deck, which is the total number of cards.
     *
     * @return The size of the deck.
     */
    public int getSize() {
        return size;
    }

    /**
     * Deals a specified number of cards from the top of the deck and returns them as an array.
     * The size of the deck is reduced accordingly.
     *
     * @param numCards The number of cards to deal.
     * @return An array of Card objects representing the dealt cards.
     */
    public Card[] deal(int numCards) {
        if (numCards > size) {
            System.out.println("Not enough cards in the deck.");
            return null;
        }

        Card[] hand = new Card[numCards];
        for (int i = 0; i < numCards; i++) {
            hand[i] = cards[size - 1 - i];
        }
        size -= numCards;
        return hand;
    }

    /**
     * Deals and shuffles the cards multiple times to generate a histogram of the total values of the
     * dealt hands. The histogram array stores the frequency of each total value.
     *
     * @param numCards The number of cards to deal in each hand.
     * @return An array representing the histogram of total values.
     */
    public int[] histogram(int numCards) {
        int[] histogram = new int[(size + 1) * numCards];
        for (int i = 0; i < 100000; i++) {
            shuffle();
            Card[] hand = deal(numCards);
            int sum = 0;
            for (Card card : hand) {
                sum += card.getValue();
            }
            histogram[sum]++;
        }
        return histogram;
    }
}
