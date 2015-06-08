
package Blackjack;

/**
 * A class representing a standard playing card used in blackjack.
 * @author Tze-Hei (Zee) Yong
 */
public class Card 
{
    private final String name;   // full name of card (e.g., 2 of Spades)
    private final String face;   // (e.g., 2, 10, Jack, Ace)
    private final String suit;   // (e.g., Clubs, Hearts)
    private final int value;     // (e.g., 10 for a Jack)
    
    /**
     * Constructor for Card object.
     * @param name  String giving descriptive name of card (e.g., 2 of Spades)
     * @param face  String giving card face (e.g., 10, Jack)
     * @param suit  String giving card suit (e.g., Hearts)
     * @param value  An integer giving points value for card (e.g., 1 for Ace)
     */
    public Card (String name, String face, String suit, int value)
    {
        this.name = name;
        this.face = face;
        this.suit = suit;
        this.value = value;
    } // end constructor
    
    public String getName()
    {
        return name;
    } // end getName
    
    public String getFace()
    {
        return face;
    } // end getFace
    
    public String getSuit()
    {
        return suit;
    } // end getSuit
    
    public int getValue()
    {
        return value;
    } // end getValue
            
} // end Card
