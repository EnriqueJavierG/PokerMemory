/**
 * This class makes a unique level where you find flushes to win
 * 
 * @version 1.0
 * @since 4/12/2017
 * 
 * @author Enrique Javier
 * @author Luis Rivera 
 * 
 * 
 */
import java.io.FileNotFoundException;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
public class FlushLevel extends RankTrioLevel{

	ValueGiver giver = new ValueGiver();
	long score = 0;
	final long x = 5; 

	protected FlushLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame)
	{
		super(validTurnTime, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("Flush Level");
		this.setCardsToTurnUp(5);
		this.setCardsPerRow(10);
		this.setRowsPerGrid(5);
		this.scoreReset();
	}


	@Override
	protected void makeDeck() {
		// In Flush level the grid consists of distinct cards, no repetitions

		//back card
		ImageIcon backIcon = this.getCardIcons()[this.getTotalCardsPerDeck()];

		int cardsToAdd[] = new int[getRowsPerGrid() * getCardsPerRow()];
		for(int i = 0; i < (getRowsPerGrid() * getCardsPerRow()); i++)
		{
			cardsToAdd[i] = i;
		}

		// randomize the order of the deck
		this.randomizeIntArray(cardsToAdd);

		// make each card object
		for(int i = 0; i < cardsToAdd.length; i++)
		{
			// number of the card, randomized
			int num = cardsToAdd[i];
			// make the card object and add it to the panel
			String rank = cardNames[num].substring(0, 1);
			String suit = cardNames[num].substring(1, 2);
			this.getGrid().add( new Card(this, this.getCardIcons()[num], backIcon, num, rank, suit));
		}
	}

	@Override
	protected boolean turnUp(Card card) {
		// the card may be turned

		if(this.getTurnedCardsBuffer().size() < getCardsToTurnUp()) 
		{
			// add the card to the list
			this.getTurnedCardsBuffer().add(card);
			if(this.getTurnedCardsBuffer().size() == getCardsToTurnUp())
			{
				// We are uncovering the last card in this turn
				// Record the player's turn
				this.getTurnsTakenCounter().increment();
				// Checks if the five turn up cards are a flush
				if(isFlush(card)){
					// Five cards match, so remove them from the list (they will remain face up)
					this.getTurnedCardsBuffer().clear();
				}
				else 
					// did not found a flush
				{
					// will deduct 5 points for every turn that you don't find a flush 
					if(score != 0) 
					{
						score -= x;
						this.getMainFrame().setScore(score);
						//					return false;
					}
					// The cards do not match, so start the timer to turn them down
					this.getTurnDownTimer().start();
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Convenience method for finding a flush combination
	 * 
	 * @param card the cards that are face up
	 * @return true if a flush combination is found
	 */
	public boolean isFlush(Card card) 
	{
		Card otherCard1 = (Card) this.getTurnedCardsBuffer().get(0);
		Card otherCard2 = (Card) this.getTurnedCardsBuffer().get(1);
		Card otherCard3 = (Card) this.getTurnedCardsBuffer().get(2);
		Card otherCard4 = (Card) this.getTurnedCardsBuffer().get(3);

		if((card.getSuit().equals(otherCard1.getSuit())) && (card.getSuit().equals(otherCard2.getSuit())) && 
				(card.getSuit().equals(otherCard3.getSuit())) && (card.getSuit().equals(otherCard4.getSuit()))) 
		{
			score += 700 + giver.rankSummation(otherCard1, otherCard2, otherCard3, otherCard4, card);
			giver.resetValues();
			this.getMainFrame().setScore(score);
			return true;
		}	
		return false;

	}

	/**
	 *  Method for reseting score for every new level
	 * 
	 */
	public void scoreReset()
	{
		this.getMainFrame().setScore(0);
	}

	
	
	/**
	 *  Method for checking if there are no more hands to find
	 *  In this case if there are no more flushes in the game.
	 * 
	 * 
	 */
	public boolean isGameOver()
	{

		int counter = 0;

		for(int i = 0; i< this.getGrid().size(); i++)
		{
			if(!this.getGrid().get(i).isFaceUp())
			{
				counter++;
			}
		}	

		if (counter == 9)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
