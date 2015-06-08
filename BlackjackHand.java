
package Blackjack;
import java.util.ArrayList;

/**
 * A class representing a blackjack hand. A BlackjackHand object can determine
 * the total point value and status (bust, blackjack, etc.) of the cards 
 * contained in the hand.
 * @author Tze-Hei (Zee) Yong
 */
public class BlackjackHand 
{
    public enum Status {BLACKJACK, TWENTYONE, BUST, CONTINUE};
    private final ArrayList<Card> hand;
    private int numberOfCards;
    private int numberOfAces;
    private int pointValue;
    private Status status;
    
    public BlackjackHand()
    {
        hand = new ArrayList<>();
        initialize();
    } // end default constructor
    
    public void add(Card card)
    {
        hand.add(card);  // call ArrayList's add method
        if (card.getValue() == 1)
            numberOfAces++;
        numberOfCards++;
        evaluateStatus();  // will set status and pointValue fields
    } // end add
    
    public int getNumberOfCards()
    {
        return numberOfCards;
    } // end getNumberOfCards
    
    public int getPointValue()
    {
        return pointValue;
    } // end getPointValue
    
    public boolean hasAce()
    {
        return (numberOfAces > 0);
    } // end hasAce
    
    public Status getStatus()
    {
        return status;
    } // end getStatus
    
    public boolean isEmpty()
    {
        return hand.isEmpty();  // call ArrayList's isEmpty method
    } // end isEmpty
    
    public void clear()
    {
        hand.clear();  // call ArrayList's clear method
        initialize();      
    } // end clear
    
    /**
     *  Displays names of all cards in hand.
     */
    public void displayAll()
    {
        for (Card card: hand)
            System.out.print(card.getName() + "   ");
        System.out.println();
    } // end displayAll
    
    /**
     *  Displays name of first card in hand.
     */
    public void displayFirst()
    {
        if (!isEmpty())
            System.out.println(hand.get(0).getName());
    } // end displayFirst

    
    // Private method to initialize or reset private data fields
    private void initialize()
    {
        numberOfCards = 0;
        numberOfAces = 0;
        pointValue = 0;
        status = Status.CONTINUE;
    } // end initialize
    
    // Private method to determine and set status of hand based on current cards
    private void evaluateStatus()
    {
        calculatePointValue();  // ensure pointValue field is updated
        
        if (pointValue > 21)
            status = Status.BUST;
        else if (pointValue == 21)
        {
            if (numberOfCards == 2)
                status = Status.BLACKJACK;
            else
                status = Status.TWENTYONE;
        }
        else
            status = Status.CONTINUE;  // sum is < 21
        
    } // end evaluateStatus

    
    // Private method to sum the points for the current cards in the hand
    // and set the pointValue data field
    private void calculatePointValue()
    {
        int sum = 0;
        for (Card card: hand)
            sum += card.getValue();
        if (hasAce() && sum <= 11)  // adjust for an Ace in hand
            sum = sum + 10;
        pointValue = sum;
    } // end calculatePointValue  
    
} // end BlackjackHand
