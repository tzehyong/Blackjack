
package Blackjack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * A class representing a deck of playing cards used in blackjack.
 * @author Tze-Hei (Zee) Yong
 */
public class Deck 
{
    private final ArrayList<Card> deck;
    private final int numberOfCards; // total cards in deck (does not change when a card is dealt)
    private int numCardsToDeal; // number of cards that have not yet been dealt
    private int nextIndex;  // array index for next card to be dealt
    
    /**
     * Constructor that creates a new Deck from an array of Card objects.
     * @param cardArray  The array of Cards to be used to create the new Deck.
     */
    public Deck(Card[] cardArray)
    {
        deck = new ArrayList<>();
        deck.addAll(Arrays.asList(cardArray));    
        numberOfCards = deck.size();
        numCardsToDeal = numberOfCards;
        nextIndex = 0;
    } // end constructor

    /**
     * Returns the total number of cards in the deck (does not change when
     * a card is dealt).
     * @return  The total number of cards in the deck.
     */
    public int getNumberOfCards()
    {
        return numberOfCards;
    } // end getNumberOfCards
    
    /**
     * @return  The number of cards in the deck that have not yet been dealt.
     */
    public int getNumCardsToDeal()
    {
        return numCardsToDeal;
    } // end getNumCardsToDeal
    
    /**
     * Displays names of all cards in deck.
     */
    public void displayAll()
    {
        for (Card card: deck)
            System.out.println(card.getName());        
    } // end displayAll    
       
    /**
     * Deals the next card from the deck.
     * Note: The deck is iterated through as each card is dealt, but cards
     * are not deleted from the deck ArrayList.
     * @return  The Card object that is the next card to be dealt.
     */
    public Card deal()
    {
        Card nextCard = null;
        if (nextIndex < deck.size())  // not yet reached end of deck
        {       
            nextCard = deck.get(nextIndex);
            nextIndex++;
            numCardsToDeal--;
        } // end if
        return nextCard;  // returns null if past end of deck
    } // end deal
    
    /**
     * Randomly shuffles the positions of all cards in the deck and resets 
     * the first card in the ArrayList as the next card to be dealt.
     */
    public void shuffle()
    {
        shuffle(deck, numberOfCards); // call private shuffle method
        nextIndex = 0;  // reset 1st card in ArrayList as next card to be dealt
        numCardsToDeal = numberOfCards;  // reset to initial value
    } // end public shuffle method
    
    // Uses recursive algorithm to randomly pick a card out of the 'unshuffled'
    // portion of the deck and set it as the next card in the 'shuffled' portion.
    // Cards are set in the 'shuffled' portion starting from the end of the
    // deck ArrayList (i.e., starting from the last array element and proceeding
    // toward the first array element).
    // @param deckRef  A reference to the deck ArrayList in this Deck object.
    // @param numToShuffle  The number of cards in the 'unshuffled' portion
    //                      of the deck (corresponding to array index values 
    //                      0 to numToShuffle - 1)
    private void shuffle(ArrayList<Card> deckRef, int numToShuffle)
    {
        if (numToShuffle > 1)  // still have part of deck that is unshuffled
        {
            // Generate random card position (index) between 0 and 
            // numToShuffle - 1, inclusive.
            Random generator = new Random();
            int nextCardPosition = generator.nextInt(numToShuffle); 
               
            // Swap last card in unshuffled portion with card at nextCardPosition.
            // New card at lastIndex is now part of the 'shuffled' portion of the deck.
            int lastIndex = numToShuffle - 1; // array index for last card in unshuffled portion        
            Card lastCard = deckRef.get(lastIndex);
            deckRef.set(lastIndex, deckRef.get(nextCardPosition));
            deckRef.set(nextCardPosition, lastCard); 
            
            // Recursively call method on the remaining 'unshuffled' portion
            // of the deck. Recursive calls continue until numToShuffle <= 1.
            shuffle(deckRef, numToShuffle - 1);
            
        } // end if
    } // end private shuffle method  
    
} // end Deck
