
package Blackjack;

import Blackjack.BlackjackHand.Status;
import java.util.Scanner;

/**
 * Contains main method for a single-player game of blackjack against a dealer.
 * @author Tze-Hei (Zee) Yong
 */
public class BlackjackApp 
{
    public static void main (String[] args)
    {
        System.out.println("Welcome to Blackjack!");
        System.out.println("\nYou have $100 to start with.\nIt costs $10 per game to play.");
        System.out.println("Blackjack pays $15 + your initial $10.");
        System.out.println("Any other win pays $10 + your initial $10.");
        System.out.println("A draw returns your initial $10.");
        System.out.println("The deck of 52 cards is reshuffled after every game.");
        int money = 100;
        
        // Create standard deck of 52 playing cards
        Card[] cardArray = new Card[52];
        String[] cardFaces = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9",
                              "10", "Jack", "Queen", "King"};
        String[] cardSuits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        int[] cardValues = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
        
        int cardArrayIndex = 0;
        for (String suit: cardSuits)
        {
            for (int i = 0; i < cardFaces.length; i++)
            {
                String nameString = cardFaces[i] + " of " + suit;
                Card newCard = new Card(nameString, cardFaces[i], suit, cardValues[i]);
                cardArray[cardArrayIndex] = newCard;
                cardArrayIndex++;
            } // end for
        } // end for
        
        Deck bjDeck = new Deck(cardArray);
        
        boolean continuePlay = true;
        while (continuePlay)
        {
            // Shuffle entire deck of cards
            bjDeck.shuffle();
            
            // Take out initial $10 bet to play
            money = money - 10;
      
            // Initial deal of two cards each to dealer and player
            // BlackjackHand's add method also updates the hand's point value
            // and status (whether blackjack, twentyone, bust, or continue).
            BlackjackHand dealerHand = new BlackjackHand();
            BlackjackHand playerHand = new BlackjackHand();
            dealerHand.add(bjDeck.deal());
            dealerHand.add(bjDeck.deal());        
            playerHand.add(bjDeck.deal());        
            playerHand.add(bjDeck.deal()); 
        
            // Display dealer's first card and player's two cards
            System.out.print("\nDealer's top card:  ");
            dealerHand.displayFirst();
            System.out.print("Player's cards:  ");
            playerHand.displayAll();
            
            // Player plays until blackjack, twentyone, bust, or stand
            boolean playerStand = false;
            while ( !playerStand && (playerHand.getStatus() == Status.CONTINUE) )
            {
                Scanner input = new Scanner(System.in);
                String response;
                do 
                {
                    System.out.print("Do you want another card (y/n)? ");
                    response = input.next().trim().toLowerCase();
                    if (response.charAt(0) == 'y')
                    {
                        playerHand.add(bjDeck.deal());
                        System.out.print("Player's cards:  ");
                        playerHand.displayAll();
                    }
                    else if (response.charAt(0) == 'n')
                        playerStand = true;
                    else  // entered invalid response
                        response = "";  // set to empty string                   
                } while (response.isEmpty());  // repeat until get valid response
            } // end outer while
        
            // Display status of player's hand
            Status playerStatus = playerHand.getStatus();
            if (playerStatus == Status.BUST)
                System.out.println("Bust! You lose!");
            else if (playerStatus == Status.BLACKJACK)
            {
                System.out.println("You have Blackjack!");
            }
            else if (playerStatus == Status.TWENTYONE)
                System.out.println("You have twenty-one!");
            else  // status is CONTINUE and player chose to stand
                System.out.println("You stand with " + playerHand.getPointValue() + " points.");
        
            // Display dealer's cards unless player has already busted
            if (playerStatus != Status.BUST)
            {
                System.out.print("\nDealer's cards:  ");
                dealerHand.displayAll();
            }
                        
            Status dealerStatus = dealerHand.getStatus();
            
            // Check for win or draw if player has Blackjack
            if (playerStatus == Status.BLACKJACK)
            {
                if (dealerStatus == Status.BLACKJACK)
                {
                    System.out.println("Dealer also has Blackjack -- it's a draw!");
                    money = money + 10;
                }
                else
                {
                    System.out.println("You win!");
                    money = money + 25;
                }
            }
            
            // Dealer plays unless player has already busted or has Blackjack
            if ((playerStatus != Status.BUST) && (playerStatus != Status.BLACKJACK))
            {
                System.out.println("\nNow the dealer plays: ");

                boolean dealerStand = false;
                while ( !dealerStand && (dealerHand.getStatus() == Status.CONTINUE) )            
                {            
                    if (dealerHand.getPointValue() >= 17)
                        dealerStand = true;
                    else
                    {
                        System.out.println("The dealer takes another card...");
                        dealerHand.add(bjDeck.deal()); 
                        System.out.print("Dealer's cards:  ");
                        dealerHand.displayAll();                
                    }
                } // end while; loop exited if blackjack, twentyone, bust, or stand

                // Display status of dealer's hand and determine winner
                dealerStatus = dealerHand.getStatus();
                if (dealerStatus == Status.BUST)
                {
                    System.out.println("The dealer is bust! You win!");
                    money = money + 20;
                }
                else if (dealerStatus == Status.BLACKJACK)
                {
                    System.out.println("The dealer has Blackjack!");
                    System.out.println("You lose!"); // already checked for draw
                }
                else if (dealerStatus == Status.TWENTYONE)
                {
                    System.out.println("The dealer has twenty-one!");
                    if (playerStatus == Status.TWENTYONE) // already checked for player blackjack
                    {
                        System.out.println("It's a draw!");
                        money = money + 10;
                    }
                    else 
                        System.out.println("You lose!");
                }
                else  // status is CONTINUE and dealer chose to stand
                {
                    System.out.println("Dealer stands with " + dealerHand.getPointValue() + " points."); 
                    if (dealerHand.getPointValue() > playerHand.getPointValue())
                        System.out.println("You lose!");
                    else if (dealerHand.getPointValue() == playerHand.getPointValue())
                    {
                        System.out.println("It's a draw!");
                        money = money + 10;
                    }
                    else 
                    {
                        System.out.println("You win!");
                        money = money + 20;
                    }
                }
            } // end if (playerStatus != Status.BUST)...
            
            // Check if player wants to stop playing
            Scanner input = new Scanner(System.in);
            String response;
            do 
            {
                System.out.println("\nYou have $" + money);
                System.out.print("Do you want to play again (y/n)? ");
                response = input.next().trim().toLowerCase();
                if (response.charAt(0) == 'y')
                {
                    if (money >= 10)
                        continuePlay = true;
                    else
                    {
                        System.out.println("Sorry, you don't have enough money to play again!");
                        continuePlay = false;
                    }
                }
                else if (response.charAt(0) == 'n')
                    continuePlay = false;
                else  // entered invalid response
                    response = "";  // set to empty string 
            } while (response.isEmpty());  // repeat until get valid response            
            
        } // end outermost while(continuePlay)
        
        System.out.println("\nGoodbye!");
        
    } // end main
    
    
    



    
    
    
    
} // end BlackjackApp
